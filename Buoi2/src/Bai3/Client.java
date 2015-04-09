package Bai3;
import java.io.*;
import java.util.*;
import java.net.*;

public class Client {
    public static int defaultPort = 65534;
    public static String defaultAddress = "localhost";
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            Socket s = new Socket(defaultAddress,defaultPort);
            System.out.println("Client started");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintStream out = new PrintStream(s.getOutputStream());
            
            
            
            s.close();
        } catch (IOException ex) {
            System.out.println("Socket Error: "+ex.getMessage());
        }
        
    }
}
