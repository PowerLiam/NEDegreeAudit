import AuditParser.AuditParser;
import AuditParser.RequirementSection;

import java.io.IOException;
import java.util.ArrayList;
import AuditParser.Audit;
import Gui.ImageViewer;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

public class Main {
    public static void main(String[] args) {
        try {
            AuditParser parser = new AuditParser();
            parser.setDocument("res/PaulAudit.htm");
            System.out.println("Headers");
            parser.getUniversityRequirements();
            //System.out.println("Headers end");
            Audit myAudit = new Audit();
            myAudit.info = parser.getStudentInfo();
            System.out.println(myAudit.info);

            int totalhours[] = parser.getTotalHours();
            System.out.println("Total Hours: " + totalhours[0] + " / " + totalhours[1]);
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
            ImageBuilder.drawUniReqs();
            ImageBuilder.drawElectives();
            System.out.println(ImageBuilder.Summary.getHeight(null));
            ImageViewer checkImage = new ImageViewer(ImageBuilder.getPrintedImage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
