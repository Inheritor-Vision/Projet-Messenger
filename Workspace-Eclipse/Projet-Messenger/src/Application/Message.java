package Application;
import java.sql.Timestamp;

public class Message {
	Boolean isEnvoyeur; // 1=envoyeur / 0=receveur
	String msg;
	Timestamp date;
	
	protected Message(Boolean isSender , String msgs) {
		this.isEnvoyeur = isSender;
		this.msg = msgs;
		this.date = new Timestamp(System.currentTimeMillis());
	}
	
	protected String getMsg() {
		return msg;
	}
	
	protected Timestamp getTimestamp() {
		return date;
	}

	protected Boolean getIsEnvoyeur() {
		return isEnvoyeur;
	}
}
