import java.io.*;
import java.net.*;
public class ClientThi {
    private static final String MULTICAST_ID = "230.0.0.2";
    private static final int MULTICAST_PORT = 8888;
    public static final String SERVER_ID = "localhost"; //Sua lai dia chi may chu
    public static final int SERVER_PORT = 65530;
    
    public static void main(String[] args) {
        try {
            MulticastSocket ms = new MulticastSocket(MULTICAST_PORT);
            InetAddress msId = InetAddress.getByName(MULTICAST_ID);
            ms.joinGroup(msId);
            System.out.println("Joined into "+msId.getHostAddress());
            
            //while(true){
                byte[] data = new byte[60000];
                DatagramPacket in = new DatagramPacket(data,data.length);
                ms.receive(in);
                String str1 = new String(in.getData(),0,in.getLength());
                
                System.out.println("Received from Server: "+str1);
                
                //if(str1.compareTo("Exit")==0) break;//DK thoat
                        
                ///---Xu ly du lieu---///
                String a[] = str1.split("[ ]");

                String str2;
                if(a.length==4){
                    try {
                        ///---Xu ly phep toan---///
                        String str3; //Bien toan cuc dung de ghi vao file
                        int x = Integer.parseInt(a[1]);
                        int y = Integer.parseInt(a[2]);
                        long s;
                        if(a[0].compareTo("+")==0){
                            s = x + y;
                            str3 = readNumber(x)+" "+covOperator(a[0])+" "+readNumber(y) + " bang "+ readNumber((int)s);
                        }
                        else if(a[0].compareTo("-")==0){
                            s = x - y;
                            str3 = readNumber(x)+" "+covOperator(a[0])+" "+readNumber(y) + " bang "+ readNumber((int)s);
                        }
                        else if(a[0].compareTo("*")==0){
                            s = x * y;
                            str3 = readNumber(x)+" "+covOperator(a[0])+" "+readNumber(y) + " bang "+ readNumber((int)s);
                        }
                        else{
                            try {
                                s = x / y; 
                                str3 = readNumber(x)+" "+covOperator(a[0])+" "+readNumber(y) + " bang "+ readNumber((int)s);
                            } catch (ArithmeticException ex) {
                                str3 = "Div#0!";
                            }
                        }
                        ///---Luu vao file---///
                        File f1 = new File("ketqua.txt");
                        FileOutputStream fout1 = new FileOutputStream(f1);
                        fout1.write(str3.getBytes());
                        fout1.close();

                        ///---Get web---///
                        Socket socket = new Socket(a[3],80);
                        PrintStream os = new PrintStream(socket.getOutputStream());
                        String statement = "GET / HTTP/1.0 \r\n";
                        os.println(statement);

                        BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        File f2 = new File(a[3]+".html");
                        FileOutputStream fout2 = new FileOutputStream(f2);
                        
                        int ch;
                        while(true){
                            ch = is.read();
                            if(ch==-1) break;
                            fout2.write(ch);
                        }
                        fout2.close();
                        socket.close();
                       
                        ///---List thu muc---///
                        File f3 = new File(".");
                        String b[] = f3.list();
                        System.out.println("---<Current Directory>---");
                        for(int i=0;i<b.length;i++){
                            System.out.println(b[i]);
                        }
                        
                        ///--Gui File---///
                        sendFile(a[3]+".html");
                        sendFile("ketqua.txt");
                        
                        ///---Thuc hien lenh thanh cong---///
                        str2 = "Successful!";
                    } catch (NumberFormatException ex) {
                        str2 = "Agrument error: You must give number!!";
                    } catch (IOException ex){
                        str2 = "Wrong web path";
                    }
                    
                }else str2 = "Agrument Error";
               
                ///--Hoan thanh---///
                System.out.println(str2);
            //}
            
            ms.leaveGroup(msId);
            ms.close();
        } catch (IOException ex) {
            System.out.println("Multicast Socket Error: "+ex.getMessage());
        }
        
    }
    
