package AuditParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AuditParser {
    private Document document;

    public AuditParser() {}

    public void setDocument(String path) throws IOException {
        document = Jsoup.parse(new File(path), "UTF-8");
    }

    public ArrayList<String> getHeaders() throws Exception {
        if (document == null) throw new Exception();
        ArrayList<String> headers = new ArrayList();
        Element pre = document.getElementsByTag("pre").get(0);
        Elements headerElems = pre.children();
        //Get the header names
        for (int ii = 6; ii < headerElems.size(); ii += 2) {
            String headerName = headerElems.get(ii).textNodes().get(0).getWholeText();
        }

        return headers;
    }

    public int getNumberInParens(String text) {
        Pattern pattern = Pattern.compile("\\((\\d*)\\)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            return -1;
        }
    }

    public ArrayList<Course> getRegisteredCourses() {
        Elements previewTexts = document.getElementsByClass("auditPreviewText");
        previewTexts.removeIf((element) -> !element.ownText().contains("THE FOLLOWING COURSES ARE CURRENT REGISTERED"));
        return parseRegisteredCourses(previewTexts.get(0).parent().parent());
    }

    public ArrayList<Course> getGeneralElectives() {
        Elements previewTexts = document.getElementsByClass("auditPreviewText");
        previewTexts.removeIf((element) -> !element.ownText().equals("GENERAL ELECTIVES"));
        return parseRegisteredCourses(previewTexts.get(1).parent().nextElementSibling().nextElementSibling());
    }

    private Course parseCourse(String text) {
        // "      FL17 CS  1800     4.00 RG  IP Discrete Structures (Hon)    "
        System.out.println("Parsing: " + text);
        return new Course(text.substring(6, 10), // semester
                text.substring(11, 15).trim(), // department
                text.substring(15, 23).trim(), // courseno
                text.substring(24, 28).trim(), // credits
                text.substring(29, 32).trim(), // registration status
                text.substring(33, 35).trim(), // progress
                text.substring(36, text.length()).trim()); // name
    }

    private ArrayList<Course> parseRegisteredCourses(Element parent) {
        Elements children = parent.children();
        children.removeIf((element) -> !(element.tagName().equals("font"))); // get the elements of tag "font"
        children.removeIf((element) -> element.text().contains("Course List")); // remove the trash that contains "Course List"
        children.removeIf((element) -> element.text().contains("ADVISOR USE")); // remove "ADVISOR USE"
        children.removeIf((element) -> element.text().contains("NEEDS")); // remove "NEEDS"
        ArrayList<Course> courses = new ArrayList();
        for (Element element : children) {
            courses.add(parseCourse(element.textNodes().get(0).getWholeText()));
        }
        return courses;
    }

    public RequirementSection getRequirementSection(String header) {
        Elements previewTexts = document.getElementsByClass("auditPreviewText");
        previewTexts.removeIf((element) -> !element.ownText().contains(header));
        Element headerElem = previewTexts.get(0);
        String headerText = headerElem.textNodes().get(0).getWholeText();
        System.out.println("Header text: " + headerText + " or " + headerElem.text());
        // "IP-     COMPUTER SCIENCE FUNDAMENTAL COURSES                     "
        return new RequirementSection(
                headerText.substring(8, headerText.length()).trim(), // header
                headerText.substring(0, 3).trim(), // status
                0, // number required
                parseRegisteredCourses(headerElem.parent().parent()), // registered courses
                new ArrayList()); // TODO: options to fulfill requirements
    }


    public StudentInfo getStudentInfo() throws Exception {
        if (this.document == null) throw new Exception();
        ArrayList<String> headers = new ArrayList();
        Element pre = this.document.getElementsByTag("pre").get(0);
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
        String focus = headers.get(4).split(" - ")[0];
        String focus_type = headers.get(4).split(" ")[3];
        return new StudentInfo(NUID, first_name, last_name, grad_date, program_code, cat_year, degree, focus, focus_type);
    }
}
