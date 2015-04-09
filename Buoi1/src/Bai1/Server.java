package Bai1;
import java.io.*;
import java.net.*;

public class Server {
    public static int defaultPort = 65530;
    public static String defaultAddress = "localhost";
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(defaultPort);
            System.out.println("Server Started. Waitting for Client");
            while(true){
                Socket s = ss.accept();
                User usr = new User(s);
                usr.start();
            }
            
        } catch (IOException ex) {
            System.out.println("Server Socket error: "+ex.getMessage());
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
            
            InetAddress id= s.getInetAddress();
            System.out.println(id+" connected!");
            while(true){
                String str1 = in.readLine();
                String str2;
                if(str1.compareTo("Exit")==0) System.exit(1);
                if(str1.compareTo("@")==0) break;
                else if(str1.compareTo("0")==0) str2="Khong";
                else if(str1.compareTo("1")==0) str2="Mot";
                else if(str1.compareTo("2")==0) str2="Hai";
                else if(str1.compareTo("3")==0) str2="Ba";
                else if(str1.compareTo("4")==0) str2="Bon";
                else if(str1.compareTo("5")==0) str2="Nam";
                else if(str1.compareTo("6")==0) str2="Sau";
                else if(str1.compareTo("7")==0) str2="Bay";
                else if(str1.compareTo("8")==0) str2="Tam";
                else if(str1.compareTo("9")==0) str2="Chin";
                else str2 = "Khong hop le";
                
                out.println(str2);
            }
            try{
                System.out.println(id+" disconected");
                s.close();
            }catch(IOException ex){
                System.out.println("Error: Cant close!");
            }
                
        } catch (IOException ex) {
            System.out.println("User Error: "+ex.getMessage());
        }
    }
}