    public static String covNumber(int num){
        String kq="#";
        switch(num){
            case 0:{
                kq="khong";
                break;
            }
            case 1:{
                kq="mot";
                break;
            }
            case 2:{
                kq="hai";
                break;
            }
            case 3:{
                kq="ba";
                break;
            }
            case 4:{
                kq="bon";
                break;
            }
            case 5:{
                kq="nam";
                break;
            }
            case 6:{
                kq="sau";
                break;
            }
            case 7:{
                kq="bay";
                break;
            }
            case 8:{
                kq="tam";
                break;
            }
            case 9:{
                kq="chin";
                break;
            }
        }
        
        return kq;
    }
    
    public static String covNumberTen(int num){
        String kq="#";
        switch(num){
            case 0:{
                kq="muoi";
                break;
            }
            case 1:{
                kq="mot";
                break;
            }
            case 2:{
                kq="hai";
                break;
            }
            case 3:{
                kq="ba";
                break;
            }
            case 4:{
                kq="bon";
                break;
            }
            case 5:{
                kq="lam";
                break;
            }
            case 6:{
                kq="sau";
                break;
            }
            case 7:{
                kq="bay";
                break;
            }
            case 8:{
                kq="tam";
                break;
            }
            case 9:{
                kq="chin";
                break;
            }
        }
        
        return kq;
    }
    
    public static String readNumber(int num){
        if(num>=0 && num <10){
            return covNumber(num);
        }
        else if (num>=10 && num<100){
            if(num==10) return ("muoi");
            int a[] = new int[3];
            a[2] = num%10;
            a[1] = num/10;
            if(a[1]==0){
                if(a[2]==0) return "";
                else return covNumberTen(num);
            }
            else if(a[1]==1) return ("muoi "+covNumberTen(a[2]));
            else{
                a[1] = 0;
                a[0] = num/10;                
                return(covNumber(a[0]) + " " + covNumberTen(a[1]) + " " + covNumberTen(a[2]));
            }            
        }else if(num>99 && num <1000){
            if(num==100) return ("mot tram");
            int a[] = new int[2];
            a[1] = num%100;
            a[0] = num/100;
            if(a[1]<10 && a[1]>0) return(readNumber(a[0]) + " tram le " + readNumber(a[1]));
            if(a[1]!=0) return(readNumber(a[0]) + " tram " + readNumber(a[1]));
            else return(readNumber(a[0]) + " tram");
        }
        else if(num>999 && num <10000){
            if(num==1000) return ("mot ngan");
            int a[] = new int[2];
            a[1] = num%1000;
            a[0] = num/1000;
            if(a[1]<10 && a[1]>0) return(readNumber(a[0]) + " ngan le " + readNumber(a[1]));
            return(readNumber(a[0]) + " ngan " + readNumber(a[1]));
        }
        else if(num>9999 && num <100000){
            if(num==10000) return ("mot trieu");
            int a[] = new int[2];
            a[1] = num%10000;
            a[0] = num/10000;
            if(a[1]<10 && a[1]>0) return(readNumber(a[0]) + " trieu le " + readNumber(a[1]));
            return(readNumber(a[0]) + " trieu " + readNumber(a[1]));
        }
        return null;
    }
    
    public static String covOperator(String arg){
        String kq="Wrong Operator";
        if(arg.equals("+")) kq = "cong";
        if(arg.equals("-")) kq = "tru";
        if(arg.equals("*")) kq = "nhan";
        if(arg.equals("/")) kq = "chia";
        return kq;
    }
    
    public static void sendFile(String fName){
        try {
            Socket toServer = new Socket(SERVER_ID,SERVER_PORT);
            PrintStream outToServer = new PrintStream(toServer.getOutputStream());
            
            //--Gui ten file ve Server--//
            outToServer.println(fName);
            
            //--Gui noi dung file ve Server--//
            FileInputStream toSv = new FileInputStream(fName);                        
            byte[] data = new byte[toSv.available()];
            toSv.read(data);
            String kq = new String(data,0,data.length);
            
            outToServer.println(kq);
            outToServer.println("EndOfFile");
            toSv.close();
            
            System.out.println("File Sent");  
        } catch (IOException ex) {
            System.out.println("Send File Error: "+ex.getMessage());
        }
    }
    
}
