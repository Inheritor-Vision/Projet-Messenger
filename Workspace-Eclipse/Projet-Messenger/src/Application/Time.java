package Application;

import java.sql.Timestamp;

public class Time {
	private Timestamp time;
	
	protected Time() {
		this.setTime();
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime() {
		this.time = new Timestamp(System.currentTimeMillis());
	}
	
	
}
