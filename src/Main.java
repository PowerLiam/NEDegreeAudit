import AuditParser.AuditParser;

import java.io.IOException;
import java.util.ArrayList;
import AuditParser.Audit;
import Gui.ImageViewer;

public class Main {
    public static void main(String[] args) {
        try {
            Audit myAudit = new Audit();
            AuditParser.setDocument("res/NickAudit.html");
            ArrayList<String> headers = AuditParser.getHeaders();
            myAudit.info = AuditParser.getStudentInfo();
            System.out.println(myAudit.info);
            for (String header : headers) {
                System.out.println(header);
            }
            
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
