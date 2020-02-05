package Common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;

public class Tools {
	
	public static enum Msg_Code{
		Connected,
		Disconnected,
		Message,
		New_Pseudo,
		Con_Ack,
		CoList,
		CoSpecificList
	}
	public static enum Ports{
		UDP_RCV(6666),UDP_SEND(6667),TCP_RCV(6668),TCP_SEND(6669),HTTP_RCV(6670);
		int value;
		private Ports(int val){
			this.value = val;
		}
		public int getValue() {
			return this.value;
		}
	}
	
	
	public static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
        	
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }
	
	public static byte[] getPcIP() {
		Enumeration<NetworkInterface> e;
		byte[] res = null;
		boolean fin = false;
		try {
			e = NetworkInterface.getNetworkInterfaces();
			while(e.hasMoreElements())
			{
			    NetworkInterface n = (NetworkInterface) e.nextElement();
			    Enumeration<InetAddress> ee = n.getInetAddresses();
			    while (ee.hasMoreElements() && !fin)
			    {
			        InetAddress i = (InetAddress) ee.nextElement();
			        //if(i.getAddress()[0] == 10) {
			        if(i.getAddress().length == 4 && !(i.getAddress()[0]==127 && i.getAddress()[1]==0 && i.getAddress()[2]==0 && i.getAddress()[3]==1)) {
			        	res = i.getAddress();
			        	fin = true;
			        }
			        
			    }
			}
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return res;
	}
}
