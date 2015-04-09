package Bai1;
import java.io.*;
import java.util.*;
import java.net.*;

public class Client {
    public static int defaultPort = 65530;
    public static String defaultAddress = "localhost";
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            Socket s = new Socket(defaultAddress,defaultPort);
            System.out.println("Client started");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintStream out = new PrintStream(s.getOutputStream());
            
            while(true){
                System.out.print("Nhap ky tu so (0 -> 9). @ de ket thuc: ");
                String str1 = input.nextLine();            
                out.println(str1);
                if(str1.compareTo("@")==0 || str1.compareTo("Exit")==0 ) break;
                
                String str2 = in.readLine();
                System.out.println("Ky tu "+str1+": "+str2);
            }
            
        } catch (IOException ex) {
            System.out.println("Socket error: "+ex.getMessage());
        }
        
        
    }
}

