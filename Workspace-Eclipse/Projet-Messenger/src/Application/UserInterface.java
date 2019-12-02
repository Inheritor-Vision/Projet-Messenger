package Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.util.EventObject;

class UserInterface{
	
	connectionPage connectionpage;
	creationcomptePage creationcomptepage;
	
	public UserInterface() {
		/*SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});*/
		
		initGUI();
		setConnectionPage();
		
	}
	
	
	
	void initGUI() {
		this.connectionpage = new connectionPage();
		this.creationcomptepage = new creationcomptePage();
		
	}
	
	void setConnectionPage() {
		this.creationcomptepage.setVisible(false);
		this.connectionpage.setVisible(true);
		
	}
	
	void setCreationcomptePage() {
		this.connectionpage.setVisible(false);
		this.creationcomptepage.setVisible(true);
		
		
	}
	
	
	
/////////////////////////////////////////PAGE DE CONNEXION (JFrame)///////////////////////////////////////////	
	
	class connectionPage extends JFrame{
		private JTextField username;
		private JTextField password;
		private JButton connexion;
		private JButton creation;
		private connectionHandler coH = new connectionHandler();
		private creationcompteHandler crH = new creationcompteHandler();
		private JPanel panel ;
		
		public connectionPage() {
			super("Messenger - Page de connexion");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.panel = new JPanel(new GridLayout(0,1));
			
			this.username = new JTextField("username");
			this.password = new JTextField("password");
			this.connexion = new JButton("connexion");
			this.creation = new JButton("création de compte");
			
			this.panel.add(this.username);
			this.panel.add(this.password);
			this.panel.add(this.connexion);
			this.panel.add(this.creation);
			
			this.connexion.addActionListener(this.coH);
			this.creation.addActionListener(this.crH);
			
			this.panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			this.getContentPane().add(this.panel, BorderLayout.CENTER);
			
			this.setSize(new Dimension(500,500));
		}
		
		
		private class connectionHandler implements ActionListener {

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
	
//////////////////////////////////CREATION COMPTE (JFrame)////////////////////////////////////////////////////
	class creationcomptePage extends JFrame{
		private JTextField username;
		private JTextField password;
		private JTextField pseudo;
		private JButton creation;
		private creationHandler crH = new creationHandler();
		private JPanel panel ;
		
		public creationcomptePage() {
			super("Messenger - Page de création de compte");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.panel = new JPanel(new GridLayout(0,1));
			
			this.username = new JTextField("username");
			this.password = new JTextField("password");
			this.pseudo = new JTextField("pseudo");
			this.creation = new JButton("création du compte");
			
			this.panel.add(this.username);
			this.panel.add(this.password);
			this.panel.add(this.pseudo);
			this.panel.add(this.creation);
			
			this.creation.addActionListener(this.crH);
			
			this.panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			this.getContentPane().add(this.panel, BorderLayout.CENTER);
			
			this.setSize(new Dimension(500,500));
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
