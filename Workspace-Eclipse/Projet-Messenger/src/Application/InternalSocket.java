package Application;

import java.util.ArrayList;
import java.util.Iterator;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.Thread;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;


/* MUST BE SYNCHRONIZED, EACH TIME YOU CALL an INTERNALSOCKET object
 * Peut etre source de pb -> deadlock : Essayer de conserver un unique synchronized*/
public class InternalSocket implements NetworkSocketInterface {
	ArrayList<Address> connectedUserList = new ArrayList<Address>(); // Need to be synchronized
	protected static final int UDP_PORT_RCV = 6666;
	protected static final int UDP_PORT_SEND = 6667;
	protected static final int TCP_PORT_RCV= 6668;
	protected static final int TCP_PORT_SEND = 6669;
	protected static final int MAX_CHAR = 256;
	protected static final String  CONNECTED =  "Connected";
	protected static final String  DISCONNECTED =  "Disconnected";
	protected static final String  MESSAGE =  "Message";
	protected static final String NEW_PSEUDO = "New_Pseudo";
	protected static final String END_MESSAGE = "ef399b2d446bb37b7c32ad2cc1b6045b";
	protected final String UsernameLogged;
	DatagramSocket UDP_SEND_Socket;
	protected DBLocale db;
	
	
	
	public InternalSocket(String UsernameLoggedAccount_){
		this.UsernameLogged = UsernameLoggedAccount_;
		this.connectedUserList = new ArrayList<Address>();
		this.db = new DBLocale();
		this.startReceiverThread();
		
		try {
			this.UDP_SEND_Socket = new DatagramSocket(InternalSocket.UDP_PORT_SEND);
		} catch (SocketException e) {
			System.out.println("Internal Socket: Error init UDP socket in constructor");
			e.printStackTrace();
		}
		
	}
	
	List<InetAddress> listAllBroadcastAddresses() throws SocketException {
	    List<InetAddress> broadcastList = new ArrayList<>();
	    Enumeration<NetworkInterface> interfaces 
	      = NetworkInterface.getNetworkInterfaces();
	    while (interfaces.hasMoreElements()) {
	        NetworkInterface networkInterface = interfaces.nextElement();
	 
	        if (networkInterface.isLoopback() || !networkInterface.isUp()) {
	            continue;
	        }
	 
	        networkInterface.getInterfaceAddresses().stream() 
	          .map(a -> a.getBroadcast())
	          .filter(Objects::nonNull)
	          .forEach(broadcastList::add);
	    }
	    return broadcastList;
	}

	@Override
	public void sendConnected(Account loggedAccount) {

		try {
			this.UDP_SEND_Socket.setBroadcast(true);
			
		} catch (SocketException e) {
			System.out.println("InternalSocket: Error sendConnected");
			e.printStackTrace();
		}
		String message = InternalSocket.CONNECTED.toString() + "\n" + loggedAccount.getPseudo() + "\n" + loggedAccount.getUsername() + "\n" + (new Timestamp(System.currentTimeMillis())).toString();
		try {
			DatagramPacket outPacket = new DatagramPacket(message.getBytes(),message.length(),listAllBroadcastAddresses().get(0), InternalSocket.UDP_PORT_RCV);
			this.UDP_SEND_Socket.send(outPacket);
		} catch ( IOException e) {
			System.out.println("InternalSocket: Error dans sendConnected");
			e.printStackTrace();
		}
	}
	
	public void sendNewPseudo(String New_Pseudo, String oldPseudo) {
		try {
			this.UDP_SEND_Socket.setBroadcast(true);
			String message = InternalSocket.NEW_PSEUDO.toString() + "\n" + New_Pseudo + "\n" + this.UsernameLogged + "\n" + oldPseudo + "\n" + (new Timestamp(System.currentTimeMillis())).toString();
			DatagramPacket outPacket = new DatagramPacket(message.getBytes(),message.length(),listAllBroadcastAddresses().get(0), InternalSocket.UDP_PORT_RCV);
			this.UDP_SEND_Socket.send(outPacket);
		} catch (IOException e) {
			System.out.println("InternalSocket: Error dans sendNewPseudo");
			e.printStackTrace();
		}
		
	}

	@Override
	public void isServerUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Address> getUserList() {
		// TODO Auto-generated method stub
		return connectedUserList;
	}
	@Override
	public synchronized void  setUserList(ArrayList<Address> ul) {
		// TODO Auto-generated method stub
		this.connectedUserList = ul;
	}

