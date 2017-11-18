import AuditParser.AuditParser;

import java.io.IOException;
import java.util.ArrayList;
import AuditParser.Audit;

public class Main {
    public static void main(String[] args) {
        try {
            AuditParser.setDocument("res/NickAudit.html");
            ArrayList<String> headers = AuditParser.getHeaders();
            Audit.info = AuditParser.getStudentInfo();
            System.out.println(Audit.info);
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
