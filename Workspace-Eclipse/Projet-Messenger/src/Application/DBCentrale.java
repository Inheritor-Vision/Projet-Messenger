package Application;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import Common.Address;
import java.net.InetAddress;
import java.net.UnknownHostException;


/*ACCES VIA TERMINAL : mysql -h srv-bdens.insa-toulouse.fr -D tpservlet_13 -u tpservlet_13 -p
 * puis mdp : La1yah4k
 */


public class DBCentrale {
	private static String login = "tpservlet_13";
	private static String pswd = "La1yah4k";
	private static String URL = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/"+login;
	static protected Connection coDBc = connectionDBCentrale();
	final static protected DBLocale DBl = new DBLocale();
	private String UsernameLogged;
	public static boolean finPullDB = false;
	private static Timestamp ts = new Timestamp(0L);
	
	public DBCentrale(String _UsernameLogged) {
		this.createTableKnownUsers();
		this.createTableConversations();
		this.UsernameLogged = _UsernameLogged;
	}
	
	
	public static Connection connectionDBCentrale() {
		Connection co = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			co = DriverManager.getConnection(URL,login,pswd);
			System.out.println("DBCentrale: Database opened successfully");
		}catch(Exception e) {
			System.out.println("Erreur de connection à la DB centrale : "+e.getMessage());
			e.printStackTrace();
		}
		return co;
	}
	
	public void close() {
		try {
			DBCentrale.coDBc.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected synchronized void createTableKnownUsers() {
		String sql = "CREATE TABLE IF NOT EXISTS knownUsers (\n"
                + "    usernameLogged VARCHAR(255) NOT NULL,\n"
                + "    username VARCHAR(255) NOT NULL,\n"
                + "    pseudo VARCHAR(255) NOT NULL,\n"
                + "    address blob NOT NULL,\n"
                + "    timestamp VARCHAR(255) NOT NULL,\n"
                + "    PRIMARY KEY(usernameLogged,username)"
                + ");";
		try {
			Statement stmt = DBCentrale.coDBc.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("DBCentrale: Error createTableKnownUsers, create statement or execute");
			e.printStackTrace();
		}
		
	
	}
	
	protected synchronized void  createTableConversations() {
		String sql = "CREATE TABLE IF NOT EXISTS conversations (\n"
                + "    sender VARCHAR(255) NOT NULL,\n"
                + "    receiver VARCHAR(255) NOT NULL,\n"
                + "    timestamp VARCHAR(255) NOT NULL,\n"
                + "    message VARCHAR(255) NOT NULL,\n"
                + "    PRIMARY KEY(sender,receiver,timestamp,message)"
                + ");";
		try {
			Statement stmt = DBCentrale.coDBc.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("DBCentrale: Error createTableConversations, create statement or execute");
			e.printStackTrace();
		}
		
	
	}
	
	protected static void  createTableAccount() {
		String sql = "CREATE TABLE IF NOT EXISTS account (\n"
                + "    username VARCHAR(255) PRIMARY KEY,\n"
                + "    password VARCHAR(255) NOT NULL,\n"
                + "    pseudo VARCHAR(255) NOT NULL\n"
                + ");";
		try {
			Statement stmt = DBCentrale.coDBc.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("DBCentrale: Error createTableAccount, create statement or execute");
			e.printStackTrace();
		}
	}

	// A faire au tout debut de l'app, pour pull la table account. Appelée à l'ouverture de l'application.
	protected static void InitPullAccount(){
		createTableAccount();
		String sql = "SELECT * FROM account;";
		try{
			PreparedStatement ps = DBCentrale.coDBc.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				DBl.setAccount(new Account(rs.getString("username"), rs.getString("password"),rs.getString("pseudo"),null));
			}
			rs.close();
			ps.close();
			//DBCentrale.coDBc.close();
		}catch(SQLException e){
			System.out.println("DBCentrale: Error InitPullAccount");
			e.printStackTrace();
		}
		
		
	}

	protected  void  PullDB(){
		//récupère la infos de la db centrale et les ajoutes dans la db locale vide. Appelé apres connection de l'user dadns le constructeur.
		try{
			this.ts = new Timestamp(System.currentTimeMillis());
			//DBCentrale.coDBc = connectionDBCentrale();
			Statement stmt = DBCentrale.coDBc.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM knownUsers where usernameLogged = '" + this.UsernameLogged +"';");
			while(rs.next()){
				DBl.setKnownUser(new Address(InetAddress.getByAddress(rs.getBytes("address")), rs.getString("pseudo"), rs.getString("username")), UsernameLogged, Timestamp.valueOf(rs.getString("timestamp")));
			}
			stmt.close();
			rs.close();
			
			
			String sql = "SELECT * FROM conversations WHERE (sender = ?) OR (receiver = ?) ;";
			PreparedStatement pstmt = DBCentrale.coDBc.prepareStatement(sql);
			pstmt.setString(1, this.UsernameLogged);
			pstmt.setString(2, this.UsernameLogged);
			ResultSet rs2 = pstmt.executeQuery();
			while(rs2.next()){
				String sender = rs2.getString("sender");
				if(this.UsernameLogged.equals(sender)) {
					System.out.println(" zaeaze " + Timestamp.valueOf(rs2.getString("timestamp")));
					DBl.setMessage(new Message(true, rs2.getString("message"),  Timestamp.valueOf(rs2.getString("timestamp"))), sender, rs2.getString("receiver"));
				}else {
					System.out.println(" zaeaze " + Timestamp.valueOf(rs2.getString("timestamp")));
					DBl.setMessage(new Message(false, rs2.getString("message"), Timestamp.valueOf(rs2.getString("timestamp"))), sender, rs2.getString("receiver"));
				}
				
			}
			
			rs2.close();
			pstmt.close();
			DBCentrale.finPullDB = true;

		}catch(SQLException | UnknownHostException e){
		System.out.println("DBCentrale: Error PullDB");
		e.printStackTrace();
		}
		

	}
	protected void PushToDBC(){
		//récupère les infos de la db locale qui sont nouvelles depuis PullDBC() et les ajoutes dans la db centrale. Appelée à la fermeture de l'app.
		//DBCentrale.coDBc = connectionDBCentrale();
		
		try {
			ResultSet rs = DBl.getRSSpecificAccount(this.UsernameLogged);
			rs.next();
			String pseudo = rs.getString("pseudo");
			String sql = "INSERT INTO account (username,password,pseudo) VALUES (?,?,?) ON DUPLICATE KEY UPDATE pseudo='"+pseudo+"';";
			PreparedStatement pstmt = DBCentrale.coDBc.prepareStatement(sql);
			pstmt.setString(1, this.UsernameLogged);
			pstmt.setString(2, rs.getString("password"));
			pstmt.setString(3, pseudo);
			pstmt.executeUpdate();
			pstmt.close();
			rs.close();
			
			rs = DBl.getRSAllKnownUsersAboveTS(this.UsernameLogged, this.ts);
			while(rs.next()) {
				
				pseudo = rs.getString("pseudo");
				sql = "INSERT INTO knownUsers (username,pseudo,address,usernameLogged,timestamp) VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE pseudo='"+pseudo+"';";
				pstmt = DBCentrale.coDBc.prepareStatement(sql);
				pstmt.setString(1, rs.getString("username"));
				pstmt.setString(2, pseudo);
				pstmt.setBytes(3, rs.getBytes("address"));
				pstmt.setString(4,this.UsernameLogged);
				pstmt.setString(5, rs.getTimestamp("timestamp").toString());
				//pstmt.setTimestamp(5,rs.getTimestamp("timestamp"));
				pstmt.executeUpdate();
				pstmt.close();
			}
			rs.close();
			
			rs = DBl.getRSAllMessageAboveTS(this.ts);
			while(rs.next()) {
				sql = "INSERT INTO conversations (sender,receiver, timestamp, message) VALUES (?,?,?,?)";
				pstmt = DBCentrale.coDBc.prepareStatement(sql);
				pstmt.setString(1, rs.getString("sender"));
				pstmt.setString(2, rs.getString("receiver"));
				pstmt.setString(3, rs.getTimestamp("timestamp").toString());
				//pstmt.setTimestamp(3, rs.getTimestamp("timestamp"));
				pstmt.setString(4,rs.getString("message"));
				pstmt.executeUpdate();
				pstmt.close();
			}
			rs.close();
			
			
			
			
			
			} catch (SQLException e) {
			System.out.println("DBCentrale: Error PushToDBC");
			e.printStackTrace();
		}
		
	}
	

	
}