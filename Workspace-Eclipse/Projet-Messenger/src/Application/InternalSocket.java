package Application;

import java.util.ArrayList;
import java.util.Iterator;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.Thread;
import java.lang.reflect.Constructor;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.sun.net.httpserver.*;
import Common.Tools;
import Common.Address;


/* MUST BE SYNCHRONIZED, EACH TIME YOU CALL an INTERNALSOCKET object
 * Peut etre source de pb -> deadlock : Essayer de conserver un unique synchronized*/
public class InternalSocket {
	ConcurrentHashMap<String,Address> connectedUserList = new ConcurrentHashMap<String,Address>(); // Need to be synchronized
	
	protected final Account UsernameLogged;
	protected static final int MAX_CHAR = 256;
	protected static final String PresenceServer = "https://srv-gei-tomcat.insa-toulouse.fr/Messenger/PresenceServer";
	DatagramSocket UDP_SEND_Socket;
	UDPThreadReceiver UDP_RCV_Thread;
	TCPThreadReceiver TCP_RCV_Thread;
	protected DBLocale db;
	protected UserInterface UI;
	protected static Long ts;
	
	
	
	public InternalSocket(Account UsernameLoggedAccount_, UserInterface _UI){
		this.ts = 0L;
		this.UsernameLogged = UsernameLoggedAccount_;
		this.connectedUserList = new ConcurrentHashMap<String,Address>();
		this.db = new DBLocale();
		this.UI = _UI;
		try {
			this.UDP_SEND_Socket = new DatagramSocket(Tools.Ports.UDP_SEND.getValue());
		} catch (SocketException e) {
			System.out.println("Internal Socket: Error init UDP socket in constructor");
			e.printStackTrace();
		}
		this.startReceiverThread();
		this.sendConnected(UsernameLogged);
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

	
	public void sendConnected(Account loggedAccount) {
		while (!UDP_RCV_Thread.isAlive() ) {
			
		}
		try {
			this.UDP_SEND_Socket.setBroadcast(true);
			
		} catch (SocketException e) {
			System.out.println("InternalSocket: Error sendConnected");
			e.printStackTrace();
		}
		System.out.println("InternalSocket: BROADCAST SENT");
		String message = Tools.Msg_Code.Connected.toString() + "\n" + loggedAccount.getPseudo() + "\n" + loggedAccount.getUsername() + "\n" + (new Timestamp(System.currentTimeMillis())).toString();
		try {
			DatagramPacket outPacket = new DatagramPacket(message.getBytes(),message.length(),listAllBroadcastAddresses().get(0), Tools.Ports.UDP_RCV.getValue());
			this.UDP_SEND_Socket.send(outPacket);
		} catch ( IOException e) {
			System.out.println("InternalSocket: Error dans sendConnected");
			e.printStackTrace();
		}
		this.notifyConnexionServlet(loggedAccount);
		
	}
	
	private void notifyConnexionServlet(Account loggedAccount) {
		HttpClient httpClient = HttpClient.newBuilder()
	            .version(HttpClient.Version.HTTP_2)
	            .build();
		
		Map<Object, Object> data = new HashMap<>();
        data.put("pseudo", loggedAccount.getPseudo());
        data.put("username", loggedAccount.getUsername());
        data.put("addr1", loggedAccount.getAddress().getIP().getAddress()[0]);
        data.put("addr2", loggedAccount.getAddress().getIP().getAddress()[1]);
        data.put("addr3", loggedAccount.getAddress().getIP().getAddress()[2]);
        data.put("addr4", loggedAccount.getAddress().getIP().getAddress()[3]);
        data.put("add","1");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(Tools.buildFormDataFromMap(data))
                .uri(URI.create(InternalSocket.PresenceServer))
                .setHeader("User-Agent", "MessengerApp") 
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        try {
			httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			System.out.println("InternalSocket: Error 1 dans notifyConnexionServlet");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("InternalSocket: Error 2 dans notifyConnexionServlet");
			e.printStackTrace();
		}
	}
	
	protected void getCoListfromServer() {
		HttpClient httpClient = HttpClient.newBuilder()
	            .version(HttpClient.Version.HTTP_2)
	            .build();
		HttpRequest request = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(InternalSocket.PresenceServer))
				.setHeader("User-Agent", "MessengerApp")
				.build();
		 try {
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
		} catch (IOException e) {
			System.out.println("InternalSocket: Error 1 dans getCoListfromServer");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("InternalSocket: Error 2 dans getCoListfromServer");
			e.printStackTrace();
		}
	}
	