	@Override
	public void sendMessage(Message msg, String Username) {
		// TODO Auto-generated method stub
		Address res = null;
		Boolean fin = false;
		synchronized (connectedUserList) {
			Iterator<Address> iter = connectedUserList.iterator();

			while (iter.hasNext() && !fin) {
			 res = iter.next();
			 if(res.getUsername().equals(Username)) {
				 fin = true;
			 }
			}
		}
		System.out.println("InternalSocket: addr find " + res.getIP());
		String message = InternalSocket.MESSAGE.toString() + "\n" +  UsernameLogged + "\n" + Username + "\n" + msg.getTimestamp().toString() + "\n" + msg.getMsg();
		System.out.println("InternalSocket: msg : " + message);
		InetAddress addrRcv = res.getIP();
		try {
			Socket TCP_SEND_Socket = new Socket(addrRcv, InternalSocket.TCP_PORT_RCV);
			PrintWriter out = new PrintWriter(TCP_SEND_Socket.getOutputStream(),true);
			out.println(message);
			TCP_SEND_Socket.close();
		} catch (IOException e) {
			System.out.println("InternalSocket: Error Send message création Socket");
			e.printStackTrace();
		}
		
	}

	@Override
	public void startReceiverThread() {
		// TODO Auto-generated method stub
		System.out.println("InternalSocket: starting RECEIVER UDP AND TCP THREADS . . .");
		TCPThreadReceiver TCP = new TCPThreadReceiver(this.db, UsernameLogged,this.connectedUserList);
		UDPThreadReceiver UDP = new UDPThreadReceiver(this.connectedUserList, this.db);
	}

	@Override
	public ArrayList<Conversation> getHistorique() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendHistorique(ArrayList<Conversation> lh) {
		// TODO Auto-generated method stub

	}
	
	

	@Override
	public void sendDisconnected(Account loggedAccount) {
		String message = InternalSocket.DISCONNECTED.toString() + "\n" + loggedAccount.getUsername() + "\n" + loggedAccount.getPseudo() + "\n" + (new Timestamp(System.currentTimeMillis())).toString();;
		try {
			DatagramPacket outPacket = new DatagramPacket(message.getBytes(),message.length(),listAllBroadcastAddresses().get(0), InternalSocket.UDP_PORT_RCV);
			this.UDP_SEND_Socket.send(outPacket);
			this.UDP_SEND_Socket.close();
		} catch ( IOException e) {
			System.out.println("InternalSocket: Error dans sendConnected");
			e.printStackTrace();
		}
		
		/*InetAddress otherIP;
		synchronized (connectedUserList) {
			for (int i = 0; i < connectedUserList.size(); i++) {
				
				otherIP = connectedUserList.get(i).getIP();
				String message = InternalSocket.DISCONNECTED.toString() + "\n" + loggedAccount.getUsername() + "\n" + loggedAccount.getPseudo() + "\n" + (new Timestamp(System.currentTimeMillis())).toString();;
				DatagramPacket outPacket = new DatagramPacket(message.getBytes(),message.length(),otherIP, InternalSocket.UDP_PORT_RCV);
				try {
					Socket.send(outPacket);
				} catch( IOException e) {
					System.out.println("Error Sending in send connected");
				}
			}
		}*/
	}

}

class UDPThreadReceiver extends Thread {
	DatagramSocket receiver;
	ArrayList<Address> connectedUserList;
	DBLocale db;
	
	public UDPThreadReceiver(ArrayList<Address> _connectedUserList, DBLocale _db) {
		super();
		this.db = _db;
		this.connectedUserList = _connectedUserList;
		System.out.println("ThreadReceiver: starting . . .");
		try {
			receiver = new DatagramSocket(InternalSocket.UDP_PORT_RCV);
		} catch (SocketException e) {
			System.out.println("UDPThreadReceiver: Error creatino socket");
			e.printStackTrace();
		}
		this.start();
		
	}
	
