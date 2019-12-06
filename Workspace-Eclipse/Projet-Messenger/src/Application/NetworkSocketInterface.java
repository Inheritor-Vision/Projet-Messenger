package Application;
import java.util.ArrayList;

public interface NetworkSocketInterface {
	
 public void sendConnected(Account loggedAccount);
 
 public void sendDisconnected(Account loggedAccount);
 
 public void isServerUp();
 
 public ArrayList<Address> getUserList();
 
 public void sendMessage(Message msg, String Username);
 
 public void startReceiverThread(Controller cont);
 
 public ArrayList<Conversation> getHistorique();
 
 public void sendHistorique(ArrayList<Conversation> lh);

 
 void setUserList(ArrayList<Address> ul);
 
 


}
