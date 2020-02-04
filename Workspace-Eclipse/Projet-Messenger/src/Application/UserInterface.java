package Application;

import javax.swing.*;

import Common.Address;

import java.lang.String;
import java.awt.*;
import java.awt.event.*;
//import java.util.EventObject;
import java.util.ArrayList;
import java.util.Map;
import java.sql.Timestamp;

@SuppressWarnings("serial")
class UserInterface extends JFrame{
	//test sans socket
	ArrayList<Address> connectedUserList = new ArrayList<Address>();
	//
	ArrayList<Address> conversation_nc = new ArrayList<Address>();
	ArrayList<Address> UserMsgNonLu = new ArrayList<Address>();
	
	Controller co;
	DBLocale db;
	String corresp = null;
	int nb_uc=0;
	int nb_nc=0;
	boolean connecte = false;
	connexionPage connexionpage;
	creationcomptePage creationcomptepage;
	utilisateursconnectesPage utilisateursconnectespage;
	JScrollPane scrollbar_uc;
	conversationPage conversationpage;
	JScrollPane scrollbar_conv;
	msgPage msgpage;
	changerpseudoPage changerpseudopage;
	MenuBar menubar;
	
	
	public UserInterface(/*ArrayList<Address> connectedUserList/*pour test*//*Controller cont, DBLocale dbl*/) {
		/*super("Messenger");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});*/
		
		super("Messenger");
		//this.connectedUserList = connectedUserList; //pour test
		/*this.db=dbl;
		this.co=cont;*/
		initGUI();
		
	}
	
	
	
	void initGUI() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new FermetureApp());
		this.setSize(new Dimension(500,500));
		this.connexionpage = new connexionPage();
		this.creationcomptepage = new creationcomptePage();
		this.utilisateursconnectespage = new utilisateursconnectesPage();
		this.scrollbar_uc = new JScrollPane(this.utilisateursconnectespage);
		this.scrollbar_uc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		//this.scrollbar_uc.setBounds(50, 30, 300, 50);
		this.conversationpage = new conversationPage();
		this.scrollbar_conv = new JScrollPane(this.conversationpage);
		this.scrollbar_conv.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.scrollbar_conv.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//this.scrollbar_conv.setBounds(50, 30, 300, 50);
		this.msgpage = new msgPage();
		this.changerpseudopage = new changerpseudoPage();
		this.menubar = new MenuBar();
		this.getContentPane().add(this.menubar, BorderLayout.PAGE_START);
		setConnexionPage();
		
		
	}

