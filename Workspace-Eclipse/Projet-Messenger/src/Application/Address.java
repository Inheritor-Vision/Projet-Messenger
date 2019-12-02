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
