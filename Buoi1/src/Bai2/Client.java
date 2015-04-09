package Bai2;
import java.io.*;
import java.util.*;
import java.net.*;

public class Client {
    public static int defaultPort = 65531;
    public static String defaultAddress = "localhost";
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            Socket s = new Socket(defaultAddress, defaultPort);
            System.out.println("Client Started");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintStream out = new PrintStream(s.getOutputStream());
            
            System.out.println("---GIAI TOAN---");
            System.out.println("Cu phap: OP Operant1 Operant2");
            while(true){
                System.out.print("Nhap phep toan: ");
                String str1 = input.nextLine();
                out.println(str1);

                if(str1.compareTo("@")==0 || str1.compareTo("Exit")==0 ) break;

                String str2 = in.readLine();
                System.out.println("Ket qua: "+str2);
            }
            
        } catch (IOException ex) {
            System.out.println("Socket error: "+ex.getMessage());
        }
        
    }
}
