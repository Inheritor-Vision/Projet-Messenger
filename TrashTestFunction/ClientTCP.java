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
        Socket sock = new Socket(InetAddress.getByName("insa-08133"), 6668);
        PrintWriter out = new PrintWriter(sock.getOutputStream(),true);
        out.println("ceci est un test de TCP BRO\nstop\n");
        out.close();
        sock.close();
    }
}