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
import java.sql.Timestamp;

public class ClientTCP {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket sock = new Socket(InetAddress.getByName("insa-11269"), 6668);
        PrintWriter out = new PrintWriter(sock.getOutputStream(),true);
        out.print("Message\nAlice Username\nBob Username\n"+ (new Timestamp(System.currentTimeMillis())).toString() + "\nMessage IMPORTANTISSIME:\nHello World!");
        out.close();
        sock.close();
    }
}