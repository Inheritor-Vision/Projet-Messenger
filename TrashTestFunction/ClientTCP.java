import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTCP {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket sock = new Socket(InetAddress.getLocalHost(), 6668);
        PrintWriter out = new PrintWriter(sock.getOutputStream());
        out.println("ceci est un test de TCP BRO\n stop\n");
        sock.close();
    }
}