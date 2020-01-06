import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.Timestamp;

public class ClientUDP {

    public static void main(String[] args) throws IOException, SocketException{
        int port = 6000;
        DatagramSocket dgramSocket= new DatagramSocket(port);
        System.out.println("ClientUDP: Server Created on port " + port );
        String message = "Connected\nAlice Pseudo\nAlice Username\n" + (new Timestamp(System.currentTimeMillis())).toString();
        
        DatagramPacket outPkt = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName("insa-11269"), 6666);
        dgramSocket.send(outPkt);
        System.out.println("ClientUDP: message sent: " + message );
        dgramSocket.close();
    }
}