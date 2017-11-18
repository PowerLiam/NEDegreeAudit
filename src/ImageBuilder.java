import jdk.nashorn.internal.parser.JSONParser;
import netscape.javascript.JSObject;

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
    private static JSObject json;
    private static JSONParser parse_json;

    public void setJson(JSObject j){
        json = j;
    }





}
