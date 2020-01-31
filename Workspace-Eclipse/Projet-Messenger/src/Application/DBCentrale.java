package Application;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import Common.Address;
import java.net.InetAddress;


/*ACCES VIA TERMINAL : mysql -h srv-bdens.insa-toulouse.fr -D tp_servlet13 -u tp_servlet13 -p
 * puis mdp : La1yah4k
 */


public class DBCentrale {
	private static String login = "tpservlet_13";
	private static String pswd = "La1yah4k";
	private static String URL = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/"+login;
	final static protected Connection coDBc = connectionDBCentrale();
	final static protected DBLocale DBl = new DBLocale();
	
	public DBCentrale() {
		
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
	protected synchronized void createTableKnownUsers() {
		String sql = "CREATE TABLE IF NOT EXISTS knownUsers (\n"
                + "    id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "    usernameLogged text NOT NULL,\n"
                + "    username text NOT NULL,\n"
                + "    pseudo text NOT NULL,\n"
                + "    address blob NOT NULL\n"
                + ");";
		try {
			Statement stmt = coDBc.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("DBCentrale: Error createTableKnownUsers, create statement or execute");
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
			Statement stmt = coDBc.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("DBCentrale: Error createTableKnownUsers, create statement or execute");
			e.printStackTrace();
		}
		
	
	}
	
	protected static void  createTableAccount() {
		String sql = "CREATE TABLE IF NOT EXISTS account (\n"
                + "    username text PRIMARY KEY,\n"
                + "    password text NOT NULL,\n"
                + "    pseudo text NOT NULL\n"
                + ");";
		try {
			Statement stmt = coDBc.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("DBCentrale: Error createTableKnownUsers, create statement or execute");
			e.printStackTrace();
		}
	}

	// A faire au tout debut de l'app, pour pull la table account. Appelée à l'ouverture de l'application.
	protected static void InitPullAccount(){
		createTableAccount();
		String sql = "SELECT * FROM account;";
		try{
			PreparedStatement ps = coDBc.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				DBl.setAccount(new Account(rs.getString("username"), rs.getString("password"),rs.getString("pseudo"),null));
			}
			rs.close();
			ps.close();
		}catch(SQLException e){
			System.out.println("DBCentrale: Error InitPullAccount");
			e.printStackTrace();
		}
		
	}

	protected  void  PullDB(String UsernameLogged){
		//récupère la infos de la db centrale et les ajoutes dans la db locale vide. Appelé apres connection de l'user dadns le constructeur.
		try{
			Statement stmt = coDBc.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM knownUsers where usernameLogged = '" + UsernameLogged +"';");
			while(rs.next()){
				DBl.setKnownUser(new Address(InetAddress.getByAddress(rs.getBytes("address")), rs.getString("pseudo"), rs.getString("username")), UsernameLogged);
			}
			stmt.close();
			rs.close();
			String sql = "SELECT * FROM conversations WHERE sender = ? ;";
			PreparedStatement pstmt = coDBc.prepareStatement(sql);
			pstmt.setString(1, userLogged);

			/// ETC
			pstmt.close();

		}catch(SQLException e){
		System.out.println("DBCentrale: Error PullDB");
		e.printStackTrace();
		}
		

	}
	protected void PushToDBC(){
		//récupère les infos de la db locale qui sont nouvelles depuis PullDBC() et les ajoutes dans la db centrale. Appelée à la fermeture de l'app.
	}
	

	
}