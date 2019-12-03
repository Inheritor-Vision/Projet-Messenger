package Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.util.EventObject;

class UserInterface extends JFrame{
	
	connexionPage connexionpage;
	creationcomptePage creationcomptepage;
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
		setConnexionPage();
		
		
	}
	
	void setConnexionPage() {
		this.setVisible(false);
		this.getContentPane().add(this.connexionpage, BorderLayout.CENTER);
		this.getContentPane().remove(this.creationcomptepage);
		this.setVisible(true);
		
	}
	
	void setCreationcomptePage() {
		this.setVisible(false);
		this.getContentPane().add(this.creationcomptepage, BorderLayout.CENTER);
		this.getContentPane().remove(this.connexionpage);
		this.setVisible(true);
		
		
	}
	
	
	
/////////////////////////////////////////PAGE DE CONNEXION (JPanel)///////////////////////////////////////////	
	
	class connexionPage extends JPanel{
		private JTextField username;
		private JTextField password;
		private JButton connexion;
		private JButton creation;
		private connexionHandler coH = new connexionHandler();
		private creationcompteHandler crH = new creationcompteHandler();
		private JPanel panel ;
		
		public connexionPage() {
			super(new GridLayout(0,1));
			
			this.username = new JTextField("username");
			this.password = new JTextField("password");
			this.connexion = new JButton("connexion");
			this.creation = new JButton("création de compte");
			
			this.add(this.username);
			this.add(this.password);
			this.add(this.connexion);
			this.add(this.creation);
			
			this.connexion.addActionListener(this.coH);
			this.creation.addActionListener(this.crH);
			
			this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
	

		}
		
		
		private class connexionHandler implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
		}
		
		private class creationcompteHandler implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				setCreationcomptePage();
				
			}
			
			
		}
	}
////////////////////////////////////////////////////////////////////////////////////////
	
//////////////////////////////////CREATION COMPTE (JPanel)////////////////////////////////////////////////////
	class creationcomptePage extends JPanel{
		private JTextField username;
		private JTextField password;
		private JTextField pseudo;
		private JButton creation;
		private creationHandler crH = new creationHandler();
		private JPanel panel ;
		
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
		
		
		private class creationHandler implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
		}
	}
///////////////////////////////////////////////////////////////////////////////////////////////////
	
////////////////////////////////MENU BAR (JMenuBar)/////////////////////////////////////////////////////////////////
	class MenuBar extends JMenuBar{
		
		//private JMenuBar menubar;
		private JMenu msysteme;
		private JMenu mconversation;
		private JMenuItem fermerapp;
		private JMenuItem deconnexion;
		private JMenuItem changercompte;
		private JMenuItem changerpseudo;
		private JMenuItem changerconversation;
		
		
		public MenuBar() {
			super();
			
			//this.menubar = new JMenuBar();
			this.msysteme = new JMenu("Système");
			this.mconversation = new JMenu("Conversations");
			this.fermerapp = new JMenuItem("fermer l'application");
			this.deconnexion = new JMenuItem("se déconnecter");
			this.changercompte = new JMenuItem("changer de compte");
			this.changerpseudo = new JMenuItem("modisfier le pseudo");
			this.changerconversation = new JMenuItem("changer de conversation");
			
		}
		
		
		
	}
}
