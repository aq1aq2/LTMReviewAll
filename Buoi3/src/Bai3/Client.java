//package Bai3;
import java.io.*;
import java.util.*;
import java.net.*;
public class Client {
    public static final int SERVER_PORT = 65000;
    public static final int CLIENT_PORT = 65530;
    public static final String SERVER_ADDRESS = "230.0.0.1";
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            //Dang nhap//
            System.out.println("--- LOGIN ---");
            System.out.print("Nhap ten cua ban: ");
            String myName = input.nextLine();
            
            MulticastSocket ms = new MulticastSocket(SERVER_PORT);
            InetAddress serverId = InetAddress.getByName(SERVER_ADDRESS);
            ms.joinGroup(serverId);
            
            System.out.println("Ban da tham gia nhom chat");
            Receive inp = new Receive(ms);
            inp.start();
            while(true){
                //Gui thong diep len nhom Multicast//
                String strtmp = input.nextLine();
                String str1 = myName + ":#"+ strtmp;
                if(strtmp.compareTo("Exit")==0){
                    inp.stop();
                    
                    str1 = myName + "#da roi khoi nhom chat";
                    byte[] dataOut = str1.getBytes();
                    DatagramPacket out = new DatagramPacket(dataOut, dataOut.length,serverId,SERVER_PORT);
                    ms.send(out);
                    
                    break;
                }
                
                byte[] dataOut = str1.getBytes();
                DatagramPacket out = new DatagramPacket(dataOut, dataOut.length,serverId,SERVER_PORT);
                ms.send(out);
                
                //DK Break + roi khoi nhom chat//
                        
            }
            ms.leaveGroup(serverId);
            System.out.println("Ban da roi khoi nhom chat");
            ms.close();
        } catch (IOException ex) {
            System.out.println("Multi-Socket error: "+ex.getMessage());
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
                //System.out.println("Ben trong ham Run");
                System.out.println(a[0] + " " + a[1]);
            }
        } catch (UnknownHostException ex) {
            System.out.println("Unknown Host Error: "+ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Receive Error: "+ex.getMessage());
        }
    }
}