package Application;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Address {
 private InetAddress IP;
 private String Pseudo;
 private String Username;
 
protected Address(InetAddress ip, String pseudo, String un) {
	IP = ip;
	setPseudo(pseudo);
	setUsername(un);
}

protected Address(String pseudo, String un) {
	try {
		this.IP = InetAddress.getByAddress(DBLocale.getPcIP());
	} catch (UnknownHostException e) {
		this.IP = null;
	}
	setPseudo(pseudo);
	setUsername(un);
}
 



protected InetAddress getIP() {
	return IP;
}

protected void setIP(InetAddress iP) {
	IP = iP;
}


protected String getPseudo() {
	return Pseudo;
}


protected void setPseudo(String pseudo) {
	Pseudo = pseudo;
}


protected String getUsername() {
	return Username;
}


protected void setUsername(String username) {
	Username = username;
}


}
