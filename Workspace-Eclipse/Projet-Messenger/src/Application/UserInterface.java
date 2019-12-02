package Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.util.EventObject;

class UserInterface{
	
	connectionPage connectionpage;
	
	public UserInterface() {
		/*SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});*/
		
		initGUI();
		
	}
	
	
	
	void initGUI() {
		this.connectionpage = new connectionPage();
		
	}
	
	void launchGUI() {
		this.connectionpage.setVisible(true);
		
	}
	
	class connectionPage extends JFrame{
		JTextField username;
		JTextField password;
		JButton connexion;
		JButton creation;
		connectionHandler coH = new connectionHandler();
		creationcompteHandler crH = new creationcompteHandler();
		JPanel panel ;
		
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
			
			this.getContentPane().add(this.panel, BorderLayout.CENTER);
			
			this.setSize(new Dimension(500,500));
		}
		
		
		class connectionHandler implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
		}
		
		class creationcompteHandler implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
		}
	}

}
