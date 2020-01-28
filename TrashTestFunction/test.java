
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class test {
    public static void main(String[] args) {
        Map<String,String> test = new HashMap<String,String>();
        test.put("LOL","LOL");
        test.put("aze","qsdcv");
        test.put("xD","gyhuk");
        test.put("=D","LhgfhOL");
        test.put("pog","zer");
        test.put("LOL","nul/20");
        Set<Entry<String, String>> setHm = test.entrySet();
        Iterator<Entry<String, String>> it = setHm.iterator();
        while(it.hasNext()){
           Entry<String, String> e = it.next();
           System.out.println(e.getKey() + " : " + e.getValue());
        }
    }


}