	public void sendNewPseudo(String New_Pseudo, String oldPseudo) {
		try {
			this.UDP_SEND_Socket.setBroadcast(true);
			String message = Tools.Msg_Code.New_Pseudo.toString() + "\n" + New_Pseudo + "\n" + this.UsernameLogged.getUsername() + "\n" + oldPseudo + "\n" + (new Timestamp(System.currentTimeMillis())).toString();
			DatagramPacket outPacket = new DatagramPacket(message.getBytes(),message.length(),listAllBroadcastAddresses().get(0), Tools.Ports.UDP_RCV.getValue());
			this.UDP_SEND_Socket.send(outPacket);
		} catch (IOException e) {
			System.out.println("InternalSocket: Error dans sendNewPseudo");
			e.printStackTrace();
		}
		
	}
	
	public void termine() {
		this.sendDisconnected(UsernameLogged);
		this.notifyDiscoServer(UsernameLogged);
		this.TCP_RCV_Thread.setStop();
		this.UDP_RCV_Thread.setStop();
		DBCentrale dbCentrale = new DBCentrale(UsernameLogged.getUsername());
		dbCentrale.PushToDBC();
	}
	
	public void notifyDiscoServer(Account loggedAccount) {
		HttpClient httpClient = HttpClient.newBuilder()
	            .version(HttpClient.Version.HTTP_2)
	            .build();
		
		Map<Object, Object> data = new HashMap<>();
        data.put("pseudo", loggedAccount.getPseudo());
        data.put("username", loggedAccount.getUsername());
        data.put("addr1", loggedAccount.getAddress().getIP().getAddress()[0]);
        data.put("addr2", loggedAccount.getAddress().getIP().getAddress()[1]);
        data.put("addr3", loggedAccount.getAddress().getIP().getAddress()[2]);
        data.put("addr4", loggedAccount.getAddress().getIP().getAddress()[3]);
        data.put("add","0");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(Tools.buildFormDataFromMap(data))
                .uri(URI.create(InternalSocket.PresenceServer))
                .setHeader("User-Agent", "MessengerApp") 
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        try {
			httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			System.out.println("InternalSocket: Error 1 dans notifyConnexionServlet");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("InternalSocket: Error 2 dans notifyConnexionServlet");
			e.printStackTrace();
		}
	}



	public ConcurrentHashMap<String,Address> getUserList() {
		// TODO Auto-generated method stub
		return connectedUserList;
	}

	public synchronized void  setUserList(ConcurrentHashMap<String,Address> ul) {
		// TODO Auto-generated method stub
		this.connectedUserList = ul;
	}


	public void sendMessage(Message msg, String Username) {
		// TODO Auto-generated method stub
		Address res = null;
		Boolean fin = false;
		synchronized (connectedUserList) {
			for (Map.Entry<String,Address> entry : connectedUserList.entrySet()) {
			 res = entry.getValue();
			 if(res.getUsername().equals(Username)) {
				 fin = true;
			 }
			}
		}
		System.out.println("InternalSocket: addr find " + res.getIP());
		String message = Tools.Msg_Code.Message.toString() + "\n" +  UsernameLogged.getUsername() + "\n" + Username + "\n" + msg.getTimestamp().toString() + "\n" + msg.getMsg();
		System.out.println("InternalSocket: msg : " + message);
		InetAddress addrRcv = res.getIP();
		try {
			Socket TCP_SEND_Socket = new Socket(addrRcv, Tools.Ports.TCP_RCV.getValue());
			PrintWriter out = new PrintWriter(TCP_SEND_Socket.getOutputStream(),true);
			out.println(message);
			TCP_SEND_Socket.close();
		} catch (IOException e) {
			System.out.println("InternalSocket: Error Send message création Socket");
			e.printStackTrace();
		}
		
	}

	
	public void startReceiverThread() {
		// TODO Auto-generated method stub
		System.out.println("InternalSocket: starting RECEIVER UDP AND TCP THREADS . . .");
		this.TCP_RCV_Thread = new TCPThreadReceiver(this.db, UsernameLogged.getUsername(),this.connectedUserList, this.UI);
		this.UDP_RCV_Thread = new UDPThreadReceiver(this.connectedUserList, this.db, this.UDP_SEND_Socket, UsernameLogged);
	}
	
