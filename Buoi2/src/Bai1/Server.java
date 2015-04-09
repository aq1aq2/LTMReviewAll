package Bai1;
import java.io.*;
import java.net.*;

public class Server {
    public static int defaultPort = 65532;
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(defaultPort);
            System.out.println("Server Started");
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
    Socket s;
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
                
                System.out.println("Nhan tu Client: "+str1);
                if(str1.compareTo("Exit")==0) System.exit(1);
                if(str1.compareTo("@")==0) break;
                
                String a[] = new String[50];
                a=str1.split("[ ]");//a[1]="d:\";
                
                if(a.length==2){
                    if(a[0].compareTo("LIST")==0){
                        File f1 = new File(a[1]); //Tao bien kieu tap tin
                        if(f1.exists()){
                            String b[] = f1.list();
                            for(int i=0;i<b.length;i++){
                                out.println(b[i]);
                            }
                        }else out.println("Duong dan khong ton tai");
                    }
                    else out.println("Loi cu phap");
                }else out.println("Loi cu phap");
                
                out.println("@");
            }
            System.out.println(id+" disconnected");       
            
        } catch (IOException ex) {
            System.out.println("User error: "+ex.getMessage());
        }
        
    }
}
