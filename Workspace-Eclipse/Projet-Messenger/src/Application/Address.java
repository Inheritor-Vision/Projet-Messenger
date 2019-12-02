package Application;

public class Address {
 private String IP;
 private String NickName;
 
protected Address(String ip, String nickname) {
	IP = ip;
	NickName = nickname;
}
 

protected String getIP() {
	return IP;
}

protected void setIP(String iP) {
	IP = iP;
}

protected String getNickName() {
	return NickName;
}

protected void setNickName(String nickName) {
	NickName = nickName;
}
}