	public  void startExecutor() {
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	    executorService.scheduleWithFixedDelay(new myTask(this.connectedUserList , this.ts), 0, 5, TimeUnit.SECONDS);
	}
	
	

	
	public ArrayList<Conversation> getHistorique() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void sendHistorique(ArrayList<Conversation> lh) {
		// TODO Auto-generated method stub

	}
	
	

	
	public void sendDisconnected(Account loggedAccount) {
		String message = Tools.Msg_Code.Disconnected.toString() + "\n" + loggedAccount.getUsername() + "\n" + loggedAccount.getPseudo() + "\n" + (new Timestamp(System.currentTimeMillis())).toString();;
		try {
			DatagramPacket outPacket = new DatagramPacket(message.getBytes(),message.length(),listAllBroadcastAddresses().get(0), Tools.Ports.UDP_RCV.getValue());
			this.UDP_SEND_Socket.send(outPacket);
			this.UDP_SEND_Socket.close();
		} catch ( IOException e) {
			System.out.println("InternalSocket: Error dans sendConnected");
			e.printStackTrace();
		}
		

}

class UDPThreadReceiver extends Thread {
	DatagramSocket receiver;
	DatagramSocket sender;
	ConcurrentHashMap<String,Address> connectedUserList;
	DBLocale db;
	boolean termine = false;
	Account userLogged;
	
	public UDPThreadReceiver(ConcurrentHashMap<String,Address>_connectedUserList, DBLocale _db, DatagramSocket _DS, Account _UN) {
		super();
		this.sender = _DS;
		this.userLogged = _UN;
		this.db = _db;
		this.connectedUserList = _connectedUserList;
		System.out.println("ThreadReceiver: starting . . .");
		try {
			receiver = new DatagramSocket(Tools.Ports.UDP_RCV.getValue());
		} catch (SocketException e) {
			System.out.println("UDPThreadReceiver: Error creatino socket");
			e.printStackTrace();
		}
		this.start();
		
	}
	public void setStop() {
		this.termine = true;
	}
	
