package Application;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class DBLocale {
	//https://www.tutorialspoint.com/sqlite/sqlite_java.htm
	//sqlite java
	Connection coDB;
	ArrayList<Address> knownUsers;
	public DBLocale() {
		this.coDB = null;
		this.knownUsers = null;
	}
	public  DBLocale(String nomDB) {
		this.coDB = connectionDB(nomDB);
		//this.knownUsers = this.getknownUsers();
	}
	
	private Connection connectionDB(String nomDB) {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./"+nomDB);
			System.out.println("DBLocale: Database opened successfully");
		} catch (ClassNotFoundException e) {
			System.out.println("DBLocale: Error connectionDB, class not Found");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DBLocale: Error connectionDB, connection failed to db");
			e.printStackTrace();
		}
		return c;
	}
	
	private ArrayList<Address> getknownUsers(){
		ArrayList<Address> temp = new ArrayList<Address>();
		Statement stmt;
		try {
			stmt = coDB.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM knownUsers;");
			while(rs.next()) {
				String username = rs.getString("username");
				String pseudo = rs.getString("pseudo");
				byte[] address = rs.getBytes("address");
				temp.add(new Address(InetAddress.getByAddress(address), pseudo, username));
			}
			rs.close();
		    stmt.close();
		} catch (SQLException e) {
			System.out.println("DBlocal: Error getknownUsers, SQL ERROR");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			System.out.println("DBlocal: Error getknownUsers, Unknown Host Error");
			e.printStackTrace();
		}
		return temp;
		
	}
	
	private Address getUserAddress(String corres) throws IOException {
		Iterator<Address> iter = this.knownUsers.iterator();
		Address res = null;
		Boolean fin = false;
		while (iter.hasNext() && !fin) {
			res = iter.next();
			if (res.getUsername() == corres) {
				fin  =true;
			}
		}
		if(res != null) {
			return res;
		}else {
			throw new IOException("DBLocal: Error geteUserAddress");
		}
	}
	
	protected Conversation getConversation(String corres) {
		Conversation conv = null;
		try {
			conv = new Conversation(this.getUserAddress(corres));
		} catch (IOException e1) {
			System.out.println("DBLocal: Error getUserAddress");
			e1.printStackTrace();
		}
		try {
			Statement stmt = coDB.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM conversation WHERE corres=" + corres + ";");
			if (rs.next() == false) {
				System.out.println("DBLocal: Error getConv return empty set");
			}else {
				 do {
					Boolean isSender = rs.getBoolean("isSender");
					Boolean isNew = rs.getBoolean("isNew");
					java.sql.Timestamp ts = rs.getTimestamp("timestamp");
					String msg = rs.getString("message");
					conv.addMessage(new Message(isSender,msg,isNew, ts));
				}while(rs.next());
				rs.close();
			    stmt.close(); 
			}
			
		} catch (SQLException e) {
			System.out.println("DBlocal: Error getMessage");
			e.printStackTrace();
		}
		return conv;
	}
	
	private int BoolToInt(boolean a) {
		if (a == true) {
			return 1;
		}else {
			return 0;
		}
	}
	// id corres isSender isNew ts msg 
	protected void setMessage(Message msg, String username, int id) {
		try {
			Statement stmt = coDB.createStatement();
			String tat = "INSERT INTO conversation (id, corres, isSender, isNew, timestamp, message) VALUES ('" + id + "', '"+ username + "', '" + BoolToInt(msg.getIsEnvoyeur()) + "', '" + BoolToInt(msg.getIsNew()) + "', '" + msg.getTimestamp() + 
					"', '" + msg.getMsg() + "');";
			ResultSet rs = stmt.executeQuery(tat);
		} catch (SQLException e) {
			System.out.println("DBLocal: Error setMessage SQL");
			e.printStackTrace();
		}
		
	}

}
