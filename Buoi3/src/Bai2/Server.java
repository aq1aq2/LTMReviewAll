package Bai2;
import java.io.*;
import java.net.*;


public class Server {
    private static final int SERVERPORT=65530;
    public static void main(String[] args) {
        try {
            DatagramSocket ds = new DatagramSocket(SERVERPORT);
            System.out.println("Server started.");
            while(true){
                byte[] buffer = new byte[60000];
                DatagramPacket in = new DatagramPacket(buffer,buffer.length);
                ds.receive(in);
                String str1 = new String(in.getData(),0,in.getLength());
                System.out.println("Nhan tu client: "+str1);
                
                String str2 = null;
                String a[] = str1.split("[ ]");
                
                if(a.length==2){
                    if(a[0].compareTo("READ")==0){
                        try{
                            File f1 = new File(a[1]);
                            System.out.println("Kich thuoc file: "+f1.length()+"bytes");
                            if(f1.length()<65536){                            
                                try (FileInputStream fin = new FileInputStream(f1)) {
                                    byte[] data = new byte[fin.available()];
                                    fin.read(data);
                                    DatagramPacket out = new DatagramPacket(data,data.length,in.getAddress(),in.getPort());
                                    ds.send(out);
                                    fin.close();
                                }
                            }
                            else str2="Kich thuoc file qua lon!";
                        }
                        catch (IOException ex) {
                            str2="Duong dan khong ton tai";
                        }
                    }else str2="Loi cu phap - Sai tu khoa";
                }else str2="Loi cu phap - Sai dinh dang";
                
                if(str2!=null){
                    System.out.println(str2);
                    byte[] data = str2.getBytes();
                    DatagramPacket out = new DatagramPacket(data, data.length-1,in.getAddress(),in.getPort());
                    ds.send(out);
                    str2 = new String(out.getData(),0,out.getLength());
                    System.out.println(str2);
                }
            }
        } catch (SocketException ex) {
            System.out.println("Datagram Socket error: "+ex.getMessage());
        } catch (UnknownHostException ex) {
            System.out.println("Unknown Host Address error: "+ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IO Error: "+ex.getMessage());
        }

    }
}
