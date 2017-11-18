import AuditParser.AuditParser;
import AuditParser.RequirementSection;

import java.io.IOException;
import java.util.ArrayList;
import AuditParser.Audit;
import Gui.ImageViewer;

public class Main {
    public static void main(String[] args) {
        try {
            AuditParser parser = new AuditParser();
            parser.setDocument("res/NickAudit.html");
            ArrayList<String> headers = parser.getHeaders();
            Audit myAudit = new Audit();
            myAudit.info = parser.getStudentInfo();
            System.out.println(myAudit.info);
            for (String header : headers) {
                System.out.println(header);
            }
            RequirementSection req = parser.getRequirementSection("COMPUTER SCIENCE FUNDAMENTAL COURSES");
            System.out.println(req.toString());

            ImageBuilder.setAudit(myAudit);
            ImageBuilder.drawStuInfo();
            ImageViewer checkImage = new ImageViewer(ImageBuilder.StudentInfo);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
