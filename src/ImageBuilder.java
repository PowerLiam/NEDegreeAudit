
import AuditParser.Audit;
import AuditParser.AuditParser;
import AuditParser.Course;
import AuditParser.Header;
import AuditParser.RequirementSection;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Chris on 11/18/2017.
 */
public class ImageBuilder {
    public static Image Summary = new BufferedImage(2300, 10000, BufferedImage.TYPE_INT_BGR);
    public static Image Major = new BufferedImage(2300, 10000, BufferedImage.TYPE_INT_BGR);
    public static Image Elective = new BufferedImage(2300, 10000, BufferedImage.TYPE_INT_BGR);
    public static Image UniReqs = new BufferedImage(2300, 10000, BufferedImage.TYPE_INT_BGR);
    public static Image StudentInfo = new BufferedImage(2300, 400, BufferedImage.TYPE_INT_BGR);
    public static Image PrintView = new BufferedImage(500, 1000, BufferedImage.TYPE_INT_BGR);
    public static Image currentTabState = new BufferedImage(2300, 100, BufferedImage.TYPE_INT_BGR);
    private static Graphics2D draw;
    private static Audit audit;
    private static Dimension ScreenHeight = Toolkit.getDefaultToolkit().getScreenSize();
    private static double height = ScreenHeight.getHeight();
    private static double width = ScreenHeight.getWidth();


    //temporary
    private static Dimension MyScreenHeight = Toolkit.getDefaultToolkit().getScreenSize();
    private static double myheight = MyScreenHeight.getHeight();
    private static double mywidth = MyScreenHeight.getWidth();

    public static void setAudit(Audit a) {
        audit = a;
    }

    public static void drawStuInfo() { //height 450
        Graphics2D cur = (Graphics2D) StudentInfo.getGraphics();
        cur.setColor(Color.LIGHT_GRAY);
        cur.fillRect(0, 0, 2300, 450);
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

        Image hours_bar = new BufferedImage(1600, 50, BufferedImage.TYPE_INT_BGR);
        Graphics2D hours = (Graphics2D) hours_bar.getGraphics();
        hours.setColor(Color.gray);
        hours.fillRect(0, 0, 1200, 50);
        hours.setColor(Color.lightGray);
        hours.fillRect(1200, 0, 400, 50);
        //TODO: GET WIDTH MULTIPLIER FROM AUDIT
        int[] mainpair = audit.myParser.getTotalHours();
        hours.setColor(Color.blue);
        hours.setStroke(new BasicStroke(5));
        hours.drawRect(0, 0, 1200, 50);
        hours.fillRect(0, 0, 1200 / mainpair[1] * mainpair[0], 50);

        hours.setFont(new Font("Serif", Font.BOLD, 50));
        hours.setColor(Color.red);
        hours.drawString( "(" + mainpair[0] + "/" + mainpair[1] + ")", 1250, 40);
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
        cur.fillRect(0, 0, 2300, 100);

        cur.setColor(Color.RED);
        Font myFont = new Font("Serif", Font.BOLD, 85);
        cur.setFont(myFont);




        cur.setStroke(new BasicStroke(5));
        switch(focus){
            case 0:
                cur.setColor(Color.BLACK);
                cur.drawRect(5, 3, 2300 / 4 - 5, 94);
                cur.drawString(" Summary", 0, 85);
                cur.setColor(Color.RED);
                cur.drawRect(2300 / 4 * 1, 3, 2300 / 4, 94);
                cur.drawRect(2300 / 4 * 2, 3, 2300 / 4, 94);
                cur.drawRect(2300 / 4 * 3, 3, 2300 / 4, 94);
                cur.drawString(" Major", 2300 / 4 * 1, 85);
                cur.drawString(" Electives", 2300 / 4 * 2, 85);
                cur.drawString(" Uni-Reqs", 2300 / 4 * 3, 85);
                break;
            case 1:
                cur.setColor(Color.RED);
                cur.drawString(" Summary", 0, 85);
                cur.drawRect(5, 3, 2300 / 4 * 1, 94);
                cur.setColor(Color.BLACK);
                cur.drawRect(2300 / 4 * 1, 3, 2300 / 4 - 5, 94);
                cur.drawString(" Major", 2300 / 4 * 1, 85);
                cur.setColor(Color.RED);
                cur.drawRect(2300 / 4 * 2, 3, 2300 / 4, 94);
                cur.drawRect(2300 / 4 * 3, 3, 2300 / 4, 94);
                cur.drawString(" Electives", 2300 / 4 * 2, 85);
                cur.drawString(" Uni-Reqs", 2300 / 4 * 3, 85);

                break;
            case 2:
                cur.setColor(Color.RED);
                cur.drawString(" Summary", 0, 85);
                cur.drawString(" Major", 2300 / 4 * 1, 85);
                cur.drawRect(5, 3, 2300 / 4 * 1, 94);
                cur.drawRect(2300 / 4, 3, 2300 / 4, 94);
                cur.setColor(Color.BLACK);
                cur.drawRect(2300 / 4 * 2, 3, 2300 / 4, 94);
                cur.drawString(" Electives", 2300 / 4 * 2, 85);
                cur.setColor(Color.RED);
                cur.drawRect(2300 / 4 * 3, 3, 2300 / 4, 94);
                cur.drawString(" Uni-Reqs", 2300 / 4 * 3, 85);
                break;
            case 3:
                cur.setColor(Color.RED);
                cur.drawString(" Summary", 0, 85);
                cur.drawString(" Major", 2300 / 4 * 1, 85);
                cur.drawString(" Electives", 2300 / 4 * 2, 85);
                cur.drawRect(5, 3, 2300 / 4 * 1 - 5, 94);
                cur.drawRect(2300 / 4, 3, 2300 / 4, 94);
                cur.drawRect(2300 / 4 * 2, 3, 2300 / 4, 94);
                cur.setColor(Color.BLACK);
                cur.drawString(" Uni-Reqs", 2300 / 4 * 3, 85);
                cur.drawRect(2300 / 4 * 3, 3, 2300 / 4, 94);
                break;
        }
    } //100 height

