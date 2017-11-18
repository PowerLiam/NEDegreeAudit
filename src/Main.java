import AuditParser.AuditParser;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            AuditParser.setDocument("res/NickAudit.html");
            ArrayList<String> headers = AuditParser.getHeaders();
            for (String header : headers) {
                System.out.println(header);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
