package Application;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import Common.Address;
import Common.Tools;

public class DBLocale {
	//https://www.tutorialspoint.com/sqlite/sqlite_java.htm
	//sqlite java
	
	private static final String CHEMIN =  "Db_Locale_Files";
	final static protected Connection coDB = connectionDB("DBMessenger");
	private static int init = 0;
	public DBLocale() {
		//this.coDB = connectionDB("DBMessenger");
		this.createTableKnownUsers();
		this.createTableConversations();
		this.createTableAccount();
	}
	public  DBLocale(String nomDB) {
		//this.coDB = connectionDB(nomDB);
		this.createTableKnownUsers();
		this.createTableConversations();
		this.createTableAccount();
		//this.knownUsers = this.getknownUsers();
	}
	
	private static synchronized Connection connectionDB(String nomDB) {
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
	protected synchronized Address getSpecificKnownUser(String UsernameLogged, String userToSearch) {
		Address res = null;
		try {
			Statement stmt = coDB.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM knownUsers where usernameLogged = '" + UsernameLogged + "' AND username = '" + userToSearch + "' ;");
			if (rs.next()) {
				res =  new Address(InetAddress.getByAddress(rs.getBytes("address")), rs.getString("pseudo"), rs.getString("username"));
			}
			stmt.close();
			rs.close();
		}catch (SQLException e) {
			System.out.println("DBlocal: Error getSpecificKnownUser, SQL ERROR");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			System.out.println("DBlocal: Error getSpecificKnownUser, Unknown Host Error");
			e.printStackTrace();
		}
		return res;
	}
	protected synchronized ArrayList<Address> getknownUsers(String UsernameLogged){
		ArrayList<Address> temp = new ArrayList<Address>();
		Statement stmt;
		try {
			stmt = coDB.createStatement();
			ResultSet rs;
			if (UsernameLogged == null) {
				rs = stmt.executeQuery("SELECT * FROM knownUsers;");
			} //on récupère tout
			else{
				rs = stmt.executeQuery("SELECT * FROM knownUsers where usernameLogged = '" + UsernameLogged +"';");
			}
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
	

	private synchronized Address getUserAddress(String userLogged,String corres) throws IOException {
		Iterator<Address> iter = this.getknownUsers(userLogged).iterator();

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
			throw new IOException("DBLocal: Error getUserAddress");
		}
	}
	
	protected synchronized Conversation getConversation(String userLogged, String corresp) {
		Conversation conv = null;
		try {
			conv = new Conversation(this.getUserAddress(userLogged,corresp));
		} catch (IOException e1) {
			System.out.println("DBLocal: Error getUserAddress");
			e1.printStackTrace();
		}
		try {
			String sql = "SELECT * FROM conversations WHERE (sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?) ORDER BY timestamp ASC;";
			PreparedStatement stmt = this.coDB.prepareStatement(sql);
			stmt.setString(1, userLogged);
			stmt.setString(2, corresp);
			stmt.setString(3, corresp);
			stmt.setString(4, userLogged);
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next() == false) {
				System.out.println("DBLocal: Error getConv return empty set");
			}else {
				 do {
					java.sql.Timestamp ts = rs.getTimestamp("timestamp");
					System.out.println(ts);
					String msg = rs.getString("message");
					if(rs.getString("sender").equals(userLogged)) {
						conv.addMessage(new Message(true,msg, ts));
					}else {
						conv.addMessage(new Message(false,msg, ts));
					}
					
				}while(rs.next());
				 
			}
			rs.close();
		    stmt.close();
			
		} catch (SQLException e) {
			System.out.println("DBlocal: Error getMessage");
			e.printStackTrace();
		}
		return conv;
	}
	
	private synchronized int BoolToInt(boolean a) {
		if (a == true) {
			return 1;
		}else {
			return 0;
		}
	}
	// id corres isSender isNew ts msg 
	protected synchronized void setMessage(Message msg, String sender, String receiver) {
		try {
			String sql = "INSERT INTO conversations (sender,receiver, timestamp, message) VALUES (?,?,?,?)";
			PreparedStatement pstmt = this.coDB.prepareStatement(sql);
			pstmt.setString(1, sender);
			pstmt.setString(2, receiver);
			pstmt.setTimestamp(3, msg.getTimestamp());
			pstmt.setString(4,msg.getMsg());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("DBLocal: Err	\n" + 
					"or setMessage SQL");
			e.printStackTrace();
		}
		
	}
	
	protected synchronized void setKnownUser(Address add, String UsernameLogged) {
		String sql = "INSERT INTO knownUsers (username,pseudo,address,usernameLogged,timestamp) VALUES (?,?,?,?,?)";
		try {
			PreparedStatement pstmt = coDB.prepareStatement(sql);
			pstmt.setString(1, add.getUsername());
			pstmt.setString(2, add.getPseudo());
			pstmt.setBytes(3, add.getIP().getAddress());
			pstmt.setString(4,UsernameLogged);
			pstmt.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("DBLocale: Error setKnownUser, creation pstmt or execute");
			e.printStackTrace();
		}
	}
	
	protected synchronized void setKnownUser(Address add, String UsernameLogged, Timestamp ts) {
		String sql = "INSERT INTO knownUsers (username,pseudo,address,usernameLogged,timestamp) VALUES (?,?,?,?,?)";
		try {
			PreparedStatement pstmt = coDB.prepareStatement(sql);
			pstmt.setString(1, add.getUsername());
			pstmt.setString(2, add.getPseudo());
			pstmt.setBytes(3, add.getIP().getAddress());
			pstmt.setString(4,UsernameLogged);
			pstmt.setTimestamp(5,ts);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("DBLocale: Error setKnownUser, creation pstmt or execute");
			e.printStackTrace();
		}
	}
	
	protected synchronized void createTableKnownUsers() {
		String sql = "CREATE TABLE IF NOT EXISTS knownUsers (\n"
                + "    usernameLogged VARCHAR(255) NOT NULL,\n"
                + "    username VARCHAR(255) NOT NULL,\n"
                + "    pseudo VARCHAR(255) NOT NULL,\n"
                + "    address blob NOT NULL,\n"
                + "    timestamp TIMESTAMP NOT NULL,\n"
                + "    PRIMARY KEY(usernameLogged,username)"
                + ");";
		try {
			Statement stmt = coDB.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("DBLocale: Error createTableKnownUsers, create statement or execute");
			e.printStackTrace();
		}
		
	
	}
	
	protected synchronized void  createTableConversations() {
		String sql = "CREATE TABLE IF NOT EXISTS conversations (\n"
                + "    sender VARCHAR(255) NOT NULL,\n"
                + "    receiver VARCHAR(255) NOT NULL,\n"
                + "    timestamp TIMESTAMP NOT NULL,\n"
                + "    message VARCHAR(255) NOT NULL\n,"
                + "    PRIMARY KEY(sender,receiver,timestamp,message)"
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
	
	protected synchronized void  createTableAccount() {
		String sql = "CREATE TABLE IF NOT EXISTS account (\n"
                + "    username VARCHAR(255) PRIMARY KEY,\n"
                + "    password VARCHAR(255) NOT NULL,\n"
                + "    pseudo VARCHAR(255) NOT NULL\n"
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
	
	protected synchronized void updatePseudoAccount(String username, String new_pseudo) {
		String sql = "UPDATE account SET pseudo = '" + new_pseudo + "' where username='" + username + "';";
		try {
			Statement stmt = this.coDB.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("DBLocale: Error createTableKnownUsers, create statement or execute");
			e.printStackTrace();
		}
	}
	
	protected synchronized Account getAccount(String username, String password) {
		String sql = "SELECT * FROM account WHERE (username = ?) AND (password = ?);"; //WHERE (username = ?) AND (password = ?) 
		ResultSet rs = null;
		String un;
		String ps;
		String pw;
		Address temp;
		Account tempA = null;
		try {
			PreparedStatement pstmt = this.coDB.prepareStatement(sql);
			pstmt.setString(1,username);
			pstmt.setString(2,password);
			rs = pstmt.executeQuery();
			rs.next();
			if ( true) {
				System.out.println("YA");
				 un = rs.getString("username");
				 ps = rs.getString("pseudo");
				 pw = rs.getString("password");
				 temp = new Address(InetAddress.getByAddress(Tools.getPcIP()),ps,un);
				 tempA = new Account(un,pw,ps,temp);	 
			}
			pstmt.close();
			rs.close();
		} catch (SQLException | UnknownHostException e) {
			System.out.println("DBLocal: Error getAccount creation or execute query");
			e.printStackTrace();
		}
		return tempA;
		
	}
	protected synchronized ArrayList<Address> getAllAccount(){
		ArrayList<Address> temp = new ArrayList<Address>();
		Statement stmt;
		try {
			stmt = coDB.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("SELECT * FROM account;");
			while(rs.next()) {
				String username = rs.getString("username");
				String pseudo = rs.getString("pseudo");
				temp.add(new Address(InetAddress.getByAddress(Tools.getPcIP()), pseudo, username));
			}
			rs.close();
		    stmt.close();
		} catch (SQLException e) {
			System.out.println("DBlocal: Error getAllAccount, SQL ERROR");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			System.out.println("DBlocal: Error getAllAccount, Unknown Host Error");
			e.printStackTrace();
		}
		return temp;
		
	}
	

	//pour éviter l'erreur UnknownHostException (pour l'instant)//
	/*protected Account getAccount2(String username, String password) {
		String sql = "SELECT * FROM account WHERE (username = ?) AND (password = ?);"; //WHERE (username = ?) AND (password = ?) 
		ResultSet rs = null;
		String un;
		String ps;
		String pw;
		Address temp;
		Account tempA = null;
		try {
			PreparedStatement pstmt = this.coDB.prepareStatement(sql);
			pstmt.setString(1,username);
			pstmt.setString(2,password);
			rs = pstmt.executeQuery();
			rs.next();
			if ( true) {
				System.out.println("YA");
				 un = rs.getString("username");
				 ps = rs.getString("pseudo");
				 pw = rs.getString("password");
				 //temp = new Address(InetAddress.getByAddress(this.getPcIP()),ps,un);
				 //tempA = new Account(un,pw,ps,temp);
				 tempA = new Account(un,pw,ps,null);
			}
			
		} catch (SQLException | UnknownHostException e) {
			System.out.println("DBLocal: Error getAccount creation or execute query");
			e.printStackTrace();
		}
		return tempA;
		
	}*/
	/////
	

	protected synchronized void setAccount(Account acc){

		String sql = "INSERT INTO account (username,password,pseudo) VALUES (?,?,?)";
		try {
			PreparedStatement pstmt = coDB.prepareStatement(sql);
			pstmt.setString(1, acc.getUsername());
			pstmt.setString(2, acc.getPassword());
			pstmt.setString(3, acc.getPseudo());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("DBLocal: Error setAccount");
			e.printStackTrace();
		}
		
	}
	
	protected synchronized void updatePseudo(String new_Pseudo, String old_Pseudo, String username, String LoggedUsername) {
		String sql = "UPDATE knownUsers SET pseudo = '" + new_Pseudo + "' where usernameLogged='" + LoggedUsername + "' AND pseudo='" + old_Pseudo + "' AND username='" + username + "';";
		try {
			Statement stmt = coDB.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("DBLocale: Error createTableKnownUsers, create statement or execute");
			e.printStackTrace();
		}
	
	}
	
	
	protected ResultSet getRSAllMessageAboveTS(Timestamp ts) {
		ResultSet rs = null;
		String sql = "SELECT * FROM conversations WHERE timestamp > ? ;";
		try {
			PreparedStatement pstmt = coDB.prepareStatement(sql);
			pstmt.setTimestamp(1, ts);
			rs = pstmt.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("DBLocale: Error getRSAllMessageAboveTS, create statement or execute");
			e.printStackTrace();
		}
		return rs;
	}
	
	protected ResultSet getRSAllKnownUsersAboveTS(String Userlogged, Timestamp ts) {
		ResultSet rs = null;
		String sql = "SELECT * FROM knownUsers WHERE usernameLogged = ? AND timestamp > ?;";
		try {
			PreparedStatement pstmt = coDB.prepareStatement(sql);
			pstmt.setString(1, Userlogged);
			pstmt.setTimestamp(2, ts);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("DBLocale: Error getRSAllKnownUsersAboveTS, create statement or execute");
			e.printStackTrace();
		}
		return rs;
	}
	
	protected ResultSet getRSSpecificAccount(String Userlogged) {
		ResultSet rs = null;
		String sql = "SELECT * FROM account WHERE username = ?;";
		try {
			PreparedStatement pstmt = coDB.prepareStatement(sql);
			pstmt.setString(1, Userlogged);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("DBLocale: Error getRSSpecificAccount, create statement or execute");
			e.printStackTrace();
		}
		return rs;
	}
	
	protected void printAllTable() {
		String sql;
		try {
			sql = "SELECT * FROM account;";
			Statement stmt = coDB.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next() == false) {
				System.out.println("\nDBLocal: account is EMPTY");
			}else {
				System.out.println("\nDBLocal:Table account:\n");
				System.out.println("--------------------------------");
				System.out.println("| username | pseudo | password |");
				System.out.println("--------------------------------\n");
				do {

					System.out.println("|" + rs.getString("username") + " | " + rs.getString("pseudo") + " | " + rs.getString("password") + "|");
				}while(rs.next());
				 
			}
			rs.close();
		    stmt.close();
		    
		    sql = "SELECT * FROM conversations";
		    stmt = coDB.createStatement();
		    rs = stmt.executeQuery(sql);
		    if (rs.next() == false) {
				System.out.println("\nDBLocal: conversations is EMPTY");
			}else {
				System.out.println("\nDBLocal:Table conversations:\n");
				System.out.println("-------------------------------------------");
				System.out.println("| sender | receiver | timestamp | message |");
				System.out.println("-------------------------------------------\n");
				do {
					System.out.println("|" + rs.getString("sender") + " | " + rs.getString("receiver") + " | "  + rs.getTimestamp("timestamp") + " | " + rs.getString("message") + " |");
				}while(rs.next());
				 
			}
			rs.close();
		    stmt.close();
		    
		    sql = "SELECT * FROM knownUsers";
		    stmt = coDB.createStatement();
		    rs = stmt.executeQuery(sql);
		    if (rs.next() == false) {
				System.out.println("\nDBLocal: knownUsers is EMPTY");
			}else {
				System.out.println("\nDBLocal:Table knownUsers:\n");
				System.out.println("------------------------------------------------------------");
				System.out.println("| usernameLogged | username | pseudo | address | timestamp |");
				System.out.println("------------------------------------------------------------\n");
				do {
					System.out.println("|" + rs.getString("usernameLogged") + " | " + rs.getString("username") + " | " + rs.getString("pseudo") + " | " + rs.getBytes("address")[0] + "." + rs.getBytes("address")[1] + "." + rs.getBytes("address")[2] + "." + rs.getBytes("address")[3] + " | " + rs.getTimestamp("timestamp") + " |");
				}while(rs.next());
				 
			}
			rs.close();
		    stmt.close();
		    
			
		}catch (SQLException e) {
			System.out.println("DBLocal: Error getAccount creation or execute query");
			e.printStackTrace();
		}
		
	}

}
