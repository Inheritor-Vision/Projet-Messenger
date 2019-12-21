package Application;

import javax.swing.*;
import java.lang.String;
import java.awt.*;
import java.awt.event.*;
//import java.util.EventObject;
import java.util.ArrayList;
import java.sql.Timestamp;

class UserInterface extends JFrame{
	//juste pour test
	ArrayList<Address> connectedUserList = new ArrayList<Address>();
	//
	ArrayList<Address> conversation_nc = new ArrayList<Address>();
	
	Controller co;
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
	
	
	public UserInterface(/*ArrayList<Address> connectedUserList/*pour test*/) {
		/*super("Messenger");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});*/
		
		super("Messenger");
		//this.connectedUserList = connectedUserList; //pour test
		initGUI();
		
	}
	
	
	
	void initGUI() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			
			this.connexion.addActionListener(this.coH);
			this.creation.addActionListener(this.crH);
			
			this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
		}
		
		
	}
////////////////////////////////////////////////////////////////////////////////////////
	
//////////////////////////////////PAGE DE CREATION COMPTE (JPanel)////////////////////////////////////////////////////
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
	
//////////////////////////////////PAGE DE CHANGEMENT DE PSEUDO(JPanel)////////////////////////////////////////////////////
	class changerpseudoPage extends JPanel{
		private JTextField pseudo;
		private JButton creation;
		private changerpseudoHandler cpH = new changerpseudoHandler();
		
		public changerpseudoPage() {
			super(new GridLayout(0,1));
			
			
			this.pseudo = new JTextField("pseudo");
			this.creation = new JButton("changer");
			
			this.add(this.pseudo);
			this.add(this.creation);
			
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
	
		
		public utilisateursconnectesPage() {
			super(new GridLayout(0,1));
			
			//if (co.getSocket().getUserList().isEmpty()) {
			if(connectedUserList.isEmpty()) { //pour test
				this.erreuruc = new JLabel("Pas d'utilisateur connecté à afficher");
				this.add(this.erreuruc);
			}
			else {
				String[] psdo = pseudo_uc();
				this.utilisateurs = new JButton[nb_uc];
				for (int i=0;i<nb_uc;i++) {
					this.utilisateurs[i]= new JButton("connecté - "+ psdo[i]);
					//this.utilisateurs[i].setMinimumSize(new Dimension(4000,4000));
					this.add(this.utilisateurs[i]);
					this.utilisateurs[i].addActionListener(this.acH);
				}
			}
			if(conversation_nc.isEmpty()) {
				this.erreurnc = new JLabel("Pas d'historique de conversation à afficher");
				this.add(erreurnc);
			}
			else {
				String[] psdonc = pseudo_nc();
				this.utilisateursnc = new JButton[nb_nc];
				for (int i=0;i<nb_nc;i++) {
					this.utilisateursnc[i]= new JButton("déconnecté - "+ psdonc[i]);
					//this.utilisateurs[i].setMinimumSize(new Dimension(4000,4000));
					this.add(this.utilisateursnc[i]);
					this.utilisateursnc[i].addActionListener(this.acH);
				}
			}
		
			this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		}
		
		String[] pseudo_uc() { //recup une liste de pseudo des uc
			/*String[] uc;
			nb_uc = co.getSocket().getUserList().size();
			uc = new String[nb_uc];
			for (int i=0;i<nb_uc;i++) {
				uc[i] = co.getSocket().getUserList().get(i).getPseudo();
			}
			
			return uc;*/
			
			//pour test
			String[] uc;
			nb_uc = connectedUserList.size();
			uc = new String[nb_uc];
			for (int i=0;i<nb_uc;i++) {
				uc[i] = connectedUserList.get(i).getPseudo();
			}
			
			return uc;
		}
		
		String[] pseudo_nc() { //recup une liste de pseudo des unc opur qui il existe une conv
			/*ArrayList<Address> co_nc = new ArrayList<Address>();
			nb_uc = connectedUserList.size();
			int test=0;
			for (int j=0;j<conversation_nc.size();j++) {
				for (int k=0;k<nb_uc;k++) {
				
					if (!conversation_nc.get(j).getPseudo().equals(co.getSocket().getUserList().get(k).getPseudo())) {
						test++;
					}
					if (test==nb_uc) {
						co_nc.add(conversation_nc.get(j));
					}
					
				}
			test=0;
			}
			String[] nc;
			nb_nc=co_nc.size();
			nc = new String[nb_nc];
			for (int i=0;i<nb_nc;i++) {
				nc[i] = co_nc.get(i).getPseudo();
			}
			
			return nc;
		*/
			
			//pour test
			ArrayList<Address> co_nc = new ArrayList<Address>();
			nb_uc = connectedUserList.size();
			int test=0;
			for (int j=0;j<conversation_nc.size();j++) {
				for (int k=0;k<nb_uc;k++) {
				
					if (!conversation_nc.get(j).getPseudo().equals(connectedUserList.get(k).getPseudo())) {
						test++;
					}
					if (test==nb_uc) {
						co_nc.add(conversation_nc.get(j));
					}
					
				}
			test=0;
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
				}
				else {
					//this.discussion = new JTextArea[co.getConversation().getConvSize()];
					this.discussion = new JLabel[co.getConversation().getConvSize()];
					Message[] m = co.getConversation().getAllMessages();
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
				/*utilisateursconnectespage = new utilisateursconnectesPage();
				scrollbar_uc = new JScrollPane(utilisateursconnectespage);
				scrollbar_uc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);*/
				//scrollbar_uc.setBounds(50, 30, 300, 50);
				setUtilisateursconnectesPage_same_frame();
			}
			else {
				connexionpage.erreur.setText("erreur de connexion");
			}
			
			/*if (exist(username_, password_)) {
				conversation_nc = pull_conversation(username_);
				co.getLoggedAccount() = pull_account(username_);
				
			}
			else {
				connexionpage.erreur.setText("erreur de connexion");
			}*/
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
	
	private class pagechangerpseudoHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			setChangerpseudoPage();
			
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
			
			
			String but = e.getActionCommand();
			if (but.charAt(0)=='d') { //c'est un utilisateur déconnecte
				//(...) màj co.conversation//
				setConversationPage_nc();
			}
			else {
				//(...) màj co.conversation//	
				setConversationPage();
			}
			
			
		}
		
		
	}
	
	private class envoyermessageHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//maj conv
			Message msg = new Message(true,msgpage.message.getText(),true);
			co.getConversation().addMessage(msg);
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
	
	public void recevoirmessageUI(Message msg){ //a utiliser uniquement si le message reçu vient de la conversation chargée
		
		
		//maj conv
		co.getConversation().addMessage(msg);
		
		//test
		System.out.println(getContentPane().getComponents()[0].equals(scrollbar_conv));
		System.out.println(getContentPane().getComponents()[1].equals(scrollbar_conv));
		int n=getContentPane().getComponents().length;
		for (int i=0;i<n;i++) {
			System.out.println(getContentPane().getComponents()[i].getName());
		}
		//
		
		if(getContentPane().getComponents()[1].equals(scrollbar_conv)) {
			//afficher new message
			JLabel newm =new JLabel(retour_ligne(msg.getMsg(),msg.getTimestamp()));
			newm.setHorizontalAlignment(SwingConstants.LEFT);
			newm.setForeground(new Color(0x339933));
			conversationpage.add(newm);
			//rafraichissement
			conversationpage.updateUI();
		}
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
//////UTILITAIRE///////
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
}
