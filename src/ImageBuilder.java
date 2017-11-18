
import AuditParser.Audit;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Created by Chris on 11/18/2017.
 */
public class ImageBuilder {
    public static Image Summary = new BufferedImage(500, 1000, BufferedImage.TYPE_INT_BGR);
    public static Image Major = new BufferedImage(500, 1000, BufferedImage.TYPE_INT_BGR);
    public static Image Elective = new BufferedImage(500, 1000, BufferedImage.TYPE_INT_BGR);
    public static Image UniReqs = new BufferedImage(500, 1000, BufferedImage.TYPE_INT_BGR);
    public static Image StudentInfo = new BufferedImage(2100, 400, BufferedImage.TYPE_INT_BGR);
    public static Image PrintView = new BufferedImage(500, 1000, BufferedImage.TYPE_INT_BGR);
    private static Graphics2D draw;
    private static Audit audit;


    public static void setAudit(Audit a){
        audit = a;
    }

    public static void drawStuInfo(){
        Graphics2D cur = (Graphics2D) StudentInfo.getGraphics();
        cur.setColor(Color.LIGHT_GRAY);
        cur.fillRect(0, 0, 2100, 400);
        cur.setColor(Color.RED);
        Font myFont = new Font("Serif", Font.BOLD, 90);
        cur.setFont(myFont);
        cur.drawString(" Student Information", 0, 90);
        myFont = new Font("Serif", Font.BOLD, 60);
        cur.setColor(Color.BLACK);
        cur.setFont(myFont);
        cur.drawString(" NAME: " + audit.info.getFirst_name() + " " + audit.info.getLast_name() + "    NUID: " + audit.info.getNUID(), 0, 150);
        cur.drawString(" Graduation Date: " + audit.info.getGrad_date() + "                Program Code: " + audit.info.getProgram_code(), 0, 210);
        cur.drawString(" Degree: " + audit.info.getDegree(), 0, 270);
        cur.drawString(" " + audit.info.getFocus_type() +": " + audit.info.getFocus(), 0, 330);
    }

    public static void drawSummary() {
        Graphics2D cur = (Graphics2D) Summary.getGraphics();
        cur.setColor(Color.LIGHT_GRAY);
        cur.fillRect(0, 0, 2100, 2000);
        cur.setColor(Color.RED);
        Font myFont = new Font("Serif", Font.BOLD,  110);
        cur.setFont(myFont);
        cur.drawString(" Summary", 0, 110);

    }

}
