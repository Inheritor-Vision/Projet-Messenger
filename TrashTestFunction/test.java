import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;

public class test {
    public static void main(String[] args) throws IOException {
        String a = "kindred \n";
        String b = "vi \n";

        BufferedReader in1 = new BufferedReader(new StringReader(a+b));
        String temp1 = in1.readLine();
        System.out.print(temp1);
        temp1 = in1.readLine();
        System.out.println(temp1);
        System.out.println(System.currentTimeMillis());
        Timestamp ts1 = new Timestamp(System.currentTimeMillis());
        System.out.println(ts1);
        int n = 1;
    try {
        System.out.println("ThreadSocketFils" + n + ": Succesfully created");
        BufferedReader in = new BufferedReader(new StringReader("message\nKindred\nTransVi\n2020-01-04 20:31:13\niusdbiusdfiou poksdfjsdf\n iudfhqpdhoqs qspodqsd\nsdfyhgsdfisd\nstop"));
        Boolean fin = false;
        String message = "";
        String sender= "";
        String rcv= "";
        Timestamp ts = null;
        while(!fin) {
            String temp = in.readLine();
            if(temp != null) {
                System.out.println("ThreadSocketFils" + n + ": msg received: " + temp);
                if(temp.equals("message")){	
                    sender = in.readLine();
                    rcv = in.readLine();
                    ts = Timestamp.valueOf(in.readLine());
				}else if (temp.equals("stop")) {
                    fin = true;
                }else {
                    message += temp + "\n";
                }
            }
            System.out.println ("Recu: \n" + sender + "\n" + rcv + "\n" + ts + "\n" + message);
        }
       
        System.out.println("ThreadSocketFils" + n +": Closing . . .");
        //son.close();
    }catch(IOException e) {
            System.out.println("ThreadSocketFils" + n + ": Error accept");
            e.printStackTrace();
    }
        
        
    }


/*public void run(){
    int n = 1;
    try {
        System.out.println("ThreadSocketFils" + n + ": Succesfully created");
        BufferedReader in = new BufferedReader(new StringReader("message\nKindred\nTransVi\niusdbiusdfiou poksdfjsdf\n iudfhqpdhoqs qspodqsd\nsdfyhgsdfisd\nstop"));
        Boolean fin = false;
        String message = "";
        String sender= "";
        String rcv= "";
        Timestamp ts = null;
        while(!fin) {
            String temp = in.readLine();
            if(temp != null) {
                System.out.println("ThreadSocketFils" + n + ": msg received: " + temp);
                if(temp.equals("message")){	
                    sender = in.readLine();
                    rcv = in.readLine();
                    ts = Timestamp.valueOf(in.readLine());
				}else if (temp.equals("stop")) {
                    fin = true;
                }else {
                    temp = in.readLine()+ "\n";
                    message += temp;
                }
            }
            System.out.println ("Recu: \n" + sender + "\n" + rcv + "\n" + ts + "\n" + message);
        }
       
        System.out.println("ThreadSocketFils" + n +": Closing . . .");
        //son.close();
    }catch(IOException e) {
            System.out.println("ThreadSocketFils" + n + ": Error accept");
            e.printStackTrace();
    }
}*/
}