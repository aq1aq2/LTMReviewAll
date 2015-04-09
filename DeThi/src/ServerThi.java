import java.io.*;
import java.net.*;

public class ServerThi {
    public static final int SERVER_PORT = 65530;
    public static final String SERVER_ID = "192.168.137.1"; //Sua lai dia chi may chu
    public static final int MULTICAST_PORT = 8888;
    public static final String MULTICAST_ID = "230.0.0.2";
    public static void main(String[] args) {
        try {
            DatagramSocket ds = new DatagramSocket(ServerThi.SERVER_PORT);
            InetAddress msId = InetAddress.getByName(ServerThi.MULTICAST_ID);
            System.out.println("Server Started");
            
            ///---Gui du lieu len nhom Multicast---///
            Send out = new Send(ds, msId);
            out.start();
            System.out.println("Sending data to Client.");
            
            ///---Nhan file ve tu Client---///
            ServerSocket ss = new ServerSocket(ServerThi.SERVER_PORT);
            System.out.println("Receiving files from Client.");
            while(true){
                Socket s = ss.accept();
                Receive in = new Receive(ds, msId,s);
                in.start();
            }
        } catch (SocketException ex) {
            System.out.println("Datagram Socket Error: "+ex.getMessage());
        } catch (UnknownHostException ex) {
            System.out.println("Unknown Host Error: "+ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Server Socket Error: "+ex.getMessage());
        }
    }
}

class Send extends Thread{
    DatagramSocket ds;
    InetAddress msId;
    public Send(DatagramSocket sub_ds, InetAddress sub_msId){
        ds = sub_ds;
        msId = sub_msId;
    }
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run(){
        try {
            while(true){
                Thread.sleep(1000);
                String str1 = "+ 311 1024 google.vn";
                byte[] data = str1.getBytes();
                DatagramPacket out = new DatagramPacket(data,data.length,msId,ServerThi.MULTICAST_PORT);
                ds.send(out);
                System.out.println("Data sent!");
            }
        } catch (SocketException ex) {
            System.out.println("Datagram Socket Error: "+ex.getMessage());
        } catch (UnknownHostException ex) {
            System.out.println("Unknown Host Error: "+ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IO Error: "+ex.getMessage());
        } catch (InterruptedException ex) {
            System.out.println("Sleep Error: "+ex.getMessage());
        }
    }
}

class Receive extends Thread{
    DatagramSocket ds;
    InetAddress msId;
    Socket s;
    public Receive(DatagramSocket sub_ds, InetAddress sub_msId, Socket ss){
        s=ss;
        ds = sub_ds;
        msId = sub_msId;
    }
    @Override
    public void run(){
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(s.getInputStream()));
            InetAddress id = s.getInetAddress();
            ///---Nhan file tu Client---///
            try {
                //--Nhan ten file tu Client--//
                String fName = is.readLine();
                File f1 = new File(fName);

                //--Tao thu muc luu file theo tung IP--//
                File dir = new File("D:\\LuuBaiLam\\"+id.getHostAddress());
                dir.mkdirs();

                //--Tao file nhan ve tu Client--//
                FileOutputStream fout = new FileOutputStream("D:\\LuuBaiLam\\"+id.getHostAddress()+"\\"+f1);
                while(true){
                    String tmp = is.readLine();
                    if(tmp.equals("EndOfFile")) break;
                    fout.write(tmp.getBytes());
                }
                fout.close();

                System.out.println("Received "+fName+" from "+id);
            } catch (IOException e) {
                System.out.println("Write File Error: "+e.getMessage());
            }
        } catch (SocketException ex) {
            System.out.println("Socket Error: "+ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IO Error: "+ex.getMessage());
        }
    }
}