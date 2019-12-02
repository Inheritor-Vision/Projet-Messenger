package Application;

public class Address {
 private String IP;
 private String Pseudo;
 private String Username;
 
protected Address(String ip, String pseudo, String un) {
	IP = ip;
	setPseudo(pseudo);
	setUsername(un);
}
 

protected String getIP() {
	return IP;
}

protected void setIP(String iP) {
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
