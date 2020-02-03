package Application;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Map;

import Common.Address;
import Common.Tools;

public class Controller {
	
	private Account loggedAccount;
	
	//private ... server;
	
	private UserInterface userInterface;
	
	private InternalSocket socket;
	
	private Conversation conversation;
	
	private DBLocale db;
	
	//private DBCentrale dbc;
	
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
		this.db = new DBLocale();
		this.userInterface = new UserInterface(/*this,this.db*/);
		this.userInterface.co = this;
		this.userInterface.db = this.db;
		DBCentrale.InitPullAccount();
		//this.dbc = new DBCentrale();
		
		
		
		
		
		
		
	}
	
	
	public InternalSocket getSocket() {
		return socket;
	}
	
	public void setSocket(InternalSocket so) {
		this.socket=so;
	}
	
	public Conversation getConversation() {
		return conversation;
	}
	
	public void setConversation(Conversation conv) {
		this.conversation=conv;
	}
	
	public Account getLoggedAccount() {
		return loggedAccount;
	}
	public UserInterface getUI() {
		return userInterface;
	}
	
	public void setLoggedAccount(Account acc) {
		this.loggedAccount=acc;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controller co = new Controller();
		
		System.out.println(Tools.getPcIP()[0]);
		System.out.println(Tools.getPcIP()[1]);
		System.out.println(Tools.getPcIP()[2]);
		System.out.println(Tools.getPcIP()[3]);
		
		co.db.printAllTable();
		
		/*try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Address simeon = new Address(null,"simeon_p","simeon_u");
		Address kevin = new Address(null,"kevin_p","kevin_u");
		Address yuyuan = new Address(null,"yuyuan_p","yuyuan_u");
		co.getSocket().getUserList().put("simeon_u", simeon);
		co.getSocket().getUserList().put("kevin_u", kevin);
		co.getSocket().getUserList().put("yuyuan_u", yuyuan);
		
		for (Map.Entry<String,Address> entry : co.getSocket().getUserList().entrySet()) {
			 System.out.println(entry.getValue().getPseudo());
			 
		}
		
		System.out.println(co.getSocket().getUserList().size());*/
		
		
		//test utililisateurs connectes//
		/*Address valentin = new Address(null,"Valentin","Valentin_u");
		Address simeon = new Address(null,"simeon","simeon_u");
		Address kevin = new Address(null,"kevin","kevin_u");
		Address yuyuan = new Address(null,"yuyuan","yuyuan_u");
		Address ds = new Address("D4rk-Sasuk3","ds_u");
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
		ArrayList<Address> utilnco = new ArrayList<Address>();
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
		utilnco.add(c);
		utilnco.add(d);
		utilnco.add(d1);
		utilnco.add(d2);
		utilnco.add(d3);
		utilnco.add(d4);
		utilnco.add(d5);
		utilnco.add(d6);
		utilnco.add(d7);
		co.userInterface.connectedUserList = utilco;
		co.userInterface.conversation_nc = utilnco;
		
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
		System.out.println("test1");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test2");
		
		Address neww = new Address(null,"new","new_u");
		utilco.add(neww);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test3");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		co.userInterface.recevoirmessageUI(new Message(false,"je suis la",true));
		////
		
	*/	
		/*while (co.getLoggedAccount() == null) {
		try {
			Thread.sleep(10000);
			System.out.println("acc null");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		System.out.println("user not null");
		Address bbb = new Address("bbb_p","bbb_u");
		co.db.setKnownUser(bbb,co.getLoggedAccount().getUsername());
		Address ds = new Address("ds_p","ds_u");
		co.db.setKnownUser(ds,co.getLoggedAccount().getUsername());
		co.db.setMessage(new Message(false,"bonjour",true), "bbb_u", "123");
		co.db.setMessage(new Message(false,"hello",true), "ds_u", "123");
		ArrayList<Address> adr = co.db.getknownUsers(co.getLoggedAccount().getUsername());
		for (int i=0;i<adr.size();i++) {
			System.out.println(adr.get(i).getPseudo());
		}
		Address aaa = new Address("aaa_p","aaa_u");
		//co.userInterface.connectedUserList.add(aaa);
		try {
			Thread.sleep(20000);
			System.out.println("DONE");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		co.userInterface.connectedUserList.add(aaa);
		Message msg = new Message(false,"hahaha",true);
		co.userInterface.recevoirmessageUI(msg,aaa);
		//co.db.setMessage(msg, "aaa_u", "123");
		co.userInterface.connectedUserList.remove(aaa);*/
		while(true) {}
	}





	

}
