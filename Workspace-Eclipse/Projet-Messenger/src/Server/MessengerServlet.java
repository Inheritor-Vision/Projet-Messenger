package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import Common.Address;
import Common.Tools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MessengerServlet extends HttpServlet{
	 static ArrayList<Address> coUsers = new ArrayList<Address>();
	 static boolean init = false;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(MessengerServlet.init == false) {
			MessengerServlet.init=true;
			coUsers.add(new Address("Alice Pseudo", "Alice Username"));
			coUsers.add(new Address("Bob Pseudo", "Bob Username"));
			coUsers.add(new Address("Eve Pseudo", "Eve Username"));
		}
		
		
		
		resp.setContentType("text/html");
		resp.setCharacterEncoding( "UTF-8" );
		PrintWriter out = resp.getWriter();
		Iterator<Address> it = coUsers.iterator();
		Address temp;
		
		out.println(Tools.Msg_Code.CoList.toString());
		while (it.hasNext()) {
			temp = it.next();
			out.println(temp.getPseudo());
			out.println(temp.getUsername());
			out.println(temp.getIP());
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Enumeration<String> parameterNames = req.getParameterNames();
		byte[] addr = new byte[4];
		String username = "";
		String pseudo = "";
		while (parameterNames.hasMoreElements()) {
		    String key = parameterNames.nextElement();
		    if(key.equals("username")) {
		    	username = req.getParameter(key);
		    }else if(key.equals("pseudo")) {
			    pseudo = req.getParameter(key);
			}else if(key.equals("addr1")) {
				addr[0] = (byte)Integer.parseInt(req.getParameter(key));
			}else if(key.equals("addr2")) {
				addr[1] = (byte)Integer.parseInt(req.getParameter(key));
			}else if(key.equals("addr3")) {
				addr[2] = (byte)Integer.parseInt(req.getParameter(key));
			}else {
				addr[3] = (byte)Integer.parseInt(req.getParameter(key));
			}
		}  
		coUsers.add(new Address(InetAddress.getByAddress(addr), pseudo, username));
	}
}

