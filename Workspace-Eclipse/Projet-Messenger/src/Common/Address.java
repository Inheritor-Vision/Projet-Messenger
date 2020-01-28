package Common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;



public class Address {
 private InetAddress IP;
 private String Pseudo;
 private String Username;
 private Timestamp ts;
 
public Address(InetAddress ip, String pseudo, String un) {
	IP = ip;
	setPseudo(pseudo);
	setUsername(un);
	setTs(new Timestamp(System.currentTimeMillis()));
}

public Address(String pseudo, String un) {
	try {
		this.IP = InetAddress.getByAddress(Tools.getPcIP());
	} catch (UnknownHostException e) {
		this.IP = null;
	}
	setPseudo(pseudo);
	setUsername(un);
	setTs(new Timestamp(System.currentTimeMillis()));
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

public String addrToString() {
	return(((int)this.IP.getAddress()[0])& 0xff) + "." + (((int)this.IP.getAddress()[1])& 0xff) + "." + (((int)this.IP.getAddress()[2])& 0xff) + "." + (((int)this.IP.getAddress()[3])& 0xff);
}

public Timestamp getTs() {
	return ts;
}

public void setTs(Timestamp ts) {
	this.ts = ts;
}
}
