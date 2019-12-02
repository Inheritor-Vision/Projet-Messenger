package Application;
import java.sql.Timestamp;

public class Message {
	Address source;
	Address destinataire;
	String msg;
	Timestamp date;
	
	protected Message(Address dest, Address src, String msgs) {
		this.source = src;
		this.destinataire = dest;
		this.msg = msgs;
		this.date = new Timestamp(System.currentTimeMillis());
	}
	
	protected String getMsg() {
		return msg;
	}
	
	protected Address getSource() {
		return source;
	}
	protected Address getDestinataire() {
		return destinataire;
	}
	
	protected Timestamp getTimestamp() {
		return date;
	}
}
