package Application;

import java.net.SocketException;
import java.util.ArrayList;

public class Controller {
	
	private Account loggedAccount;
	
	//private ... server;
	
	private UserInterface userInterface;
	
	private InternalSocket socket;
	
	//private Conversation conversation;
	
	public Controller() {
		/*//test utililisateurs connectes//
		Address valentin = new Address(null,"Valentin_p","Valentin_u");
		Address simeon = new Address(null,"simeon_p","simeon_u");
		ArrayList<Address> utilco = new ArrayList<Address>();
		utilco.add(valentin);
		utilco.add(simeon);
		//*/
		
		this.userInterface = new UserInterface();
		this.userInterface.co = this;
		
		
		
		
	}
	
	
	public InternalSocket getSocket() {
		return socket;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controller co = new Controller();
		
		//test utililisateurs connectes//
		Address valentin = new Address(null,"Valentin_p","Valentin_u");
		Address simeon = new Address(null,"simeon_p","simeon_u");
		ArrayList<Address> utilco = new ArrayList<Address>();
		utilco.add(valentin);
		utilco.add(simeon);
		co.userInterface.connectedUserList = utilco;
		
		////
		
		
		/////
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test");
		////
		
	}





	

}
