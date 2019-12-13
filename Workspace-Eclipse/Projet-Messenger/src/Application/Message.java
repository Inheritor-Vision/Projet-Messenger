package Application;
import java.sql.Timestamp;

public class Message {
	Boolean isEnvoyeur; // 1=envoyeur / 0=receveur
	String msg;
	Timestamp date;
	Boolean isNew;
	
	protected Message(Boolean isSender , String msgs, Boolean _isNew) {
		this.isEnvoyeur = isSender;
		this.msg = msgs;
		this.date = new Timestamp(System.currentTimeMillis());
		this.isNew = _isNew;
	}
	protected Message(Boolean isSender , String msgs, Boolean _isNew, Timestamp ts) {
		this.isEnvoyeur = isSender;
		this.msg = msgs;
		this.date = ts;
		this.isNew = _isNew;
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
	protected Boolean getIsNew() {
		return isNew;
	}
}
