package Bai2;
import java.io.*;
import java.net.*;

public class Server {
    public static int defaultPort = 65531;
    public static String defaultAddress = "localhost";
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(defaultPort);
            System.out.println("Server started. Watting for client...");
            while(true){
                Socket s = ss.accept();
                User usr = new User(s);
                usr.start();
            }
        } catch (IOException ex) {
            System.out.println("Server Socket Error: "+ex.getMessage());
        }
    }
}
class User extends Thread{
    public Socket s;
    public User(Socket ss){
        s=ss;
    }
    @Override
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintStream out = new PrintStream(s.getOutputStream());
            
            InetAddress id = s.getInetAddress();
            System.out.println(id+" connected");
            
            while(true){
                String str1 = in.readLine();
                System.out.println("Chuoi nhan duoc: "+str1);
                
                //Dieu kien dung
                if(str1.compareTo("Exit")==0) System.exit(1);
                if(str1.compareTo("@")==0) break;
                
                //Cat chuoi ra thanh mang
                String a[] = new String[50];
                a = str1.split("[ ]");
                //Kiem tra chuoi co cat dung hay khong
//                for(int i=0;i<a.length;i++){
//                    System.out.print(a[i]+" ");
//                }
                //Xu ly ket qua 
                String str2 = "";
                if(a.length!=3){
                    str2="Sai cu phap";
                }else{
                    try{
                        int m = Integer.parseInt(a[1]), n = Integer.parseInt(a[2]), result;
                        if(a[0].compareTo("+")==0){
                            result = m+n;
                            str2 = Integer.toString(result);
                        }
                        else if(a[0].compareTo("-")==0){
                            result = m-n;
                            str2 = Integer.toString(result);
                        }
                        else if(a[0].compareTo("*")==0){
                            result = m*n;
                            str2 = Integer.toString(result);
                        }
                        else if(a[0].compareTo("/")==0){
                            try{
                                result = m/n;
                                str2 = Integer.toString(result);
                            }
                            catch(Exception ex){
                                str2="Khong the chia cho 0";
                            }
                        } else str2="Sai cu phap";
                        
                    }catch(Exception ex){
                        str2="Cac ky tu nhap vao khong phai la so!";
                    }
                }
                out.println(str2);
            }
            try{
                System.out.println(id+" disconected");
                s.close();
            }catch(IOException ex){
                System.out.println("Error: Cant close!");
            }
            
        } catch (IOException ex) {
            System.out.println("Thread error: "+ex.getMessage());
        }
            
    }
}
