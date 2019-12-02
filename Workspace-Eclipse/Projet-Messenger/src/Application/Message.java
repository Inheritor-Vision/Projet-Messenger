package Application;
import java.sql.Timestamp;

public class Message {
	Boolean isEnvoyeur;
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

	public Boolean getIsEnvoyeur() {
		return isEnvoyeur;
	}
}
