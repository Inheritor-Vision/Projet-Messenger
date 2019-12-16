package Application;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class DBLocale {
	//https://www.tutorialspoint.com/sqlite/sqlite_java.htm
	//sqlite java
	
	private static final String CHEMIN =  "./Db_Locale_Files";
	protected Connection coDB;
	protected ArrayList<Address> knownUsers = new ArrayList<Address>();
	public DBLocale() {
		this.coDB = null;
	}
	public  DBLocale(String nomDB) {
		this.coDB = connectionDB(nomDB);
		this.createTableKnownUsers();
		this.createTableConversations();
		this.createTableAccount();
		//this.knownUsers = this.getknownUsers();
	}
	
	private Connection connectionDB(String nomDB) {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + CHEMIN + "/"+nomDB);
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
	
	protected ArrayList<Address> getknownUsers(){
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM conversations WHERE corres='" + corres + "';");
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
	protected void setMessage(Message msg, String username) {
		try {
			String sql = "INSERT INTO conversations (corres, isSender, isNew, timestamp, message) VALUES (?,?,?,?,?)";
			PreparedStatement pstmt = this.coDB.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setInt(2,BoolToInt(msg.getIsEnvoyeur()));
			pstmt.setInt(3,BoolToInt(msg.getIsNew()));
			pstmt.setTimestamp(4, msg.getTimestamp());
			pstmt.setString(5,msg.getMsg());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("DBLocal: Error setMessage SQL");
			e.printStackTrace();
		}
		
	}
	
	protected void setKnownUser(Address add) {
		String sql = "INSERT INTO knownUsers (username,pseudo,address) VALUES (?,?,?)";
		try {
			PreparedStatement pstmt = this.coDB.prepareStatement(sql);
			pstmt.setString(1, add.getUsername());
			pstmt.setString(2, add.getPseudo());
			pstmt.setBytes(3, add.getIP().getAddress());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DBLocale: Error setKnownUser, creation pstmt or execute");
			e.printStackTrace();
		}
	}
	
	protected void createTableKnownUsers() {
		String sql = "CREATE TABLE IF NOT EXISTS knownUsers (\n"
                + "    id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "    username text NOT NULL,\n"
                + "    pseudo text NOT NULL,\n"
                + "    address blob NOT NULL\n"
                + ");";
		try {
			Statement stmt = this.coDB.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("DBLocale: Error createTableKnownUsers, create statement or execute");
			e.printStackTrace();
		}
		
	
	}
	
	protected void createTableConversations() {
		String sql = "CREATE TABLE IF NOT EXISTS conversations (\n"
                + "    id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "    corres text NOT NULL,\n"
                + "    isSender integer NOT NULL,\n"
                + "    isNew integer NOT NULL,\n"
                + "    timestamp date NOT NULL,\n"
                + "    message text NOT NULL\n"
                + ");";
		try {
			Statement stmt = this.coDB.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("DBLocale: Error createTableKnownUsers, create statement or execute");
			e.printStackTrace();
		}
		
	
	}
	
	protected void createTableAccount() {
		String sql = "CREATE TABLE IF NOT EXISTS account (\n"
                + "    username text PRIMARY KEY,\n"
                + "    password text NOT NULL,\n"
                + "    pseudo text NOT NULL,\n"
                + "    address blob NOT NULL\n"
                + ");";
		try {
			Statement stmt = this.coDB.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("DBLocale: Error createTableKnownUsers, create statement or execute");
			e.printStackTrace();
		}
	}
	
	protected Account getAccount(String username, String password) {
		String sql = "SELECT * FROM account WHERE username= ? AND password= ?";
		ResultSet rs = null;
		String un;
		String ps;
		Address temp;
		Account tempA = null;;
		try {
			PreparedStatement pstmt = this.coDB.prepareStatement(sql);
			pstmt.setString(1,username);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if (rs.next() == true) {
				 un = rs.getString("username");
				 ps = rs.getString("password");
				 temp = new Address(InetAddress.getByAddress(rs.getBytes("address")),ps,un);
				 tempA = new Account(un,ps,rs.getString("pseudo"),temp);
			}
			
		} catch (SQLException | UnknownHostException e) {
			System.out.println("DBLocal: Error getAccount creation or execute query");
			e.printStackTrace();
		}
		return tempA;
		
	}

}