/////////méthodes de changement de page//////
	void setConnexionPage() {
		this.setVisible(false);
		this.getContentPane().add(this.connexionpage, BorderLayout.CENTER);
		this.getContentPane().remove(this.changerpseudopage);
		this.getContentPane().remove(this.creationcomptepage);
		this.getContentPane().remove(this.scrollbar_uc);
		this.getContentPane().remove(this.scrollbar_conv);
		this.getContentPane().remove(this.msgpage);
		this.setVisible(true);	
	}
	
	void setCreationcomptePage() {
		this.setVisible(false);
		this.getContentPane().add(this.creationcomptepage, BorderLayout.CENTER);
		this.getContentPane().remove(this.changerpseudopage);
		this.getContentPane().remove(this.connexionpage);
		this.getContentPane().remove(this.scrollbar_uc);
		this.getContentPane().remove(this.scrollbar_conv);
		this.getContentPane().remove(this.msgpage);
		this.setVisible(true);	
	}
	
	void setChangerpseudoPage() {
		this.setVisible(false);
		this.getContentPane().add(this.changerpseudopage, BorderLayout.CENTER);
		this.getContentPane().remove(this.connexionpage);
		this.getContentPane().remove(this.creationcomptepage);
		this.getContentPane().remove(this.scrollbar_uc);
		this.getContentPane().remove(this.scrollbar_conv);
		this.getContentPane().remove(this.msgpage);
		this.setVisible(true);	
	}
	
	void setUtilisateursconnectesPage_same_frame() {
		if(co.getLoggedAccount() != null) {
			this.setVisible(false);
			//on recrée la page pour màj
			this.getContentPane().remove(this.scrollbar_uc);
			this.utilisateursconnectespage = new utilisateursconnectesPage();
			this.scrollbar_uc = new JScrollPane(this.utilisateursconnectespage);
			this.scrollbar_uc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			//
			this.getContentPane().add(this.scrollbar_uc, BorderLayout.CENTER);
			this.getContentPane().remove(this.connexionpage);
			this.getContentPane().remove(this.changerpseudopage);
			this.getContentPane().remove(this.creationcomptepage);
			this.getContentPane().remove(this.scrollbar_conv);
			this.getContentPane().remove(this.msgpage);
			this.setVisible(true);
		}
	}
	
	void setConversationPage() {
		this.setVisible(false);		
		//on recrée la page pour màj
		this.getContentPane().remove(this.scrollbar_conv);
		this.getContentPane().remove(this.msgpage);
		this.conversationpage = new conversationPage();
		this.msgpage = new msgPage();
		this.scrollbar_conv = new JScrollPane(this.conversationpage);
		this.scrollbar_conv.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.scrollbar_conv.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//
		this.getContentPane().add(this.scrollbar_conv, BorderLayout.CENTER);
		this.getContentPane().add(this.msgpage, BorderLayout.SOUTH);
		this.getContentPane().remove(this.connexionpage);
		this.getContentPane().remove(this.changerpseudopage);
		this.getContentPane().remove(this.creationcomptepage);
		this.getContentPane().remove(this.scrollbar_uc);
		this.setVisible(true);
	}
	
	void setConversationPage_nc() {
		this.setVisible(false);		
		//on recrée la page pour màj
		this.getContentPane().remove(this.scrollbar_conv);
		this.getContentPane().remove(this.msgpage);
		this.conversationpage = new conversationPage();
		this.scrollbar_conv = new JScrollPane(this.conversationpage);
		this.scrollbar_conv.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.scrollbar_conv.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//
		this.getContentPane().add(this.scrollbar_conv, BorderLayout.CENTER);
		this.getContentPane().remove(this.connexionpage);
		this.getContentPane().remove(this.changerpseudopage);
		this.getContentPane().remove(this.creationcomptepage);
		this.getContentPane().remove(this.scrollbar_uc);
		this.setVisible(true);
	}
	
	void setConversationPage_recmsg() {
		this.setVisible(false);		
		//on recrée la page pour màj
		this.getContentPane().remove(this.scrollbar_conv);
		this.getContentPane().remove(this.msgpage);
		this.conversationpage = new conversationPage();
		this.scrollbar_conv = new JScrollPane(this.conversationpage);
		this.scrollbar_conv.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.scrollbar_conv.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//
		this.getContentPane().add(this.scrollbar_conv, BorderLayout.CENTER);
		this.getContentPane().add(this.msgpage, BorderLayout.SOUTH);
		this.getContentPane().remove(this.connexionpage);
		this.getContentPane().remove(this.changerpseudopage);
		this.getContentPane().remove(this.creationcomptepage);
		this.getContentPane().remove(this.scrollbar_uc);
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
			
			this.username.addActionListener(this.coH); //press enter
			this.password.addActionListener(this.coH); //press enter
			this.connexion.addActionListener(this.coH);
			this.creation.addActionListener(this.crH);
			
			this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
		}
		
		
	}
////////////////////////////////////////////////////////////////////////////////////////
	
//////////////////////////////////PAGE DE CREATION COMPTE (JPanel)////////////////////////////////////////////////////

	class creationcomptePage extends JPanel{
		private JLabel entete;
		private JTextField username;
		private JTextField password;
		private JTextField pseudo;
		private JButton creation;
		private creationcompteHandler crH = new creationcompteHandler();
		
		public creationcomptePage() {
			super(new GridLayout(0,1));
			
			this.entete = new JLabel("créer un compte");
			this.username = new JTextField("username");
			this.password = new JTextField("password");
			this.pseudo = new JTextField("pseudo");
			this.creation = new JButton("création du compte");
			
			this.add(this.entete);
			this.add(this.username);
			this.add(this.password);
			this.add(this.pseudo);
			this.add(this.creation);
			
			this.username.addActionListener(this.crH); //press enter
			this.password.addActionListener(this.crH); //press enter
			this.pseudo.addActionListener(this.crH); //press enter
			this.creation.addActionListener(this.crH);
			
			this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
		}
		
		
		
	}
///////////////////////////////////////////////////////////////////////////////////////////////////
	
