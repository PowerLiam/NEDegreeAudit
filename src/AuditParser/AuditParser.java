package AuditParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class AuditParser {
    private static Document document;

    public static void setDocument(String path) throws IOException {
        document = Jsoup.parse(new File(path), "UTF-8");
    }

    public static ArrayList<String> getHeaders() throws Exception {
        if (document == null) throw new Exception();
        ArrayList<String> headers = new ArrayList();
        Element pre = document.getElementsByTag("pre").get(0);
        Elements headerSpans = pre.children();
        //Get the headers.
        for (int ii = 6; ii < headerSpans.size(); ii += 2) {
            String text = headerSpans.get(ii).text();
            headers.add(text.substring(3, text.length()));
        }
        headers.remove(headers.size() - 1);
        headers.remove(headers.size() - 1);
        return headers;
    }

    public static RequirementSection getRequirementSection(String header) {
        return null;
    }


    public static StudentInfo getStudentInfo() throws Exception {
        if (document == null) throw new Exception();
        ArrayList<String> headers = new ArrayList();
        Element pre = document.getElementsByTag("pre").get(0);
        Elements headerSpans = pre.children();
        //Get the headers.
        for (int ii = 0; ii < 6; ii ++) {
            headers.add(headerSpans.get(ii).text());
        }
        String NUID = UTILS.remove(headers.get(0).split(" "), "")[4];
        String first_name = UTILS.remove(headers.get(1).split(" "), "")[1];
        String last_name = headers.get(1).split(" ")[0];
        String grad_date = UTILS.remove(headers.get(1).split(" "), "")[4];
        String program_code = headers.get(2).split(" ")[2];
        String cat_year = UTILS.remove(headers.get(2).split(" "), "")[5];;
        String degree = headers.get(3);
        String focus = headers.get(4).split(" ")[0];
        String focus_type = headers.get(4).split(" ")[3];
        return new StudentInfo(NUID, first_name, last_name, grad_date, program_code, cat_year, degree, focus, focus_type);
    }
}
