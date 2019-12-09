import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class serverTCP {
    public static void main (String[] args){ 
        try{
            ServerSocket servSocket = new ServerSocket(6668);
            System.out.println(" Waiting for connection");
            Socket link = servSocket.accept();
            boolean fin =false;
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            while(!fin){
                
                String temp = in.readLine();
                if(temp != null){
                    System.out.println(temp);
                        
                        
                    
                    
                }
                
            }

            
            link.close();
            servSocket.close();

        }catch (IOException e){
            System.out.print("Error instanciation Socket Client");
        }

        

        


    }

}