    public static void drawSummary() throws Exception {
        ArrayList<RequirementSection> register = new ArrayList<RequirementSection>();
        register.add(audit.myParser.getRegisteredCourses());
        Header registered = new Header("Registered Courses", "IP", register);
        Image rendered = renderHeader(registered);
        //get total height
        int totalheight = 1250 + rendered.getHeight(null);
        Summary = new BufferedImage(2300, totalheight, BufferedImage.TYPE_INT_BGR);


        Graphics2D cur = (Graphics2D) Summary.getGraphics();
        cur.setColor(Color.LIGHT_GRAY);
        cur.fillRect(0, 0, 2300, 2000);
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
        int[] majorpair= new int[2];
        for(Header h : audit.myParser.getHeaders()){
            majorpair[0] += h.getPair()[0];
            majorpair[1] += h.getPair()[1];
        }
        major.setColor(Color.blue);
        major.setStroke(new BasicStroke(5));
        major.drawRect(0, 0, 1200, 50);
        major.fillRect(0, 0, 1200 / majorpair[1] * majorpair[0], 50);
        //                                 total  part
        Image required_electives_bar = new BufferedImage(1200, 50, BufferedImage.TYPE_INT_BGR);
        Graphics2D electives = (Graphics2D) required_electives_bar.getGraphics();
        electives.setColor(Color.gray);
        electives.fillRect(0, 0, 1200, 50);
        ArrayList<RequirementSection> elect = new ArrayList<RequirementSection>();
        elect.add(audit.myParser.getGeneralElectives());
        elect.add(audit.myParser.getRequiredGeneralElectives());
        Header electiveheader = new Header("General Electives", "IP", elect);
        int[] electivepair = electiveheader.getPair();
        electives.setColor(Color.blue);
        electives.setStroke(new BasicStroke(5));
        electives.drawRect(0, 0, 1200, 50);
        electives.fillRect(0, 0, 1200 / electivepair[1] * electivepair[0], 50);
        //                                 total  part

        Image uni_req_bar = new BufferedImage(1200, 50, BufferedImage.TYPE_INT_BGR);
        Graphics2D uni = (Graphics2D) uni_req_bar.getGraphics();
        Header unih = audit.myParser.getUniversityRequirements();
        int[] unipair = unih.getPair();
        uni.setColor(Color.gray);
        uni.fillRect(0, 0, 1200, 50);
        uni.setColor(Color.blue);
        uni.setStroke(new BasicStroke(5));
        uni.drawRect(0, 0, 1200, 50);
        //TODO FIX
        uni.fillRect(0, 0, 1200 / unipair[1] * unipair[0], 50);
        //                                 total  part

        myFont = new Font("Serif", Font.BOLD, 50);
        cur.setFont(myFont);
        cur.drawString(" Major Requirements:", 0, 700);
        cur.drawImage(major_bar, 100, 750,null);
        cur.drawString( "(" + majorpair[0] + "/" + majorpair[1] + ")", 1400, 775);
        //                      part        total

        cur.drawString(" Required General Electives:", 0, 900);
        cur.drawImage(required_electives_bar, 100, 950,null);
        cur.drawString( "(" + electivepair[0] + "/" + electivepair[1] + ")", 1400, 975);
        //                      part        total

        cur.drawString(" University Requirements:", 0, 1100);
        cur.drawImage(uni_req_bar, 100, 1150,null);
        cur.drawString( "(" + unipair[0] + "/" + unipair[1] + ")", 1400, 1175);
        //                      part        total
        //TODO: GET PART AND TOTAL FROM AUDIT


        //registered courses: starting height 1150

        cur.drawImage(rendered, 0, 1250, null);
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

    public static Image renderCourse(Course c){ //height = 100
        Image course = new BufferedImage(2000, 100, BufferedImage.TYPE_INT_BGR);
        Graphics2D g = (Graphics2D)course.getGraphics();
        g.setFont(new Font("Serif", Font.BOLD, 75));
        g.setColor(Color.gray);
        g.fillRect(0, 0, 2000, 100);
        g.setStroke(new BasicStroke( 10));
        if(c.getProgress().equals("IP")) g.setColor(new Color(209, 128, 19));
        else g.setColor(new Color(62, 183, 72));
        g.drawRect(0, 0, 2000, 100);
        g.drawString(c.getDepartment() + "-" + c.getCourseno() + ": " + c.getName() + "        " + c.getCredits() + "    " + c.getRegistration(), 100, 75);
        return course;
    }

    public static Image renderHeader(Header h){
        int sum = 0;
        for(RequirementSection r : h.getRequirements()){
            sum += 50; //title
            sum += 100 * r.getRegisteredCourses().size();
            sum += 50 * r.getCourseOptions().size();
            sum += 80; // spacing btw next section
        }
        sum += 90; //title of header
        sum += 80; //title of header

        Image header = new BufferedImage(2300, sum, BufferedImage.TYPE_INT_BGR);
        Graphics2D g = (Graphics2D) header.getGraphics();
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, 2300, sum);

        System.out.println("STATUS: " + h.getStatus());
        if(h.getStatus().equals("")) g.setColor(new Color(200, 129, 39));
        else if(h.getStatus().equals("IP")) g.setColor(new Color(200, 129, 39));
        else if(h.getStatus().equals("NO")) g.setColor(new Color(160, 0, 0));
        else if(h.getStatus().equals("OK")) g.setColor(new Color(62, 183, 72));
        else g.setColor(new Color(200, 41, 193));



        g.setFont(new Font("Serif", Font.BOLD, 90));
        g.drawString(h.getName(), 10, 90);
        g.setFont(new Font("Serif", Font.BOLD, 50));
        int offset = 180;
        for(RequirementSection r : h.getRequirements()){
            if(r.getStatus().equals("+")) g.setColor(new Color(8, 183, 0));
            else if(r.getStatus().equals("IP-")) g.setColor(new Color(200, 129, 39));
            else if(r.getStatus().equals("IP")) g.setColor(new Color(200, 129, 39));
            else if(r.getStatus().equals("IP+")) g.setColor(new Color(78, 106, 11));
            else if(r.getStatus().equals("-")) g.setColor(new Color(177, 16, 32));
            else g.setColor(new Color(8, 183, 0));

            if(r.getStatus().equals("")) g.drawString(r.getTitle(), 20, offset + 50);
            else g.drawString(r.getTitle() + "  Status: " + r.getStatus(), 30, offset + 50);

            offset += 60;
            for(Course c : r.getRegisteredCourses()){
                if(c.getDepartment().equals("")) c.setDepartment("     ");
                g.drawImage(renderCourse(c), 30, offset, null);
                offset += 100;
            }
            g.setFont(new Font("Serif", Font.BOLD, 50));
            for(String c : r.getCourseOptions()){
                g.drawString(c, 30, offset + 50);
                System.out.println("STRINGS: "+ c);
                offset += 50;
            }
            offset += 80;
        }
        return header;
    }