	private void sendSpecificConnected(InetAddress addr, String Pseudo, String Username) {
		
		String message = Tools.Msg_Code.Con_Ack + "\n" + userLogged.getPseudo() + "\n" + userLogged.getUsername() + "\n" + (new Timestamp(System.currentTimeMillis())).toString();
		System.out.println("UDPThreadReceiver: sendSpecificConnected " + message + "\n\n" + addr.getAddress()[0] + + addr.getAddress()[1] + + addr.getAddress()[2] + addr.getAddress()[3]);
		try {
			DatagramPacket outPacket = new DatagramPacket(message.getBytes(),message.length(),addr, Tools.Ports.UDP_RCV.getValue());
			this.sender.send(outPacket);
		} catch ( IOException e) {
			System.out.println("UDPThreadReceiver: Error dans sendConnected");
			e.printStackTrace();
		}
	}
	@Override
	public void run(){
		System.out.println("UDPThreadReceiver: running . . .");
		try {
			receiver.setSoTimeout(1000);
		} catch (SocketException e1) {
			System.out.println("UDPThreadReceiver: Error setSO");
			e1.printStackTrace();
		}
		while(!termine) {
			byte[] buffer = new byte[InternalSocket.MAX_CHAR];
			DatagramPacket inPacket = new DatagramPacket(buffer,buffer.length);
			try {
				
				receiver.receive(inPacket);
				if (inPacket != null) {
					System.out.println("UUDPThreadReceiver: msg recu");
					InetAddress clientAddress = inPacket.getAddress();
					String message = new String (inPacket.getData(), 0, inPacket.getLength());
					BufferedReader reader = new BufferedReader(new StringReader(message));
					String line = reader.readLine();
					if (line.contains(Tools.Msg_Code.Connected.toString())) {
						System.out.println("UDPThreadReceiver: Connected received: " + message +" from " + clientAddress.getAddress()[0] + "." + clientAddress.getAddress()[1] + "." + clientAddress.getAddress()[2] + "." + clientAddress.getAddress()[3]);
						String Pseudo = reader.readLine();
						String Username = reader.readLine();
						if (!Username.equals(userLogged.getUsername())) {
							synchronized(this.connectedUserList) {
								
								this.connectedUserList.put(Username,new Address(clientAddress,Pseudo,Username ));
								
								this.sendSpecificConnected(clientAddress, Pseudo, Username);
							}
						}
						UI.refreshPageUserCo();
	
					}else if (line.contains(Tools.Msg_Code.Disconnected.toString())) {
						System.out.println("UDPThreadReceiver: Disconnected received: " + message);
						synchronized(this.connectedUserList) {
							String Username = reader.readLine();
							this.connectedUserList.remove(Username);
							
							
							
						}
						UI.refreshPageUserCo();
					}else if (line.contains(Tools.Msg_Code.New_Pseudo.toString())){
						System.out.println("UDPThreadReceiver: New_Pseudo received: " + message);
						
						synchronized(this.connectedUserList) {
							String new_pseudo = reader.readLine();
							String username = reader.readLine();
							String old_pseudo = reader.readLine();
							if (!username.equals(userLogged.getUsername())) {
								this.connectedUserList.put(username,new Address(InetAddress.getByAddress(clientAddress.getAddress()),new_pseudo, username));
								
								this.db.updatePseudo(new_pseudo, old_pseudo, username, UsernameLogged.getUsername());
							}
							
						}
						UI.refreshPageUserCo();
					}else if(line.contains(Tools.Msg_Code.Con_Ack.toString())) {
						System.out.println("UDPThreadReceiver: Connected_ACK received: " + message);
						synchronized(this.connectedUserList) {
							String Pseudo = reader.readLine();
							String Username = reader.readLine();
							this.connectedUserList.put(Username,new Address(clientAddress,Pseudo,Username ));
						}
						UI.refreshPageUserCo();
					}else {
						System.out.println("UDPThreadReceiver: Unknown message received: " + message);
					}
				}
				
				
				
			} catch(SocketTimeoutException a) {
				
			}catch (IOException e) {
				System.out.println("UDPThreadReceiver: Error thread");
				e.printStackTrace();
			}
		}
		this.receiver.close();
		System.out.println("UDPThreadReceiver: Closing . . .");
		
		
	}
}
	
class TCPThreadReceiver extends Thread {
		ServerSocket receiver;
		DBLocale db;
		ConcurrentHashMap<String,Address> coUsers;
		int n;
		String UsernameLogged;
		UserInterface UI;
		boolean termine = false;
		public TCPThreadReceiver(DBLocale db_, String _UsernameLogged, ConcurrentHashMap<String,Address> _coUsers, UserInterface _UI) {
			super();
			this.coUsers = _coUsers;
			this.UsernameLogged = _UsernameLogged;
			this.db = db_;
			this.UI = _UI;
			try {
				receiver = new ServerSocket(Tools.Ports.TCP_RCV.getValue());
			} catch (IOException e) {
				System.out.println("TCPThreadReceiver: Error constructor ServSocket");
				e.printStackTrace();
			}
			this.n = 0;
			System.out.println("TCPThreadreceiver: Creation ServSocket");
			this.start();
			
		}
		
		
		public void setStop() {
			this.termine = true;
		}
		
