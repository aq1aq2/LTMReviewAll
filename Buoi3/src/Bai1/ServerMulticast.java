package Bai1;
import java.io.*;
import java.util.*;
import java.net.*;

public class ServerMulticast {
    private static final int SERVERPORT=65530;
    private static final String SERVERID="230.0.0.1";
    private static final int CLIENTPORT=64530;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            DatagramSocket ds = new DatagramSocket(SERVERPORT);
            System.out.println("UDP Socket Server Started");
            byte[] buffer = new byte[60000];
            while(true){
                InetAddress serverId = InetAddress.getByName(SERVERID);
                
                Date date = new Date();
                String str2 = date.toString();
                DatagramPacket out = new DatagramPacket(str2.getBytes(),0,str2.length(),serverId,CLIENTPORT);
                Thread.sleep(1000);
                ds.send(out);
            }
        } catch (SocketException ex) {
            System.out.println("Datagram Socket Error: "+ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IO Error: "+ex.getMessage());
        } catch (InterruptedException ex) {
            System.out.println("Thread error: "+ex.getMessage());
        }
    }
}