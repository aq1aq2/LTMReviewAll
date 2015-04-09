package Bai2;
import java.io.*;
import java.util.*;
import java.net.*;
public class Client {
    public static int defaultPort = 80;
    public static String defaultAddress = "www.google.com";
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            Socket s = new Socket(defaultAddress, defaultPort);
            System.out.println("Client started");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintStream out = new PrintStream(s.getOutputStream());
            
            //---Noi dung---//
            String path="/";
            String statement = "GET "+path+" HTTP/1.0\r\n";
            out.println(statement);
            
            String filename="google.html";
            FileOutputStream f1 = new FileOutputStream("d:\\"+filename);
            long ch;
            while(true){
                ch = in.read();
                if(ch==-1) break;
                System.out.print((char)ch);
                f1.write((char)ch);
            }
            f1.close();
            s.close();
            
            System.out.println("\nDa luu thanh cong");
            //---Ket thuc---//
            
        } catch (IOException ex) {
            System.out.println("Socket Error: "+ex.getMessage());
        }
        
    }
}
