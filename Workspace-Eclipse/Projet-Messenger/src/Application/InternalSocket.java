package Application;

import java.util.ArrayList;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.IOException;
import java.lang.Thread;

public class InternalSocket implements NetworkSocketInterface {
	ArrayList<Address> connectedUserList;
	protected static final int PORT_RCV = 6666;
	protected static final int PORT_SEND = 6667;
	protected static final int MAX_CHAR = 256;
	protected static final String  CONNECTED =  "Connected";
	protected static final String  DISCONNECTED =  "Disconnected";
	protected static final String  MESSAGE =  "Message";
	DatagramSocket Socket;
	
	public enum MessageType {
		CONNECTED,
		
	}
	
	public InternalSocket() throws SocketException{
		System.out.println("InternalSocket: starting . . .");
		Socket = new DatagramSocket(InternalSocket.PORT_SEND);
	}

	@Override
	public void sendConnected() {
		// TODO Auto-generated method stub
		
		DatagramPacket outPacket = new DatagramPacket()
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
	public void sendMessage(Message msg) {
		// TODO Auto-generated method stub

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

}

class ThreadReceiver extends Thread {
	DatagramSocket receiver;
	
	public ThreadReceiver() throws SocketException {
		System.out.println("ThreadReceiver: starting . . .");
		receiver = new DatagramSocket(InternalSocket.PORT_RCV);
		
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