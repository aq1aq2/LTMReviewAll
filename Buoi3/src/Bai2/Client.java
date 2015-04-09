package Bai2;
import java.io.*;
import java.util.*;
import java.net.*;
public class Client {
    private static final int SERVERPORT = 65530;
    private static final String SERVERID = "localhost";
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            DatagramSocket ds = new DatagramSocket();
            InetAddress serverId = InetAddress.getByName(SERVERID);
            System.out.println("Client started");
            System.out.println("---Lay noi dung file---");
            System.out.println("Cu phap: READ <tenfile>"); 
            while(true){
                System.out.print("Nhap: ");
                String str1 = input.nextLine();
                
                byte[] data = str1.getBytes();
                DatagramPacket out = new DatagramPacket(data,data.length,serverId,SERVERPORT);
                ds.send(out);
                
                System.out.print("Nhap duong dan luu file: ");
                String path = input.nextLine();
                
                byte[] buffer = new byte[60000];
                DatagramPacket in = new DatagramPacket(buffer,buffer.length);
                ds.receive(in);
                
                String str2 = new String(in.getData(),0,in.getLength());
                System.out.println(str2);

                File f1 = new File(path);
                FileOutputStream fout = new FileOutputStream(f1);
                fout.write(in.getData(),0,in.getLength());
                fout.close();
                System.out.println("File wrote successful");
            }
            
        } catch (SocketException ex) {
            System.out.println("Datagram Socket error: "+ex.getMessage());
        } catch (UnknownHostException ex) {
            System.out.println("Unknown Host error: "+ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
}