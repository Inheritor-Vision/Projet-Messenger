package Application;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

public class testMain {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		InternalSocket temp1 = new InternalSocket("tempUser");
		Address addr = new Address(InetAddress.getLocalHost(), "testPS", "testUN" );
		Address addrDest = new Address(InetAddress.getByName("insa-08134"), "testPS", "testUN" );
		Account test = new Account("testUN", "testPWd", "testPS", addr);
		Message msg = new Message(true,"SALUT !\n LOL");
		temp1.connectedUserList.add(addrDest);
		temp1.sendMessage(msg, "testUN");
		
	}

}
