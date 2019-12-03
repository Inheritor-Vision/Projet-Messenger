package Application;

import java.util.ArrayList;
import java.util.Iterator;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.io.IOException;
import java.lang.Thread;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InternalSocket implements NetworkSocketInterface {
	ArrayList<Address> connectedUserList; // Need to be synchronized
	protected static final int UDP_PORT_RCV = 6666;
	protected static final int UDP_PORT_SEND = 6667;
	protected static final int MAX_CHAR = 256;
	protected static final String  CONNECTED =  "Connected";
	protected static final String  DISCONNECTED =  "Disconnected";
	protected static final String  MESSAGE =  "Message";
	protected final String UsernameLogged;
	DatagramSocket Socket;
	
	
	
	public InternalSocket(String UsernameLoggedAccount_) throws SocketException{
		this.UsernameLogged = UsernameLoggedAccount_;
		System.out.println("InternalSocket: starting . . .");
		this.Socket = new DatagramSocket(InternalSocket.UDP_PORT_SEND);
		
		
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
			this.Socket.setBroadcast(true);
			
		} catch (SocketException e) {
			System.out.println("InternalSocket: Error sendConnected");
			e.printStackTrace();
		}
		String message = InternalSocket.CONNECTED.toString() + "\n" + loggedAccount.getUsername() + "\n" + loggedAccount.getPseudo() + "\n" + (new Timestamp(System.currentTimeMillis())).toString();;
		try {
			DatagramPacket outPacket = new DatagramPacket(message.getBytes(),message.length(),listAllBroadcastAddresses().get(0), InternalSocket.UDP_PORT_RCV);
			this.Socket.send(outPacket);
		} catch ( IOException e) {
			System.out.println("InternalSocket: Error dans sendConnected");
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
		String message = InternalSocket.MESSAGE.toString() + "\n" +  UsernameLogged + "\n" + Username + "\n" + msg.getTimestamp().toString() + "\n" + msg.getMsg();;
		DatagramPacket outPacket = new DatagramPacket(message.getBytes(),message.length(),res.getIP(), InternalSocket.UDP_PORT_RCV);
		try {
			Socket.send(outPacket);
		} catch( IOException e) {
			System.out.println("Error Sending in sendMessage");
		}
		
	}

	@Override
	public void startReceiverThread(Controller cont) {
		// TODO Auto-generated method stub

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
			this.Socket.send(outPacket);
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

class ThreadReceiver extends Thread {
	DatagramSocket receiver;
	
	public ThreadReceiver() throws SocketException {
		System.out.println("ThreadReceiver: starting . . .");
		receiver = new DatagramSocket(InternalSocket.UDP_PORT_RCV);
		
	}
	
	@Override
	public void run(){
		System.out.println("ThreadReceiver: running . . .");
		while(true) {
			byte[] buffer = new byte[InternalSocket.MAX_CHAR];
			DatagramPacket inPacket = new DatagramPacket(buffer,buffer.length);
			try {
				receiver.receive(inPacket);
				System.out.println("ThreadReceiver: msg received");
				InetAddress clientAddress = inPacket.getAddress();
				String message = new String (inPacket.getData(), 0, inPacket.getLength());
				
			}catch (IOException e) {
				System.out.println("Error thread");
			}
		}
		
		
	}
	
	
	
	
	
	
	
}