    public static void drawMajor() throws Exception {
        int totalheight = 550;
        for (Header h : audit.myParser.getHeaders()){
            totalheight += 100;
            Image temp = renderHeader(h);
            int temp_height = temp.getHeight(null);
            totalheight += temp_height;
        }
        Major = new BufferedImage(2300, totalheight, BufferedImage.TYPE_INT_BGR);

        Graphics2D cur = (Graphics2D) Major.getGraphics();
        cur.setColor(Color.LIGHT_GRAY);
        cur.fillRect(0, 0, 2300, 2000);
        drawTabs(1);
        drawStuInfo();
        cur.drawImage(StudentInfo, 0, 0, null);
        cur.drawImage(currentTabState, 0, 450, null);
        int offset = 550;

        for (Header h : audit.myParser.getHeaders()){
            cur.fillRect(0, offset, 2300, 100);
            offset += 100;
            Image temp = renderHeader(h);
            int temp_height = temp.getHeight(null);
            cur.drawImage(temp, 0, offset, null);
            offset += temp_height;
        }
    }

    public static void drawElectives () throws Exception {
        ArrayList<RequirementSection> elect = new ArrayList<RequirementSection>();
        elect.add(audit.myParser.getGeneralElectives());
        elect.add(audit.myParser.getRequiredGeneralElectives());
        Header electives = new Header("General Electives", "IP", elect);
        Image rendered = renderHeader(electives);
        //get total height
        int totalheight = 550 + rendered.getHeight(null);
        Elective = new BufferedImage(2300, totalheight, BufferedImage.TYPE_INT_BGR);


        Graphics2D cur = (Graphics2D) Elective.getGraphics();
        cur.setColor(Color.LIGHT_GRAY);
        cur.fillRect(0, 0, 2300, 2000);
        drawTabs(2);
        drawStuInfo();
        cur.drawImage(StudentInfo, 0, 0, null);
        cur.drawImage(currentTabState, 0, 450, null);
        cur.drawImage(rendered, 0, 550, null);

    }

