package Bai1;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static int defaultPort = 65532;
    public static String defaultAddress = "localhost";
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            Socket s = new Socket(defaultAddress, defaultPort);
            System.out.println("Client started");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintStream out = new PrintStream(s.getOutputStream());
            
            while(true){
                System.out.println("---LIST FILE---");
                System.out.println("Cu phap: LIST <path>");
                System.out.print("Nhap cau lenh: ");
                String str1 = input.nextLine();
                
                if(str1.compareTo("Exit")==0) break;
                
                out.println(str1);

                System.out.println("<"+str1+">");
                while(true){
                    String str2 = in.readLine();
                    if(str2.compareTo("@")==0) break;
                    System.out.println(str2);
                }
                
            }
            s.close();
        } catch (IOException ex) {
            System.out.println("Socket Error: "+ex.getMessage());
        }
        
    }
}