		public void run() {
			try {
				receiver.setSoTimeout(1000);
			} catch (SocketException e1) {
				System.out.println("TCPThreadReceiver: Error setTO");
				e1.printStackTrace();
			}
			while(!termine) {
				
					
					
					Socket clientSocket;
					try {
						clientSocket = receiver.accept();
						if (clientSocket != null) {
							n++;
							System.out.println("TCPThreadReceiver: Creation Socket fils en cours . . .");
							ThreadSocketFils temp = new ThreadSocketFils(clientSocket, n, db,UsernameLogged, this.coUsers, this.UI);
						}
					} catch(SocketTimeoutException a) {
						
					}catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				
			}
			try {
				this.receiver.close();
				System.out.println("TCPThreadReceiver: Closing . . .");
			} catch (IOException e) {
				System.out.println("TCPThreadReceiver: Error close");
				e.printStackTrace();
			}
			
		}
		
	}
	
	
class ThreadSocketFils extends Thread{
		Socket son;
		int n;
		DBLocale db;
		ConcurrentHashMap<String,Address> coUsers;
		String UsernameLogged;
		UserInterface UI;
		ThreadSocketFils(Socket chassot, int a, DBLocale db_, String _UsernameLogged, ConcurrentHashMap<String,Address> _coUsers, UserInterface _UI){
			this.UsernameLogged = _UsernameLogged;
			//this.db= db_;
			this.db = new DBLocale();
			this.coUsers = _coUsers;
			son = chassot;
			n =a;
			this.UI = _UI;
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
					if(temp.equals(Tools.Msg_Code.Message.toString())){	
						sender = in.readLine();
						rcv = in.readLine();
						ts = Timestamp.valueOf(in.readLine());
					}else {
		                message += temp+ "\n";
					}
					temp = in.readLine();
				}
				System.out.println("ThreadSocketFils" + n + ": msg received: " + sender + ";\n"+ rcv + ";\n"+ message);
				System.out.println("ThreadSocketFils" + n +":" + UsernameLogged +";");
				if( UsernameLogged.equals(rcv)) {
					Address temporary = this.db.getSpecificKnownUser(UsernameLogged, sender);
					if (temporary == null) {
						
						synchronized(this.coUsers) {
							if (this.coUsers.containsKey(sender)) {
								Address tempor = this.coUsers.get(sender);
								temporary = new Address(tempor.getIP(),tempor.getPseudo(),tempor.getUsername());
								this.db.setKnownUser(temporary, UsernameLogged);
							}
						}
						
					}
					Message ephemere = new Message(false,message,ts);
					this.db.setMessage(ephemere,sender,rcv);
					this.UI.recevoirmessageUI(ephemere,temporary);
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

class myTask extends TimerTask{
	ConcurrentHashMap<String, Address> userCo;
	Long ts;
	myTask(ConcurrentHashMap<String, Address> _userCo, Long _ts){
		this.userCo = _userCo;
		this.ts = _ts;
	}

	@Override
	public void run() {
		HttpClient httpClient = HttpClient.newBuilder()
	            .version(HttpClient.Version.HTTP_2)
	            .build();
		
		HttpRequest request = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(InternalSocket.PresenceServer +"?type="+Tools.Msg_Code.CoSpecificList+"&ts="+ this.ts))
				.setHeader("User-Agent", "MessengerApp")
				.build();
		 HttpResponse<String> response;
		try {
			Long tmp = System.currentTimeMillis();
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			this.ts = tmp;
			System.out.println(response.body());
			BufferedReader out = new BufferedReader(new StringReader(response.body()));
			String val = out.readLine();
			val = out.readLine();
			while(!val.equals("--rm--")){
				String pseudo = val;
				String username = out.readLine();
				String addr = out.readLine();
				this.userCo.put(username, new Address(InetAddress.getByName(addr.substring(1)), pseudo, username));
				System.out.println("Ajout de " + username + " Taille de la liste " + this.userCo.size());
				val = out.readLine();
			}
			val = out.readLine();
			while(val != null) {
				this.userCo.remove(val);
				System.out.println("Rm de " + val + " Taille de la liste " + this.userCo.size());
				val = out.readLine();
			}
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	       
		
	}
	
}
}
	




	

