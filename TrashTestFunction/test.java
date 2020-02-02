
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.io.Reader;

public class test {
    public static void main(String[] args) {
        Map<String, String> test = new HashMap<String, String>();
        test.put("LOL", "LOL");
        test.put("aze", "qsdcv");
        test.put("xD", "gyhuk");
        test.put("=D", "LhgfhOL");
        test.put("pog", "zer");
        test.put("LOL", "nul/20");
        test.remove("al");
        Set<Entry<String, String>> setHm = test.entrySet();
        Iterator<Entry<String, String>> it = setHm.iterator();
        while (it.hasNext()) {
            Entry<String, String> e = it.next();
            System.out.println(e.getKey() + " : " + e.getValue());
        }
        
        System.out.println(System.currentTimeMillis());
        
    }


}