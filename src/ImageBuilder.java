
import AuditParser.Audit;
import AuditParser.Course;
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
    public static Image Summary = new BufferedImage(2100, 2000, BufferedImage.TYPE_INT_BGR);
    public static Image Major = new BufferedImage(500, 1000, BufferedImage.TYPE_INT_BGR);
    public static Image Elective = new BufferedImage(500, 1000, BufferedImage.TYPE_INT_BGR);
    public static Image UniReqs = new BufferedImage(500, 1000, BufferedImage.TYPE_INT_BGR);
    public static Image StudentInfo = new BufferedImage(2100, 400, BufferedImage.TYPE_INT_BGR);
    public static Image PrintView = new BufferedImage(500, 1000, BufferedImage.TYPE_INT_BGR);
    public static Image currentTabState = new BufferedImage(2100, 100, BufferedImage.TYPE_INT_BGR);
    private static Graphics2D draw;
    private static Audit audit;


    public static void setAudit(Audit a) {
        audit = a;
    }

    public static void drawStuInfo() { //height 450
        Graphics2D cur = (Graphics2D) StudentInfo.getGraphics();
        cur.setColor(Color.LIGHT_GRAY);
        cur.fillRect(0, 0, 2100, 450);
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
        cur.drawString(" " + audit.info.getFocus_type() + ": " + audit.info.getFocus(), 0, 330);

        Image hours_bar = new BufferedImage(1200, 50, BufferedImage.TYPE_INT_BGR);
        Graphics2D hours = (Graphics2D) hours_bar.getGraphics();
        hours.setColor(Color.gray);
        hours.fillRect(0, 0, 1200, 50);
        //TODO: GET WIDTH MULTIPLIER FROM AUDIT
        hours.setColor(Color.blue);
        hours.fillRect(0, 0, 1200 / 14 * 9, 50);
        //                                 total  part

        cur.drawImage(hours_bar, 50, 350, null);
    }

    //0 for summary
    //1 for major
    //2 for electives
    //3 for University Requirements
    public static void drawTabs(int focus){
        Graphics2D cur = (Graphics2D) currentTabState.getGraphics();
        cur.setColor(Color.pink);
        cur.fillRect(0, 0, 2100, 100);

        cur.setColor(Color.RED);
        Font myFont = new Font("Serif", Font.BOLD, 85);
        cur.setFont(myFont);




        cur.setStroke(new BasicStroke(5));
        switch(focus){
            case 0:
                cur.setColor(Color.BLACK);
                cur.drawRect(5, 3, 2100 / 4 - 5, 94);
                cur.drawString(" Summary", 0, 85);
                cur.setColor(Color.RED);
                cur.drawRect(2100 / 4 * 1, 3, 2100 / 4, 94);
                cur.drawRect(2100 / 4 * 2, 3, 2100 / 4, 94);
                cur.drawRect(2100 / 4 * 3, 3, 2100 / 4, 94);
                cur.drawString(" Major", 2100 / 4 * 1, 85);
                cur.drawString(" Electives", 2100 / 4 * 2, 85);
                cur.drawString(" Uni-Reqs", 2100 / 4 * 3, 85);
                break;
            case 1:
                cur.setColor(Color.RED);
                cur.drawString(" Summary", 2100 / 4 * 1, 85);
                cur.drawRect(5, 3, 2100 / 4 * 1, 94);
                cur.setColor(Color.BLACK);
                cur.drawRect(2100 / 4 * 1, 3, 2100 / 4 - 5, 94);
                cur.drawString(" Major", 2100 / 4 * 1, 85);
                cur.setColor(Color.RED);
                cur.drawRect(2100 / 4 * 2, 3, 2100 / 4, 94);
                cur.drawRect(2100 / 4 * 3, 3, 2100 / 4, 94);
                cur.drawString(" Electives", 2100 / 4 * 2, 85);
                cur.drawString(" Uni-Reqs", 2100 / 4 * 3, 85);

                break;
            case 2:
                cur.setColor(Color.RED);
                cur.drawString(" Summary", 2100 / 4 * 1, 85);
                cur.drawString(" Major", 2100 / 4 * 1, 85);
                cur.drawRect(5, 3, 2100 / 4 * 1 - 5, 94);
                cur.drawRect(2100 / 4, 3, 2100 / 4, 94);
                cur.setColor(Color.BLACK);
                cur.drawRect(2100 / 4 * 2, 3, 2100 / 4, 94);
                cur.drawString(" Electives", 2100 / 4 * 2, 85);
                cur.setColor(Color.RED);
                cur.drawRect(2100 / 4 * 3, 3, 2100 / 4, 94);
                cur.drawString(" Uni-Reqs", 2100 / 4 * 3, 85);
                break;
            case 3:
                cur.setColor(Color.RED);
                cur.drawString(" Summary", 2100 / 4 * 1, 85);
                cur.drawString(" Major", 2100 / 4 * 1, 85);
                cur.drawString(" Electives", 2100 / 4 * 2, 85);
                cur.drawRect(5, 3, 2100 / 4 * 1 - 5, 94);
                cur.drawRect(2100 / 4, 3, 2100 / 4, 94);
                cur.drawRect(2100 / 4 * 2, 3, 2100 / 4, 94);
                cur.setColor(Color.BLACK);
                cur.drawString(" Uni-Reqs", 2100 / 4 * 3, 85);
                cur.drawRect(2100 / 4 * 3, 3, 2100 / 4, 94);
                break;
        }
    } //100 height

    public static void drawSummary() {
        Graphics2D cur = (Graphics2D) Summary.getGraphics();
        cur.setColor(Color.LIGHT_GRAY);
        cur.fillRect(0, 0, 2100, 2000);
        drawTabs(0);
        drawStuInfo();
        cur.drawImage(StudentInfo, 0, 0, null);
        cur.drawImage(currentTabState, 0, 450, null);
        cur.setColor(Color.RED);

        Font myFont = new Font("Serif", Font.BOLD, 85);
        cur.setFont(myFont);
        cur.drawString(" Progress:", 0, 635);

        Image major_bar = new BufferedImage(1200, 50, BufferedImage.TYPE_INT_BGR);
        Graphics2D major = (Graphics2D) major_bar.getGraphics();
        major.setColor(Color.gray);
        major.fillRect(0, 0, 1200, 50);
        //TODO: GET WIDTH MULTIPLIER FROM AUDIT
        major.setColor(Color.blue);
        major.fillRect(0, 0, 1200 / 14 * 9, 50);
        //                                 total  part

        Image required_electives_bar = new BufferedImage(1200, 50, BufferedImage.TYPE_INT_BGR);
        Graphics2D electives = (Graphics2D) required_electives_bar.getGraphics();
        electives.setColor(Color.gray);
        electives.fillRect(0, 0, 1200, 50);
        //TODO: GET WIDTH MULTIPLIER FROM AUDIT
        electives.setColor(Color.blue);
        electives.fillRect(0, 0, 1200 / 14 * 9, 50);
        //                                 total  part

        Image uni_req_bar = new BufferedImage(1200, 50, BufferedImage.TYPE_INT_BGR);
        Graphics2D uni = (Graphics2D) uni_req_bar.getGraphics();
        uni.setColor(Color.gray);
        uni.fillRect(0, 0, 1200, 50);
        //TODO: GET WIDTH MULTIPLIER FROM AUDIT
        uni.setColor(Color.blue);
        uni.fillRect(0, 0, 1200 / 14 * 9, 50);
        //                                 total  part

        myFont = new Font("Serif", Font.BOLD, 50);
        cur.setFont(myFont);
        cur.drawString(" Major Requirements:", 0, 700);
        cur.drawImage(major_bar, 100, 750,null);
        cur.drawString( "(" + "9" + "/" + "14" + ")", 1400, 775);
        //                      part        total
        //TODO: GET PART AND TOTAL FROM AUDIT

        cur.drawString(" Required General Electives:", 0, 900);
        cur.drawImage(required_electives_bar, 100, 950,null);
        cur.drawString( "(" + "9" + "/" + "14" + ")", 1400, 975);
        //                      part        total
        //TODO: GET PART AND TOTAL FROM AUDIT

        cur.drawString(" University Requirements:", 0, 1100);
        cur.drawImage(uni_req_bar, 100, 1150,null);
        cur.drawString( "(" + "9" + "/" + "14" + ")", 1400, 1175);
        //                      part        total
        //TODO: GET PART AND TOTAL FROM AUDIT


        //registered courses: starting height 1150

    }
    // All COURSE OBJECTS HAVE BEEN STARTED
    // Progress
    // IP, " "
    // IP: In Progress
    // " ": Finished
    //
    //
    //
    // Registration
    // RG, NR, NT, T
    // RG: Registered
    // NR: Not reported
    // NT: Unacceptable T grade
    // T: Transferred

    public Image renderCourse(Course c){ //height = 100
        Image course = new BufferedImage(700, 100, BufferedImage.TYPE_INT_BGR);
        Graphics2D g = (Graphics2D)course.getGraphics();
        g.setFont(new Font("Serif", Font.BOLD, 90));
        if(c.getProgress().equals("IP")) g.setColor(Color.RED);
        else g.setColor(Color.GREEN);
        g.drawString(c.getDepartment() + "-" + c.getCourseno() + ": " + c.getName() + "        " + c.getCredits() + "    " + c.getRegistration(), 100, 95);
        return course;
    }

    /*public Image renderHeader(Header h){

    }*/

}
