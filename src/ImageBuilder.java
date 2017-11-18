
import AuditParser.Audit;
import org.jsoup.nodes.Document;

import java.awt.*;

/**
 * Created by Chris on 11/18/2017.
 */
public class ImageBuilder {
    public static Image Summary;
    public static Image Major;
    public static Image Elective;
    public static Image UniReqs;
    public static Image PrintView;
    private static Graphics2D draw;
    private static Audit audit;

    public void setDoc(Audit d){
        audit = d;
    }

}
