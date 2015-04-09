package Bai1;
import java.io.*;
import java.net.*;
public class ClientMulticast {
    private static final int SERVERPORT = 64530;
    private static final String SERVERID = "230.0.0.1";
    public static void main(String[] args) {
        try {
            System.out.println("Client started");
            MulticastSocket ms = new MulticastSocket(SERVERPORT);
            InetAddress serverId = InetAddress.getByName(SERVERID);
            ms.joinGroup(serverId);
            System.out.println("Joined into "+SERVERID);
            
            while(true){
                byte[] data = new byte[60000];
                DatagramPacket in = new DatagramPacket(data, data.length);
                ms.receive(in);

                String str1 = new String(in.getData(),0,in.getLength());
                System.out.println("Thoi gian hien tai la: "+str1);
            }
        } catch (IOException ex) {
            System.out.println("Socket Error: "+ex.getMessage());
        }
        
    }
}

class Receive extends Thread{
    public static DatagramSocket ds;
    public Receive(DatagramSocket dds){
        ds=dds;
    }
    @Override
    public void run(){
        try {
            while(true){
                byte[] buffer = new byte[60000];
                DatagramPacket in = new DatagramPacket(buffer, buffer.length);
                ds.receive(in);
                String str1 = new String(in.getData(),0,in.getLength());
                String a[] = str1.split("[#]");
                
                System.out.println(a[0] + ": " + a[1]);
            }
        } catch (UnknownHostException ex) {
            System.out.println("Unknown Host Error: "+ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Receive Error: "+ex.getMessage());
        }
    }
}