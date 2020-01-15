package Application;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class testMain {

	public static void main(String[] args) throws UnknownHostException, InterruptedException {
		ArrayList<Address> temp3 = new ArrayList<Address>();
		Address temp1 =new Address("lol","lol"); 
		Address temp2 =new Address("lol","lol"); 
		temp3.add(temp1);
		System.out.println(temp3.remove(temp1));		
		// TODO Auto-generated method stub
		/*DBLocale db = new DBLocale();
		db.setKnownUser(new Address(InetAddress.getLocalHost(), "Bob psd", "Bob usn"), "Alice usn");
		db.setKnownUser(new Address(InetAddress.getLocalHost(), "Eve psd", "Eve usn"), "Alice usn");
		db.updatePseudo("Dark Eve", "Eve psd", "Eve usn", "Alice usn");
		System.out.println(db.getSpecificKnownUser("Alice usn", "Eve usn").getPseudo());*/
		
		
		//temp.sendNewPseudo("Dark Alice", "Alice psd");
		
		/*DBLocale db = new DBLocale("appDb");
		db.setAccount(new Account("Alice usn", "Alice pswd", "Alice psd", new Address(InetAddress.getLocalHost(),"Alice psd","Alice usn")));
		db.setAccount(new Account("Bob usn", "Bob pswd", "Bob psd", new Address(InetAddress.getLocalHost(),"Bob psd","Bob usn")));
		db.setAccount(new Account("Eve usn", "Eve pswd", "Eve psd", new Address(InetAddress.getLocalHost(),"Eve psd","Eve usn")));
		
		db.setMessage(new Message(true,"Ceci est un test", true), "Alice", "Bob");
		db.setMessage(new Message(true,"Ceci est une reponse test", true), "Bob", "Alice");
		
		db.setKnownUser(new Address(InetAddress.getLocalHost(),"Bob psd","Bob usn"), "Alice");
		db.setKnownUser(new Address(InetAddress.getLocalHost(),"Eve psd","Eve usn"), "Alice");
		db.setKnownUser(new Address(InetAddress.getLocalHost(),"Eve psd","Eve usn"), "Bob");
		db.printAllTable();*/
		//System.out.println(temp.get(0).getUsername() + "\n" + temp.get(0).getPseudo());
		/*
		 //Test send reception msg
		InternalSocket temp1 = new InternalSocket("tempUser");
		System.out.println(temp1.connectedUserList.size());
		temp1.startReceiverThread();
		*/
		/*
		// Test UDP Connecte
		InternalSocket temp1 = new InternalSocket("tempUser");
		Address addr = new Address(InetAddress.getLocalHost(), "testPS", "testUN" );
		Account test = new Account("testUN", "testPWd", "testPS", addr);
		temp1.sendConnected(test);*/
		
		/*//TEST TCP SEND
		Address addr = new Address(InetAddress.getLocalHost(), "testPS", "testUN" );
		Address addrDest = new Address(InetAddress.getByName("insa-08131"), "testPS", "testUN" );
		Account test = new Account("testUN", "testPWd", "testPS", addr);
		Message msg = new Message(true,"SALUT !\n LOL", true);
		temp1.connectedUserList.add(addrDest);
		temp1.sendMessage(msg, "testUN");*/
		
		
		/*//TEST TCP RCV
		

		
		InetAddress temp2 = Inet4Address.getLocalHost();
		System.out.println(temp2.getAddress()[0] + "." + temp2.getAddress()[1] +"." + temp2.getAddress()[2] + "." +temp2.getAddress()[3]);
		
		
		DBLocale db = new DBLocale("test.db");
		InetAddress temp3 = InetAddress.getByAddress(db.getPcIP());
		System.out.println(temp3.getAddress()[0] + "." + temp3.getAddress()[1] +"." + temp3.getAddress()[2] + "." +temp3.getAddress()[3]);*/
		
		/* Test COnversation
		db.knownUsers.add(new Address(InetAddress.getLocalHost(), "usertest", "unused champ"));
		String me = "me";
		String username = "usertest";		
		Message msg = new Message(true, "Ceci est le message de test.", true);
		db.setMessage(msg, me,username);
		msg = new Message(true, "Ceci est un autre message de test.", true);
		db.setMessage(msg, me,username);
		
		String enemy = "enemy";
		msg = new Message(true, "Ceci est un message de l'ennemie", true);
		db.setMessage(msg, me,enemy);
		
		Conversation conv = db.getConversation(me,enemy);
		Message[] tempz = conv.getAllMessages();
		for (int x = 0; x <conv.getConvSize(); x++) {
			System.out.println("NEW MSG: " + tempz[x].msg);
		}
		*/
		
		
	/*//test account
		Account acc = new Account ("Louis","0", "Lolodu82", new Address(InetAddress.getLocalHost(), "Lolodu82/17","Louis"));
		Account acc2 = new Account ("Louise","0", "Lololettedu82/17", new Address(InetAddress.getLocalHost(), "Lololettedu82/17","Louise"));
		db.setAccount(acc);
		db.setAccount(acc2);
		
		Account acc3 = db.getAccount("Louis", "0");
		System.out.println("ACCOUNT: UN:" + acc3.getUsername() + ". PS:" + acc3.getPseudo() + ". PW:" + acc3.getPassword() + "."); 
	*/
		
		
		
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
		
		while(true)  {
			
		}
		
	}

}
