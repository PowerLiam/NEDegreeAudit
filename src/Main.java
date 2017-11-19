import AuditParser.AuditParser;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import AuditParser.Audit;
import Gui.ImageViewer;

import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        try {
            AuditParser parser = new AuditParser();
            //parser.setDocument("res/PaulAudit.htm");
            System.out.println("Loading from " + args[0]);
            System.out.println("Saving to " + args[1]);
            parser.setDocument(args[0]);
            Audit myAudit = new Audit();
            myAudit.info = parser.getStudentInfo();
            System.out.println(myAudit.info);

            myAudit.myParser = parser;
            ImageBuilder.setAudit(myAudit);
            ImageBuilder.drawStuInfo();
            ImageBuilder.drawSummary();
            ImageBuilder.drawMajor();
            ImageBuilder.drawUniReqs();
            ImageBuilder.drawElectives();
            System.out.println(ImageBuilder.Summary.getHeight(null));
            Image image = ImageBuilder.getPrintedImage();

            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            bufferedImage.getGraphics().drawImage(image, 0, 0, null);
            ImageIO.write(bufferedImage, "jpg", new File(args[1]));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