//////////////////////////////////PAGE DE CHANGEMENT DE PSEUDO(JPanel)////////////////////////////////////////////////////

	class changerpseudoPage extends JPanel{
		private JLabel erreur_cp;
		private JTextField pseudo;
		private JButton creation;
		private changerpseudoHandler cpH = new changerpseudoHandler();
		
		public changerpseudoPage() {
			super(new GridLayout(0,1));
			
			this.erreur_cp = new JLabel("entrez un nouveau pseudo");
			this.pseudo = new JTextField("pseudo");
			this.creation = new JButton("changer");
			
			this.add(this.erreur_cp);
			this.add(this.pseudo);
			this.add(this.creation);
			
			this.pseudo.addActionListener(this.cpH); //press enter
			this.creation.addActionListener(this.cpH);
			
			this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		}
	
	
	
	}
///////////////////////////////////////////////////////////////////////////////////////////////////
	
/////////////////////////////////////////PAGE UTILISATEURS CONNECTES (JPanel)///////////////////////////////////////////	

	class utilisateursconnectesPage extends JPanel{
		private JButton[] utilisateurs;
		private JButton[] utilisateursnc;
		private JLabel erreuruc;
		private JLabel erreurnc;
		private afficherconversationHandler acH = new afficherconversationHandler();
		ArrayList<Address> cnc = new ArrayList<Address>();
	
		
		public utilisateursconnectesPage() {
			super(new GridLayout(0,1));
			
			try {
				if (co.getSocket().getUserList().isEmpty()) { //avec socket
				//if(connectedUserList.isEmpty()) { //sans socket
					this.erreuruc = new JLabel("Pas d'utilisateur connecté à afficher");
					this.add(this.erreuruc);
				}
				else {
					String[] psdo = pseudo_uc();
					this.utilisateurs = new JButton[nb_uc];
					for (int i=0;i<nb_uc;i++) {
						this.utilisateurs[i]= new JButton("connecté - "+ psdo[i]);
						//this.utilisateurs[i].setBackground(new Color(0xA4CBDF));
						if(inMsgNonLuPsdo(psdo[i])==-1) {
							this.utilisateurs[i].setBackground(new Color(0xA4CBDF));
						}
						else {
							this.utilisateurs[i].setBackground(new Color(0x86F5BA));
						}
						//this.utilisateurs[i].setMinimumSize(new Dimension(4000,4000));
						this.add(this.utilisateurs[i]);
						this.utilisateurs[i].addActionListener(this.acH);
					}
				}
			}catch(NullPointerException np) {
				this.erreuruc = new JLabel("Pas de liste d'utilisateurs");
				this.add(this.erreuruc);
			}
			
			try {
				cnc = db.getknownUsers(co.getLoggedAccount().getUsername());
				for (int i=0;i<cnc.size();i++) {
					System.out.println("ui "+cnc.get(i).getPseudo());
				}//test
			}catch(NullPointerException e) {
				System.out.println("knownusers vide");
			}
			//conversation_nc = db.getknownUsers();
			//if(conversation_nc.isEmpty()) {
			if(cnc.isEmpty()) {
				this.erreurnc = new JLabel("Pas d'historique de conversation à afficher");
				this.add(erreurnc);
			}
			else {
				//on regarde si les knownUsers ont une conv avec l'utilisateur logged, on enleve ceux qui n'en ont pas
				ArrayList<Address> cnc2 = new ArrayList<Address>();
				for (int j=0;j<cnc.size();j++) {
					if (!db.getConversation(co.getLoggedAccount().getUsername(), cnc.get(j).getUsername()).isEmpty()) {
						cnc2.add(cnc.get(j));
					}
				}
				cnc = cnc2;
				cnc2 = null; //free()
				//
				
				String[] psdonc = pseudo_nc(); 
				System.out.println(psdonc+" "+psdonc.length);//test
				if (psdonc.length == 0) {
					this.erreurnc = new JLabel("Pas d'historique de conversation à afficher");
					this.add(erreurnc);
				}
				else {
					this.utilisateursnc = new JButton[nb_nc];
					for (int i=0;i<nb_nc;i++) {
						this.utilisateursnc[i]= new JButton("déconnecté - "+ psdonc[i]);
						//this.utilisateursnc[i].setBackground(new Color(0xD4DADD));
						if(inMsgNonLuPsdo(psdonc[i])==-1) {
							this.utilisateursnc[i].setBackground(new Color(0xD4DADD));
						}
						else {
							this.utilisateurs[i].setBackground(new Color(0x86F5BA));
						}
						//this.utilisateurs[i].setMinimumSize(new Dimension(4000,4000));
						this.add(this.utilisateursnc[i]);
						this.utilisateursnc[i].addActionListener(this.acH);
					}
				}
			}
		
			this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		}
		
		String[] pseudo_uc() { //recup une liste de pseudo des uc
						
			String[] uc;
			int i=0;
			nb_uc = co.getSocket().getUserList().size(); //avec socket
			//nb_uc = connectedUserList.size(); //sans socket
			uc = new String[nb_uc];
			/*for (i=0;i<nb_uc;i++) {
				uc[i] = co.getSocket().getUserList().get(i).getPseudo(); //avec socket
				//uc[i] = connectedUserList.get(i).getPseudo(); //sans socket
			}*/
			for (Map.Entry<String,Address> entry : co.getSocket().getUserList().entrySet()) { //ConcurrentHashMap
				 uc[i]=entry.getValue().getPseudo();
				 i++;
			}
			
			return uc;
		}
		
		String[] pseudo_nc() { //recup une liste de pseudo des unc pour qui il existe une conv
			
			ArrayList<Address> co_nc = new ArrayList<Address>();
			nb_uc = co.getSocket().getUserList().size(); //avec socket
			//nb_uc = connectedUserList.size(); //sans socket
			int test=0;
			if (nb_uc != 0) {
				for (int j=0;j<cnc.size();j++) {
					/*for (int k=0;k<nb_uc;k++) {
					
						if (!cnc.get(j).getPseudo().equals(co.getSocket().getUserList().get(k).getPseudo())) { //avec socket
						//if (!cnc.get(j).getPseudo().equals(connectedUserList.get(k).getPseudo())) { //sans socket
							test++;
						}
						if (test==nb_uc) {
							co_nc.add(cnc.get(j));
						}
						
					}*/
					for (Map.Entry<String,Address> entry : co.getSocket().getUserList().entrySet()) { //ConcurrentHashMap
						 if(!cnc.get(j).getPseudo().equals(entry.getValue().getPseudo())){
							 test++;
						 }
						 if (test==nb_uc) {
							 co_nc.add(cnc.get(j));
						 }
					}
				test=0;
				}
			}
			else {
				co_nc = cnc;
			}
			String[] nc;
			nb_nc=co_nc.size();
			nc = new String[nb_nc];
			for (int i=0;i<nb_nc;i++) {
				nc[i] = co_nc.get(i).getPseudo();
			}
			
			return nc;
		}
	
	}
