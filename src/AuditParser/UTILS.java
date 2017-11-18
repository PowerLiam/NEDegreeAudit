package AuditParser;

import java.util.ArrayList;

/**
 * Created by Chris on 11/18/2017.
 */
public class UTILS{
    public static String[] remove(String[] arr, String regex){
        ArrayList<String> ret = new ArrayList<>();
        for(String s : arr) {
            if (s.equals(regex)) continue;
            ret.add(s);
        }
        String[] realret = new String[ret.size()];
        for(int i = 0; i < realret.length; i++){
            realret[i] = ret.get(i);
        }
        return realret;
    }

}
