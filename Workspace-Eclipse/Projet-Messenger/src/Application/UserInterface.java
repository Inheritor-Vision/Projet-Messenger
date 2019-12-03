package Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.util.EventObject;

class UserInterface extends JFrame{
	
	Controller co;
	boolean connecte = false;
	connexionPage connexionpage;
	creationcomptePage creationcomptepage;
	utilisateursconnectesPage utilisateursconnectespage;
	MenuBar menubar;
	
	public UserInterface() {
		/*super("Messenger");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});*/
		
		super("Messenger");
		initGUI();
		
	}
	
	
	
	void initGUI() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(500,500));
		this.connexionpage = new connexionPage();
		this.creationcomptepage = new creationcomptePage();
		this.utilisateursconnectespage = new utilisateursconnectesPage();
		this.menubar = new MenuBar();
		this.getContentPane().add(this.menubar, BorderLayout.PAGE_START);
		setConnexionPage();
		
		
	}

/////////méthodes de changement de page//////
	void setConnexionPage() {
		this.setVisible(false);
		this.getContentPane().add(this.connexionpage, BorderLayout.CENTER);
		this.getContentPane().remove(this.creationcomptepage);
		this.getContentPane().remove(this.utilisateursconnectespage);
		this.setVisible(true);	
	}
	
	void setCreationcomptePage() {
		this.setVisible(false);
		this.getContentPane().add(this.creationcomptepage, BorderLayout.CENTER);
		this.getContentPane().remove(this.connexionpage);
		this.getContentPane().remove(this.utilisateursconnectespage);
		this.setVisible(true);	
	}
	
	void setUtilisateursconnectesPage_same_frame() {
		this.setVisible(false);
		this.getContentPane().add(this.utilisateursconnectespage, BorderLayout.CENTER);
		this.getContentPane().remove(this.connexionpage);
		this.getContentPane().remove(this.creationcomptepage);
		this.setVisible(true);
	}
////////////////////////////////////////////	
	
	
/////////////////////////////////////////PAGE DE CONNEXION (JPanel)///////////////////////////////////////////	
	
	class connexionPage extends JPanel{
		private JTextField username;
		private JTextField password;
		private JButton connexion;
		private JButton creation;
		private connexionHandler coH = new connexionHandler();
		private pagecreationcompteHandler crH = new pagecreationcompteHandler();
		private JLabel erreur;
		
		public connexionPage() {
			super(new GridLayout(0,1));
			
			this.username = new JTextField("username");
			this.password = new JTextField("password");
			this.connexion = new JButton("connexion");
			this.creation = new JButton("création de compte");
			this.erreur = new JLabel("Entrez username/password");
			
			this.add(this.erreur);
			this.add(this.username);
			this.add(this.password);
			this.add(this.connexion);
			this.add(this.creation);
			
			this.connexion.addActionListener(this.coH);
			this.creation.addActionListener(this.crH);
			
			this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
		}
		
		
	}
////////////////////////////////////////////////////////////////////////////////////////
	
//////////////////////////////////CREATION COMPTE (JPanel)////////////////////////////////////////////////////
	class creationcomptePage extends JPanel{
		public JTextField username;
		private JTextField password;
		private JTextField pseudo;
		private JButton creation;
		private creationcompteHandler crH = new creationcompteHandler();
		
		public creationcomptePage() {
			super(new GridLayout(0,1));
			
			this.username = new JTextField("username");
			this.password = new JTextField("password");
			this.pseudo = new JTextField("pseudo");
			this.creation = new JButton("création du compte");
			
			this.add(this.username);
			this.add(this.password);
			this.add(this.pseudo);
			this.add(this.creation);
			
			this.creation.addActionListener(this.crH);
			
			this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
		}
		
		
		
	}
///////////////////////////////////////////////////////////////////////////////////////////////////
	
/////////////////////////////////////////PAGE UTILISATEURS CONNECTES (JPanel)///////////////////////////////////////////	
	
class utilisateursconnectesPage extends JPanel{
	private JButton[] utilisateurs;
	private afficherconversationHandler acH = new afficherconversationHandler();

	
	public utilisateursconnectesPage() {
		super(new GridLayout(0,1));
		
		this.utilisateurs = new JButton[100];
		for (int i=0;i<100;i++) {
			this.utilisateurs[i]= new JButton(Integer.toString(i));
			this.add(this.utilisateurs[i]);
			this.utilisateurs[i].addActionListener(this.acH);
		}
		
		
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	
	}
	
	/*String[] pseudo_uc() {
		String[] uc;
		
		
		return uc;
	}*/

}
////////////////////////////////////////////////////////////////////////////////////////
	
////////////////////////////////MENU BAR (JMenuBar)/////////////////////////////////////////////////////////////////
	class MenuBar extends JMenuBar{
		
		private JMenu msysteme;
		private JMenu mconversation;
		private JMenuItem fermerapp;
		private JMenuItem deconnexion;
		private JMenuItem changercompte;
		private JMenuItem changerpseudo;
		private JMenuItem creercompte;
		private JMenuItem changerconversation;
		private JMenuItem userco;
		
		
		public MenuBar() {
			super();
			
			this.msysteme = new JMenu("Système");
			this.mconversation = new JMenu("Conversations");
			this.fermerapp = new JMenuItem("fermer l'application");
			this.deconnexion = new JMenuItem("se déconnecter");
			this.changercompte = new JMenuItem("changer de compte");
			this.changerpseudo = new JMenuItem("modisfier le pseudo");
			this.creercompte = new JMenuItem("créer un compte");
			this.changerconversation = new JMenuItem("changer de conversation");
			this.userco = new JMenuItem("utilisateurs connectés");
			
			this.add(this.msysteme);
			this.add(this.mconversation);
			
			this.msysteme.add(this.deconnexion);
			this.deconnexion.addActionListener(new deconnexionHandler());
			this.msysteme.add(this.changercompte);
			this.changercompte.addActionListener(new deconnexionHandler());
			this.msysteme.add(this.changerpseudo);
			this.changerpseudo.addActionListener(new changerpseudoHandler());
			this.msysteme.add(this.creercompte);
			this.creercompte.addActionListener(new pagecreationcompteHandler());
			this.msysteme.add(this.fermerapp);
			this.fermerapp.addActionListener(new fermerappHandler());
			
			this.mconversation.add(this.changerconversation);
			this.changerconversation.addActionListener(new changerconversationHandler());
			this.mconversation.add(this.userco);
			this.userco.addActionListener(new utilisateursconnectesHandler());
			
		}
		
		
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////
	
///////////////////////////////LES ACTIONS LISTENER///////////////////////////////////////////////////////
	private class pagecreationcompteHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setCreationcomptePage();
			
		}
		
		
	}
	
	private class creationcompteHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class connexionHandler implements ActionListener {
		

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String username_ = connexionpage.username.getText();
			String password_ = connexionpage.password.getText();
			
			if ( username_.equals("admin") && password_.equals("admin")) { //par exemple pour l'instant
				//(...)//
				connexionpage.erreur.setText("Entrez username/password");
				setUtilisateursconnectesPage_same_frame();
			}
			else {
				connexionpage.erreur.setText("erreur de connexion");
			}
			
		}
		
		
	}
	
	private class deconnexionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			//(...)//
			
			connexionpage.erreur.setText("Entrez username/password");
			setConnexionPage();
		}
		
		
	}
	
	private class changerpseudoHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	private class fermerappHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			//(...) deconnexion//
			
			System.exit(0);
		}
		
		
	}
	
	private class changerconversationHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	private class utilisateursconnectesHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	private class afficherconversationHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