////////////////////////////////////////////////////////////////////////////////////////

///////////////PAGE DE CONVERSATION (JPanel)////////////////////////////////////////////

	class conversationPage extends JPanel{
		//private JTextArea[] discussion;
		private JLabel[] discussion;
		/*private JTextField message;
		private JButton envoi_message;
		private envoyermessageHandler emH = new envoyermessageHandler();*/
		
		public conversationPage() {
			super(new GridLayout(0,1));
			try {
				if (co.getConversation().isEmpty()) {
					//this.discussion = new JTextArea[1];
					//this.discussion[0] = new JTextArea("pas de conversation");
					this.discussion = new JLabel[1];
					this.discussion[0] = new JLabel("pas de conversation");
					this.discussion[0].setForeground(Color.RED);
					System.out.println("\n\n\n\n JE SUIS LA ");
				}
				else {
					//this.discussion = new JTextArea[co.getConversation().getConvSize()];
					this.discussion = new JLabel[co.getConversation().getConvSize()];
					Message[] m = co.getConversation().getAllMessages();
					System.out.println("message[] "+m[0].getMsg());//
					for (int i=0;i<co.getConversation().getConvSize();i++) {
						this.discussion[i] = new JLabel(retour_ligne(m[i].getMsg(),m[i].getTimestamp()));
						if (m[i].getIsEnvoyeur()) {
							this.discussion[i].setForeground(Color.BLUE);
							this.discussion[i].setHorizontalAlignment(SwingConstants.RIGHT);
						}
						else {
							this.discussion[i].setForeground(new Color(0x339933));
							this.discussion[i].setHorizontalAlignment(SwingConstants.LEFT);
						}
						this.add(this.discussion[i]);
					}
				} 
			} catch (NullPointerException e) {
				//this.discussion = new JTextArea[1];
				//this.discussion[0] = new JTextArea("pas de conversation");
				this.discussion = new JLabel[1];
				this.discussion[0] = new JLabel("pas de conversation");
				this.discussion[0].setForeground(Color.RED);
			}
			
	
			/*this.message = new JTextField();
			try {
			this.envoi_message = new JButton("envoyer le message à "+ co.getConversation().getDestinataire().getPseudo());
			} catch (NullPointerException e) {
				this.envoi_message = new JButton("envoyer le message");
			}
			this.add(this.message);
			this.add(this.envoi_message);
			
			this.envoi_message.addActionListener(this.emH);*/
			
			this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
		}
		
		/*public String retour_ligne(String str, Timestamp date) {
			String str_rtl;
			boolean saut_l = false;
			str_rtl = new String("");
			
			for (int i=1;i<str.length()+1;i++) {
				if(i%40 == 0) {
					saut_l=true;
				}
				if(str.charAt(i-1)==' ' && saut_l) {
					str_rtl = str_rtl + str.charAt(i-1) + "<br>";
					saut_l=false;
				}
				else {
					str_rtl = str_rtl + str.charAt(i-1);
				}
			}
			
			
			return "<html><font color=0x000000 size=1>"+date + "</font> : <br>" + str_rtl+"</html>";
		}*/
		
		
	}
	

	class msgPage extends JPanel{
		
		private JTextField message;
		private JButton envoi_message;
		private envoyermessageHandler emH = new envoyermessageHandler();
		
		public msgPage() {
			super(new GridLayout(0,1));
			this.message = new JTextField();
			try {
				this.envoi_message = new JButton("envoyer le message à "+ co.getConversation().getDestinataire().getPseudo());
			} catch (NullPointerException e) {
				this.envoi_message = new JButton("envoyer le message");
			}
			this.add(this.message);
			this.add(this.envoi_message);
			
			this.message.addActionListener(this.emH); //press enter
			this.envoi_message.addActionListener(this.emH);
			
			this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		}
		
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
		private JMenuItem debug;
		
		
		public MenuBar() {
			super();
			
			this.msysteme = new JMenu("Système");
			this.mconversation = new JMenu("Conversations");
			this.fermerapp = new JMenuItem("fermer l'application");
			this.deconnexion = new JMenuItem("se déconnecter");
			this.changercompte = new JMenuItem("changer de compte");
			this.changerpseudo = new JMenuItem("modifier le pseudo");
			this.creercompte = new JMenuItem("créer un compte");
			this.changerconversation = new JMenuItem("changer de conversation");
			this.userco = new JMenuItem("utilisateurs connectés");
			this.debug = new JMenuItem("debug");
			
			this.add(this.msysteme);
			this.add(this.mconversation);
			
			this.msysteme.add(this.deconnexion);
			this.deconnexion.addActionListener(new deconnexionHandler());
			this.msysteme.add(this.changercompte);
			this.changercompte.addActionListener(new deconnexionHandler());
			this.msysteme.add(this.changerpseudo);
			this.changerpseudo.addActionListener(new pagechangerpseudoHandler());
			this.msysteme.add(this.creercompte);
			this.creercompte.addActionListener(new pagecreationcompteHandler());
			this.msysteme.add(this.fermerapp);
			this.fermerapp.addActionListener(new fermerappHandler());
			
			this.mconversation.add(this.changerconversation);
			this.changerconversation.addActionListener(new changerconversationHandler());
			this.mconversation.add(this.userco);
			this.userco.addActionListener(new utilisateursconnectesHandler());
			this.mconversation.add(this.debug);
			this.debug.addActionListener(new debughandler());
		}
		
		
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
///////////////////////////////LES ACTIONS LISTENER///////////////////////////////////////////////////////
	private class pagecreationcompteHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(co.getLoggedAccount()!=null) {
				//db
				DBCentrale dbCentrale = new DBCentrale(co.getLoggedAccount().getUsername());
				dbCentrale.PushToDBC();
				//rzo
				co.getSocket().termine();
				co.setLoggedAccount(null);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				//
			}
			setCreationcomptePage();
			
		}
		
		
	}
	
	private class creationcompteHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String username_ = creationcomptepage.username.getText();
			String password_ = creationcomptepage.password.getText();
			String pseudo_ = creationcomptepage.pseudo.getText();
			ArrayList<Address> users = new ArrayList<Address>();
			
			boolean unique=true;
			try {
				//users = db.getknownUsers(co.getLoggedAccount().getUsername());
				//users = db.getknownUsers(null);
				users = db.getAllAccount();
			} catch (NullPointerException npe) {
				System.out.println(npe);
			}
			if (!users.isEmpty()) {
				for (int i=0;i<users.size();i++) {
					if (users.get(i).getUsername().equals(username_) || users.get(i).getPseudo().equals(pseudo_)) {
						unique=false;
					}
				}
			}
			if (unique) {
				Address add = new Address(pseudo_,username_);
				Account acc =  new Account(username_,password_,pseudo_,add);
				db.setAccount(acc);
				//db.setKnownUser(add,co.getLoggedAccount().getUsername());
				db.setKnownUser(add,acc.getUsername()); //il se connait lui-même
				
				connexionpage.erreur.setText("création du compte de "+pseudo_+" réussie");
				connexionpage.erreur.setForeground(Color.GREEN);
				setConnexionPage();
			}
			else {
				creationcomptepage.entete.setText("erreur: compte déjà existant");
				creationcomptepage.entete.setForeground(Color.RED);

			}
			
			
		}
		
	}
	
	private class connexionHandler implements ActionListener {
		

		@Override
		public void actionPerformed(ActionEvent e) {
			String username_ = connexionpage.username.getText();
			String password_ = connexionpage.password.getText();
			
			Account acc;

			acc = db.getAccount(username_, password_); //quand getpcip() marche
			
			if (acc == null) {
				connexionpage.erreur.setText("erreur de connexion");
				connexionpage.erreur.setForeground(Color.RED);
			}
			else {
				connexionpage.erreur.setText("Entrez username/password");
				connexionpage.erreur.setForeground(Color.BLACK);
				
				acc.setAddress(new Address(acc.getPseudo(),acc.getUsername())); //si on utilise getAccount2()
				System.out.println(acc.getAddress().getIP()); //test
				co.setLoggedAccount(acc);
				DBCentrale DBc  = new DBCentrale(acc.getUsername());
				DBc.PullDB();
				//rzo
				co.setSocket(new InternalSocket(acc,co.getUI()));
				//
				
				setUtilisateursconnectesPage_same_frame();
			}
			
			//+ vérifier qu'il n'y ai pas deja un uc avec ce compte
		}
		
		
	}
	
	private class deconnexionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//(...)//
			if (!(getContentPane().getComponents()[1].equals(creationcomptepage) || getContentPane().getComponents()[1].equals(connexionpage))) { //on est pas connecté
				//rzo
				co.getSocket().termine();
				co.setLoggedAccount(null); //voir si ça bug pas
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				//
			}
			connexionpage.erreur.setText("Entrez username/password");
			connexionpage.erreur.setForeground(Color.BLACK);
			setConnexionPage();
			corresp = null;
		}
		
		
	}
	
	private class pagechangerpseudoHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			changerpseudopage.erreur_cp.setText("entrez un nouveau pseudo");
			changerpseudopage.erreur_cp.setForeground(Color.BLACK);
			
			if(getContentPane().getComponents()[1].equals(connexionpage) || getContentPane().getComponents()[1].equals(creationcomptepage)) {
				connexionpage.erreur.setText("Impossible de changer le pseudo sans être connecté");
				connexionpage.erreur.setForeground(Color.RED);
				
				setConnexionPage();
			}
			else {
				setChangerpseudoPage();
			}
			
		}
		
		
	}
	
	private class changerpseudoHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String pseudo_ = changerpseudopage.pseudo.getText();
			
			
			//unicité sans serveur central (pas optimal)
			ArrayList<Address> users = new ArrayList<Address>();
			boolean unique=true;
			try {
				//users = db.getknownUsers(co.getLoggedAccount().getUsername());
				//users = db.getknownUsers(null); 
				users = db.getAllAccount();
			} catch (NullPointerException npe) {
				System.out.println(npe);
			}
			if (!users.isEmpty()) {
				for (int i=0;i<users.size();i++) {
					if (users.get(i).getPseudo().equals(pseudo_)) {
						unique=false;
					}
				}
			}
			//
			if (unique) {
				String old_psdo = co.getLoggedAccount().getPseudo();
				//rzo
				co.getSocket().sendNewPseudo(pseudo_, old_psdo);
				//logged account
				Address add=new Address(pseudo_,co.getLoggedAccount().getUsername());
				Account acc=new Account(co.getLoggedAccount().getUsername(),co.getLoggedAccount().getPassword(),pseudo_,add);
				co.setLoggedAccount(acc);
				//db
				//db.updatePseudo(pseudo_, old_psdo, co.getLoggedAccount().getUsername(), co.getLoggedAccount().getUsername()); //modifier le pseudo de son compte dans knownUsers (car on se connait soi-meme)
				db.updatePseudoAccount(co.getLoggedAccount().getUsername(), pseudo_);
				
				
				changerpseudopage.erreur_cp.setText("changement du pseudo réussi");
				changerpseudopage.erreur_cp.setForeground(Color.GREEN);
				//changerpseudopage.updateUI();
			}
			else {
				changerpseudopage.erreur_cp.setText("erreur: pseudo déjà existant");
				changerpseudopage.erreur_cp.setForeground(Color.RED);

			}
		}
		
		
	}
	
	//
	public void fermerapp() {
		System.out.println("EXIT APP");
		if(co.getLoggedAccount()!=null) {
			//db
			DBCentrale dbCentrale = new DBCentrale(co.getLoggedAccount().getUsername());
			dbCentrale.PushToDBC();
			dbCentrale.close();
			//rzo
			co.getSocket().termine();
			co.setLoggedAccount(null);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			//
		}
		
		System.exit(0);
	}
	private class fermerappHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {			
			fermerapp();
		}
		
	}
	private class FermetureApp implements WindowListener {
		
		public void windowClosing(WindowEvent e) {
			fermerapp();
		}

		public void windowOpened(WindowEvent e) {
			System.out.println("OPEN APP");
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	//
	
	private class changerconversationHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setUtilisateursconnectesPage_same_frame();
			corresp = null;
			
		}
		
		
	}
	
	private class utilisateursconnectesHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setUtilisateursconnectesPage_same_frame();
			corresp = null;
		}
		
		
	}
	
	private class afficherconversationHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			String but = e.getActionCommand();
			String correspsdo;
			//String corresp = null;
			ArrayList<Address> colist = db.getknownUsers(co.getLoggedAccount().getUsername());
			int n = but.length();
			if (but.charAt(0)=='d') { //c'est un utilisateur déconnecte
				correspsdo = but.substring(13,n);
				for (int i=0;i<colist.size();i++) {
					if (correspsdo.equals(colist.get(i).getPseudo())) {
						corresp = colist.get(i).getUsername();
						
					}
				}
				if (corresp == null) {
					System.out.println("ERREUR: cas impossible -> un utilisateur déconnecté est forcément dans knownusers");
				}
				else {
					System.out.println("UI "+corresp); //test
					System.out.println("UI "+co.getLoggedAccount().getUsername()); //test
					co.setConversation(db.getConversation(co.getLoggedAccount().getUsername(), corresp));
				}
				setConversationPage_nc();
			}
			else { //c'est un utilisateur connecte
				correspsdo = but.substring(11,n);
				for (int i=0;i<colist.size();i++) {
					if (correspsdo.equals(colist.get(i).getPseudo())) {
						corresp = colist.get(i).getUsername();
					}
				}
				if (corresp == null) {
					
					Address add=null;
					//ConcurrentHashMap
					for (Map.Entry<String,Address> entry : co.getSocket().getUserList().entrySet()) { //ConcurrentHashMap
						 if(entry.getValue().getPseudo().equals(correspsdo)) {
							 add = entry.getValue();
						 }
						 
					}
					//avec socket
					/*for (int j=0;j<co.getSocket().getUserList().size();j++) {
						if (co.getSocket().getUserList().get(j).getPseudo().equals(correspsdo)) {
							add = co.getSocket().getUserList().get(j);
						}
					}*/
					//
					//sans socket
					/*for (int j=0;j<connectedUserList.size();j++) { 
						if (connectedUserList.get(j).getPseudo().equals(correspsdo)) {
							add = connectedUserList.get(j);
						}
					}*/
					//
					if (add == null) {
						System.out.println("ERREUR: cas impossible -> un utilisateur connecté est forcément dans connectedUserList");
					}
					else {
						db.setKnownUser(add,co.getLoggedAccount().getUsername());
						co.setConversation(new Conversation(add));
					}
					corresp=add.getUsername();
				}
				else {
					
					co.setConversation(db.getConversation(co.getLoggedAccount().getUsername(), corresp));
					System.out.println("conversation "+co.getConversation().getDestinataire()+" "+co.getConversation().getConvSize());
				}
				setConversationPage();
			}
			//on enleve corresp de UserMsgNonLu
			int in = inMsgNonLu(corresp);
			if(in!=-1) {
				UserMsgNonLu.remove(in);
			}
			//
		}
		
		
	}
	
	private class envoyermessageHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//maj conv
			Message msg = new Message(true,msgpage.message.getText());
			co.getConversation().addMessage(msg);
			//rzo
			co.getSocket().sendMessage(msg, corresp);
			//
			//maj db	
			db.setMessage(msg, co.getLoggedAccount().getUsername(), corresp);
			//afficher new message
			JLabel newm =new JLabel(retour_ligne(msg.getMsg(),msg.getTimestamp()));
			newm.setHorizontalAlignment(SwingConstants.RIGHT);
			newm.setForeground(Color.BLUE);
			conversationpage.add(newm);
			//rafraichissement
			conversationpage.updateUI();
			
			msgpage.message.setText("");
			
			//setConversationPage();
			
		}
		
		
	}
	
	public void recevoirmessageUI(Message msg, Address sender){ //a utiliser uniquement si le message reçu vient de la conversation chargée
		
		//test
		System.out.println(co.getConversation().getDestinataire().getUsername());

		/*
		//maj db


		db.setMessage(msg, sender.getUsername(), co.getLoggedAccount().getUsername());
		//on met le sender dans known user si on le connait pas
		
		//vérifie si sender dans knownusers puis ajoute
		ArrayList<Address> ku = db.getknownUsers(co.getLoggedAccount().getUsername());
		boolean connu = false;
		for (int i=0;i<ku.size();i++) {
			if (ku.get(i).getUsername().equals(sender.getUsername()) && ku.get(i).getIP().equals(sender.getIP())/*a voir si on regarde aussi l'IP*//*) {
				connu = true;
			}
		}
		if (!connu) {
			db.setKnownUser(sender,co.getLoggedAccount().getUsername());
		}
		//*/
		
		
			
			
		if(co.getConversation().getDestinataire().getUsername().equals(sender.getUsername()) && getContentPane().getComponents()[1].equals(scrollbar_conv)) { //on est sur la page conversation avec celui qui envoie le message
			//maj conv
			co.getConversation().addMessage(msg);
			//afficher new message
			JLabel newm =new JLabel(retour_ligne(msg.getMsg(),msg.getTimestamp()));
			newm.setHorizontalAlignment(SwingConstants.LEFT);
			newm.setForeground(new Color(0x339933));
			conversationpage.add(newm);
			//rafraichissement
			conversationpage.updateUI();
		}
		else {
			if(inMsgNonLu(sender.getUsername())==-1) {
				UserMsgNonLu.add(sender);
			}
		}
		
		if (getContentPane().getComponents()[1].equals(scrollbar_uc)) { //on est sur la page utilisateur connectes
			setUtilisateursconnectesPage_same_frame();
		}		
		
	}
	
	public void refreshPageUserCo() {
		if (getContentPane().getComponents()[1].equals(scrollbar_uc)) {
			setUtilisateursconnectesPage_same_frame();
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
//////UTILITAIRE///////
	public int inMsgNonLu(String username) { //-1 si username pas dans UserMsgNonLu, rang de username sinon
		int in = -1;
		if(!UserMsgNonLu.isEmpty() && username!=null) {
			for(int i=0;i<UserMsgNonLu.size();i++) {
				if(UserMsgNonLu.get(i).getUsername().equals(username)) {
					in = i;
				}
			}
		}
		return in;
	}
	public int inMsgNonLuPsdo(String psdo) { //-1 si psdo pas dans UserMsgNonLu, rang de psdo sinon
		int in = -1;
		if(!UserMsgNonLu.isEmpty() && psdo!=null) {
			for(int i=0;i<UserMsgNonLu.size();i++) {
				if(UserMsgNonLu.get(i).getPseudo().equals(psdo)) {
					in = i;
				}
			}
		}
		return in;
	}
	
	public String retour_ligne(String str, Timestamp date) {
		String str_rtl;
		boolean saut_l = false;
		str_rtl = new String("");
		
		for (int i=1;i<str.length()+1;i++) {
			if(i%40 == 0) {
				saut_l=true;
			}
			if(str.charAt(i-1)==' ' && saut_l) {
				str_rtl = str_rtl + str.charAt(i-1) + "<br>";
				saut_l=false;
			}
			else {
				str_rtl = str_rtl + str.charAt(i-1);
			}
		}
		
		
		return "<html><font color=0x000000 size=1>"+date + "</font> : <br>" + str_rtl+"</html>";
	}
////////////////////////////
	
////debug
	private class debughandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			db.printAllTable();
			
		}
		
		
	}
}
