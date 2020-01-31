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
			String sql = "SELECT * FROM conversations WHERE (sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?);";
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
					Boolean isSender = rs.getBoolean("isSender");
					Boolean isNew = rs.getBoolean("isNew");
					java.sql.Timestamp ts = rs.getTimestamp("timestamp");
					String msg = rs.getString("message");
					conv.addMessage(new Message(isSender,msg,isNew, ts));
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
			String sql = "INSERT INTO conversations (sender,receiver, isSender, isNew, timestamp, message) VALUES (?,?,?,?,?,?)";
			PreparedStatement pstmt = this.coDB.prepareStatement(sql);
			pstmt.setString(1, sender);
			pstmt.setString(2, receiver);
			pstmt.setInt(3,BoolToInt(msg.getIsEnvoyeur()));
			pstmt.setInt(4,BoolToInt(msg.getIsNew()));
			pstmt.setTimestamp(5, msg.getTimestamp());
			pstmt.setString(6,msg.getMsg());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("DBLocal: Err	\n" + 
					"or setMessage SQL");
			e.printStackTrace();
		}
		
	}
	
	protected synchronized void setKnownUser(Address add, String UsernameLogged) {
		String sql = "INSERT INTO knownUsers (username,pseudo,address,usernameLogged) VALUES (?,?,?,?)";
		try {
			PreparedStatement pstmt = this.coDB.prepareStatement(sql);
			pstmt.setString(1, add.getUsername());
			pstmt.setString(2, add.getPseudo());
			pstmt.setBytes(3, add.getIP().getAddress());
			pstmt.setString(4,UsernameLogged);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("DBLocale: Error setKnownUser, creation pstmt or execute");
			e.printStackTrace();
		}
	}
	
	protected synchronized void createTableKnownUsers() {
		String sql = "CREATE TABLE IF NOT EXISTS knownUsers (\n"
                + "    id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "    usernameLogged text NOT NULL,\n"
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
	
	protected synchronized void  createTableConversations() {
		String sql = "CREATE TABLE IF NOT EXISTS conversations (\n"
                + "    id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "    sender text NOT NULL,\n"
                + "    receiver text NOT NULL,\n"
                + "    isSender integer NOT	\n" + 
                " NULL,\n"
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
	
	protected synchronized void  createTableAccount() {
		String sql = "CREATE TABLE IF NOT EXISTS account (\n"
                + "    username text PRIMARY KEY,\n"
                + "    password text NOT NULL,\n"
                + "    pseudo text NOT NULL\n"
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
			PreparedStatement pstmt = this.coDB.prepareStatement(sql);
			pstmt.setString(1, acc.getUsername());
			pstmt.setString(2, acc.getPassword());
			pstmt.setString(3, acc.getPseudo());
			System.out.println("Debug: " + pstmt.executeUpdate());
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("DBLocal: Error setAccount");
			e.printStackTrace();
		}
		
	}
	
	protected synchronized void updatePseudo(String new_Pseudo, String old_Pseudo, String username, String LoggedUsername) {
		String sql = "UPDATE knownUsers SET pseudo = '" + new_Pseudo + "' where usernameLogged='" + LoggedUsername + "' AND pseudo='" + old_Pseudo + "' AND username='" + username + "';";
		try {
			Statement stmt = this.coDB.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("DBLocale: Error createTableKnownUsers, create statement or execute");
			e.printStackTrace();
		}
	
	}
	
	
	protected void TEMP() {
		String sql = "SELECT MAX(timestamp) FROM conversations";
		try {
			Statement stmt = DBLocale.coDB.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println(rs.next());
			System.out.println(rs.getTimestamp(1));
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("DBLocale: Error ,dsfdsftement or execute");
			e.printStackTrace();
		}
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
					String tmp1;
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
				System.out.println("--------------------------------------------------------------");
				System.out.println("| sender | receiver | isSender | isNew | timestamp | message |");
				System.out.println("--------------------------------------------------------------\n");
				do {
					System.out.println("|" + rs.getString("sender") + " | " + rs.getString("receiver") + " | " + rs.getBoolean("isSender") + " | " + rs.getBoolean("isNew")+ " | " + rs.getTimestamp("timestamp") + " | " + rs.getString("message") + " |");
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
				System.out.println("------------------------------------------------");
				System.out.println("| usernameLogged | username | pseudo | address |");
				System.out.println("------------------------------------------------\n");
				do {
					System.out.println("|" + rs.getString("usernameLogged") + " | " + rs.getString("username") + " | " + rs.getString("pseudo") + " | " + rs.getBytes("address")[0] + "." + rs.getBytes("address")[1] + "." + rs.getBytes("address")[2] + "." + rs.getBytes("address")[3] + " |");
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
