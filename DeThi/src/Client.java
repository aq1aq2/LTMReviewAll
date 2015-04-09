import java.io.*;
import java.util.*;
import java.net.*;
public class Client {
    private static final int SERVER_PORT = 65530;
    private static final String SERVER_ID = "localhost";
    private static final int MULTICAST_PORT = 8888;
    private static final String MULTICAST_ID = "230.0.0.1";
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Client started");
        try {
            ///---Tham gia nhom Multicast---///
            MulticastSocket ms = new MulticastSocket(MULTICAST_PORT);
            InetAddress msId = InetAddress.getByName(MULTICAST_ID);
            InetAddress svId = InetAddress.getByName(SERVER_ID);
            ms.joinGroup(msId);
            System.out.println("Joined into "+msId.getHostAddress());
            while(true){
                ///---Gui yeu cau---///
                System.out.println("--- B1203897 ---");
                System.out.println("VD: + 100 200 google.com");
                System.out.print("Nhap yeu cau: ");
                String str1 = input.nextLine();
                byte[] data = str1.getBytes();
                DatagramPacket out = new DatagramPacket(data,data.length,svId,SERVER_PORT);
                ms.send(out);
                
                if(str1.compareTo("Exit")==0) break;//Lenh thoat
                
                ///---Nhan yeu cau---///
                byte[] buffer = new byte[60000];
                DatagramPacket in = new DatagramPacket(buffer,buffer.length);
                ms.receive(in);
                String str2 = new String(in.getData(),0,in.getLength());
                System.out.println("Ket qua: "+str2);
            }
            ms.leaveGroup(msId);
            ms.close();
        } catch (IOException ex) {
            System.out.println("Multicast Socket Error: "+ex.getMessage());
        }
        
    }
}
