package Application;

public class Controller {
	
	private Account loggedAccount;
	
	//private ... server;
	
	private UserInterface userInterface;
	
	private InternalSocket socket;
	
	//private Conversation conversation;
	
	public Controller() {
		this.userInterface = new UserInterface();
		this.userInterface.co = this;
		
	}
	
	
	public InternalSocket getSocket() {
		return socket;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controller co = new Controller();
		
	}





	

}
