package Common;

import java.net.InetAddress;
import java.net.UnknownHostException;



public class Address {
 private InetAddress IP;
 private String Pseudo;
 private String Username;
 
public Address(InetAddress ip, String pseudo, String un) {
	IP = ip;
	setPseudo(pseudo);
	setUsername(un);
}

public Address(String pseudo, String un) {
	try {
		this.IP = InetAddress.getByAddress(Tools.getPcIP());
	} catch (UnknownHostException e) {
		this.IP = null;
	}
	setPseudo(pseudo);
	setUsername(un);
}
 



public InetAddress getIP() {
	return IP;
}

public void setIP(InetAddress iP) {
	IP = iP;
}


public String getPseudo() {
	return Pseudo;
}


public void setPseudo(String pseudo) {
	Pseudo = pseudo;
}


public String getUsername() {
	return Username;
}


public void setUsername(String username) {
	Username = username;
}


}
