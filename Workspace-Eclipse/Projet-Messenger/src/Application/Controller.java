package Application;

import java.net.SocketException;
import java.util.ArrayList;

public class Controller {
	
	private Account loggedAccount;
	
	//private ... server;
	
	private UserInterface userInterface;
	
	private InternalSocket socket;
	
	private Conversation conversation;
	
	public Controller() {
		/*//test utililisateurs connectes//
		Address valentin = new Address(null,"Valentin_p","Valentin_u");
		Address simeon = new Address(null,"simeon_p","simeon_u");
		ArrayList<Address> utilco = new ArrayList<Address>();
		utilco.add(valentin);
		utilco.add(simeon);
		.0
		//*/
		this.conversation = new Conversation(new Address(null,"test","test"));
		this.userInterface = new UserInterface();
		this.userInterface.co = this;
		
		
		
		
		
	}
	
	
	public InternalSocket getSocket() {
		return socket;
	}
	
	public Conversation getConversation() {
		return conversation;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controller co = new Controller();
		
		//test utililisateurs connectes//
		Address valentin = new Address(null,"Valentin","Valentin_u");
		Address simeon = new Address(null,"simeon","simeon_u");
		Address kevin = new Address(null,"kevin","kevin_u");
		Address yuyuan = new Address(null,"yuyuan","yuyuan_u");
		Address ds = new Address(null,"D4rk-Sasuk3","ds_u");
		Address j = new Address(null,"jean","jean_u");
		Address t = new Address(null,"tom","tom_u");
		Address l = new Address(null,"leo","leo_u");
		Address y = new Address(null,"yan","yan_u");
		Address le = new Address(null,"lea","lea_u");
		Address em = new Address(null,"emma","emma_u");
		Address a = new Address(null,"a","a_u");
		Address b = new Address(null,"b","b_u");
		Address c = new Address(null,"c","c_u");
		Address d = new Address(null,"d","d_u");
		Address d1 = new Address(null,"d1","d1_u");
		Address d2 = new Address(null,"d2","d2_u");
		Address d3 = new Address(null,"d3","d3_u");
		Address d4 = new Address(null,"d4","d4_u");
		Address d5 = new Address(null,"d5","d5_u");
		Address d6 = new Address(null,"d6","d6_u");
		Address d7 = new Address(null,"d7","d7_u");
		ArrayList<Address> utilco = new ArrayList<Address>();
		utilco.add(valentin);
		utilco.add(simeon);
		utilco.add(kevin);
		utilco.add(yuyuan);
		utilco.add(ds);
		utilco.add(j);
		utilco.add(t);
		utilco.add(l);
		utilco.add(y);
		utilco.add(le);
		utilco.add(em);
		utilco.add(a);
		utilco.add(b);
		utilco.add(c);
		utilco.add(d);
		utilco.add(d1);
		utilco.add(d2);
		utilco.add(d3);
		utilco.add(d4);
		utilco.add(d5);
		utilco.add(d6);
		utilco.add(d7);
		co.userInterface.connectedUserList = utilco;
		
		//test conversation
		co.conversation.setDestinataire(ds);
		co.conversation.addMessage(new Message(true, "bonjour",true));
		co.conversation.addMessage(new Message(false, "yo",true));
		co.conversation.addMessage(new Message(true, "ma phrase est très long exprès pour tester l'affichage des messages\n plutot long pas besoin de la lire jsuqu'au bout ça n'a pas trop d'interet hahahahahahah ahaha hahahah ahahahah ahahahahahahahahahahahaha",true));
		co.conversation.addMessage(new Message(false, "ma phrase aussi est très long exprès pour tester l'affichage des messages plutot long pas besoin de la lire jsuqu'au bout ça n'a pas trop d'interet hahahahahahah ahaha hahahah ahahahah ahahahahahahahahahahahaha",true));
		co.conversation.addMessage(new Message(true, "ok",true));
		//co.conversation.addMessage(new Message(true, "1234567890123456789012345678901234567890123456789012345678901234567890123456789",true));
		//co.conversation.addMessage(new Message(false, "1234567890123456789012345678901234567890123456789012345678901234567890123456789",true));
		//
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test");
		
		Address neww = new Address(null,"new","new_u");
		utilco.add(neww);
		
		
		////
		
		while(true) {
			
		}
		
	}





	

}