    public static void drawUniReqs(){
        Header h = audit.myParser.getUniversityRequirements();
        Image rendered = renderHeader(h);
        UniReqs = new BufferedImage(2300, 550 + rendered.getHeight(null), BufferedImage.TYPE_INT_BGR);
        Graphics2D cur = (Graphics2D) UniReqs.getGraphics();
        cur.setColor(Color.LIGHT_GRAY);
        cur.fillRect(0, 0, 2300, 550 + rendered.getHeight(null));
        drawTabs(3);
        drawStuInfo();
        cur.drawImage(StudentInfo, 0, 0, null);
        cur.drawImage(currentTabState, 0, 450, null);
        cur.drawImage(rendered, 0, 550, null);
    }

    public static Image getPrintedImage() throws Exception {
        drawSummary();
        drawElectives();
        drawMajor();
        drawUniReqs();
        Image print = new BufferedImage(2300, Summary.getHeight(null) + Major.getHeight(null) + Elective.getHeight(null) + UniReqs.getHeight(null) + 800 , BufferedImage.TYPE_INT_BGR);
        Graphics2D g = (Graphics2D) print.getGraphics();
        g.drawImage(Summary, 0, 0, null);
        g.drawImage(Major, 0, 200 + Summary.getHeight(null), null);
        g.drawImage(Elective, 0, 400 + Summary.getHeight(null) + Major.getHeight(null), null);
        g.drawImage(UniReqs, 0, 600 + Summary.getHeight(null) + Major.getHeight(null) + Elective.getHeight(null), null);
        return print;
    }

}
