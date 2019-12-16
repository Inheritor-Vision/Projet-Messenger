package Application;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class testMain {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		//InternalSocket temp1 = new InternalSocket("tempUser");
		/*Address addr = new Address(InetAddress.getLocalHost(), "testPS", "testUN" );
		Address addrDest = new Address(InetAddress.getByName("insa-08134"), "testPS", "testUN" );
		Account test = new Account("testUN", "testPWd", "testPS", addr);
		Message msg = new Message(true,"SALUT !\n LOL");
		temp1.connectedUserList.add(addrDest);
		temp1.sendMessage(msg, "testUN");*/
		
		//temp1.startReceiverThread();

		
		InetAddress temp2 = Inet4Address.getLocalHost();
		System.out.println(temp2.getAddress()[0] + "." + temp2.getAddress()[1] +"." + temp2.getAddress()[2] + "." +temp2.getAddress()[3]);
		
		DBLocale db = new DBLocale("test.db");
		
		
		db.knownUsers.add(new Address(InetAddress.getLocalHost(), "usertest", "unused champ"));
		String username = "usertest";		
		Message msg = new Message(true, "Ceci est le message de test.", true);
		db.setMessage(msg, username);
		msg = new Message(true, "Ceci est un autre message de test.", true);
		db.setMessage(msg, username);
		
		String enemy = "enemy";
		msg = new Message(true, "Ceci est un message de l'ennemie", true);
		db.setMessage(msg, enemy);
		
		Conversation conv = db.getConversation(enemy);
		Message[] tempz = conv.getAllMessages();
		for (int x = 0; x <conv.getConvSize(); x++) {
			System.out.println("NEW MSG: " + tempz[x].msg);
		}
		
		
		
		
		/* //TEST KNOWN USER 
		Address add = new Address(InetAddress.getLocalHost(),"pseudal", "usernal");
		db.setKnownUser(add);
		add = new Address(InetAddress.getLocalHost(),"dimitriax", "dimitri");
		db.setKnownUser(add);
		add = new Address(InetAddress.getLocalHost(),"GAUUUDIAX", "gauthier");
		db.setKnownUser(add);
		add = new Address(InetAddress.getLocalHost(),"ARNAX", "arnaud");
		db.setKnownUser(add);
		add = new Address(InetAddress.getLocalHost(),"DARK_SASUK3", "simeon");
		db.setKnownUser(add);
		ArrayList<Address> LOL = db.getknownUsers();		
		Iterator<Address> iter = LOL.iterator();
		while(iter.hasNext()) {
			Address temp = iter.next();
			System.out.println("NEW USER IN LIST "+ temp.getPseudo() +"; "+ temp.getUsername() +"; " + temp.getIP());
		}*/
		
		
		
		
		/*Message msg = new Message(true, "Ceci est un test", true);
		String username = "lol";
		int id = 15;
		db.setMessage(msg, username, id);*/
		
	}

}
