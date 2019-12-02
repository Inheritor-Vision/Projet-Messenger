package Application;

public class Message {
	Address source;
	Address destinataire;
	String msg;
	Time date;
	
	protected Message(Address dest, Address src, String msgs) {
		this.source = src;
		this.destinataire = dest;
		this.msg = msgs;
		this.date = new Time();
	}
	
}
