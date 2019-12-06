package Application;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

public class testMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InternalSocket temp1 = new InternalSocket("tempUser");
		try {
			List<InetAddress> tempL = temp1.listAllBroadcastAddresses();
			for (int i = 0; i< tempL.size(); i++) {
				System.out.println(tempL.get(i));
			}
		} catch (SocketException e) {
			System.out.println("Error main");
			e.printStackTrace();
		}
		
	}

}
