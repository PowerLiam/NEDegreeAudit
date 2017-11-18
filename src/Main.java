import AuditParser.AuditParser;
import AuditParser.RequirementSection;

import java.io.IOException;
import java.util.ArrayList;
import AuditParser.Audit;

public class Main {
    public static void main(String[] args) {
        try {
            AuditParser parser = new AuditParser();
            parser.setDocument("res/NickAudit.html");
            ArrayList<String> headers = parser.getHeaders();
            AuditParser.setDocument("res/NickAudit.html");
            ArrayList<String> headers = AuditParser.getHeaders();
            Audit.info = AuditParser.getStudentInfo();
            System.out.println(Audit.info);
            for (String header : headers) {
                System.out.println(header);
            }
            RequirementSection req = parser.getRequirementSection("COMPUTER SCIENCE FUNDAMENTAL COURSES");
            System.out.println(req.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