	@Override
	public void run(){
		System.out.println("UDPThreadReceiver: running . . .");
		while(true) {
			byte[] buffer = new byte[InternalSocket.MAX_CHAR];
			DatagramPacket inPacket = new DatagramPacket(buffer,buffer.length);
			try {
				System.out.println("UDPThreadReceiver: waiting for messages");
				receiver.receive(inPacket);
				
				InetAddress clientAddress = inPacket.getAddress();
				String message = new String (inPacket.getData(), 0, inPacket.getLength());
				BufferedReader reader = new BufferedReader(new StringReader(message));
				String line = reader.readLine();
				if (line.contains(InternalSocket.CONNECTED)) {
					System.out.println("UDPThreadReceiver: Connected received: " + message);
					synchronized(this.connectedUserList) {
						this.connectedUserList.add(new Address(clientAddress,reader.readLine(), reader.readLine()));
						
					}
				}else if (line.contains(InternalSocket.DISCONNECTED)) {
					System.out.println("UDPThreadReceiver: Disconnected received: " + message);
					synchronized(this.connectedUserList) {
						this.connectedUserList.remove(new Address(clientAddress,reader.readLine(), reader.readLine()));
					}
				}else if (line.contains(InternalSocket.NEW_PSEUDO)){
					System.out.println("UDPThreadReceiver: New_Pseudo received: " + message);
					synchronized(this.connectedUserList) {
						String new_pseudo = reader.readLine();
						String username = reader.readLine();
						String old_pseudo = reader.readLine();
						this.connectedUserList.add(new Address(InetAddress.getByAddress(clientAddress.getAddress()),new_pseudo, username));
						Boolean fin = false;
						Iterator<Address> iter = this.connectedUserList.iterator();
						Address tempor;
;						while (!fin && iter.hasNext()) {
							tempor = iter.next();
							if(tempor.getPseudo().equals(old_pseudo)) {
								this.connectedUserList.remove(tempor);
								fin = true;
							}
						}
						this.connectedUserList.remove(new Address(InetAddress.getByAddress(clientAddress.getAddress()),old_pseudo, username));
					}
				}else {
					System.out.println("UDPThreadReceiver: Unknown message received: " + message);
				}
				
			}catch (IOException e) {
				System.out.println("UDPThreadReceiver: Error thread");
			}
		}
		
		
	}
}
	
	class TCPThreadReceiver extends Thread {
		ServerSocket receiver;
		DBLocale db;
		ArrayList<Address> coUsers;
		int n;
		String UsernameLogged;
		public TCPThreadReceiver(DBLocale db_, String _UsernameLogged, ArrayList<Address> _coUsers) {
			super();
			this.coUsers = _coUsers;
			this.UsernameLogged = _UsernameLogged;
			this.db = db_;
			try {
				receiver = new ServerSocket(InternalSocket.TCP_PORT_RCV);
			} catch (IOException e) {
				System.out.println("TCPThreadReceiver: Error constructor ServSocket");
				e.printStackTrace();
			}
			this.n = 0;
			System.out.println("TCPThreadreceiver: Creation ServSocket");
			this.start();
			
		}
		
		public void run() {
			while(true) {
				try {
					Socket clientSocket = receiver.accept();
					n++;
					System.out.println("TCPThreadReceiver: Creation Socket fils en cours . . .");
					ThreadSocketFils temp = new ThreadSocketFils(clientSocket, n, db,UsernameLogged, this.coUsers);
					
				} catch (IOException e) {
					System.out.println("TCPThreadReceiver: Error accept");
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	
	class ThreadSocketFils extends Thread{
		Socket son;
		int n;
		DBLocale db;
		ArrayList<Address> coUsers;
		String UsernameLogged;
		ThreadSocketFils(Socket chassot, int a, DBLocale db_, String _UsernameLogged, ArrayList<Address> _coUsers){
			this.UsernameLogged = _UsernameLogged;
			this.db= db_;
			this.coUsers = _coUsers;
			son = chassot;
			n =a;
			System.out.println("ThreadSocketFils" + n + ": creation ThreadSocketfils . . .");
			this.start();
		}
		
		@Override
		public void run(){
			try {
				System.out.println("ThreadSocketFils" + n + ": Succesfully created");
				BufferedReader in = new BufferedReader(new InputStreamReader(son.getInputStream()));
				Boolean fin = false;
				String message = "";
				String sender = "";
				String rcv = "";
				Timestamp ts = null;
				String temp = in.readLine();
				while(temp !=null) {
					System.out.println("ThreadSocketFils" + n + ": msg received: " + temp);
					if(temp.equals(InternalSocket.MESSAGE)){	
						sender = in.readLine();
						rcv = in.readLine();
						ts = Timestamp.valueOf(in.readLine());
;					}else if (temp.equals(InternalSocket.END_MESSAGE)) {
						fin = true;
					}else {
		                message += temp+ "\n";
					}
					temp = in.readLine();
				}
				if( UsernameLogged.equals(rcv)) {
					Address temporary = this.db.getSpecificKnownUser(UsernameLogged, sender);
					if (temporary == null) {
						synchronized(this.coUsers) {
							boolean fin2 = false;
							Iterator<Address> lol = this.coUsers.iterator();
							Address tempor = lol.next();
							while(!fin2 && lol.hasNext()) {
								if (tempor.getUsername().equals(sender)) {
									fin2 = true;
									this.db.setKnownUser(new Address(tempor.getIP(),tempor.getPseudo(),tempor.getUsername()), UsernameLogged);
								}
							}
						}
						
					}
					
					this.db.setMessage(new Message(false,message,true,ts),sender,rcv);
				}else {
					System.out.println("ThreadSocketFils" + n +": Msg rejeté car mauvais destinataire");
				}
				
				System.out.println("ThreadSocketFils" + n +": Closing . . .");
				son.close();
			}catch(IOException e) {
					System.out.println("ThreadSocketFils" + n + ": Error accept");
					e.printStackTrace();
			}
		}
			
		}
	
	class Con implements Serializable{
		private static final long serialVersionUID = 7732913674841021379L;
		private Address addr;
		public Con (Address adr) {
			addr =adr;
		}
		
	}
	
	
		
	
	
	

