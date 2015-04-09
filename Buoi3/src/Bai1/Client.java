package Bai1;
import java.io.*;
import java.util.*;
import java.net.*;
public class Client {
    private static final int SERVERPORT = 65530;
    private static final String SERVERADDRESS = "localhost";
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            DatagramSocket ds = new DatagramSocket();
            InetAddress serverId = InetAddress.getByName(SERVERADDRESS);
            System.out.println("Client started");
            while(true){
                System.out.print("Nhap vao chuoi bat ky: ");
                String str1 = input.nextLine();
                
                byte[] data = str1.getBytes();
                DatagramPacket out = new DatagramPacket(data, data.length,serverId,SERVERPORT);
                ds.send(out);
                
                byte[] buffer = new byte[60000];
                DatagramPacket in = new DatagramPacket(buffer, buffer.length);
                ds.receive(in);
                String str2 = new String(in.getData(),0,in.getLength());
                System.out.println("Ngay thang hien tai: "+str2);
            }
            
        } catch (SocketException ex) {
            System.out.println("Datagram Socket error: "+ex.getMessage());
        } catch (UnknownHostException ex) {
            System.out.println("Unknown Host Error: "+ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
}
