package Application;

import java.sql.DriverManager;
import java.sql.Connection;

/*ACCES VIA TERMINAL : mysql -h srv-bdens.insa-toulouse.fr -D tp_servlet13 -u tp_servlet13 -p
 * puis mdp : La1yah4k
 */


public class DBCentrale {
	private static String login = "tpservlet_13";
	private static String pswd = "La1yah4k";
	private static String URL = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/"+login;
	final static protected Connection coDBc = connectionDBCentrale();
	
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
	
	void PullDBC(){
		//récupère la infos de la db centrale et les ajoutes dans la db locale vide. Appelée à l'ouverture de l'application.
	}
	void PushToDBC(){
		//récupère les infos de la db locale qui sont nouvelles depuis PullDBC() et les ajoutes dans la db centrale. Appelée à la fermeture de l'app.
	}
	

	
}