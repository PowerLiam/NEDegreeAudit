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
            System.out.println("Headers");
            parser.getHeaders();
            //System.out.println("Headers end");
            Audit myAudit = new Audit();
            myAudit.info = parser.getStudentInfo();
            System.out.println(myAudit.info);
            /*for (String header : headers) {
                System.out.println(header);
            }*/
            //RequirementSection req = parser.getRequirementSection("COMPUTER SCIENCE FUNDAMENTAL COURSES");
            //System.out.println(req.toString());
            /*System.out.println("Registered Courses");
            System.out.println(parser.getRegisteredCourses().toString());
            System.out.println("General Electives");
            System.out.println(parser.getGeneralElectives().toString());*/
            //System.out.println(parser.getNumberInParens("Complete the following (10) courses:"));
            myAudit.myParser = parser;
            ImageBuilder.setAudit(myAudit);
            ImageBuilder.drawStuInfo();
            ImageBuilder.drawSummary();
            ImageBuilder.drawMajor();
            ImageBuilder.drawElectives();
            System.out.println(ImageBuilder.Summary.getHeight(null));
            ImageViewer checkImage = new ImageViewer(ImageBuilder.Elective);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
