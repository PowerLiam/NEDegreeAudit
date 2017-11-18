package AuditParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
        Elements headerSpans = pre.children();
        for (int ii = 6; ii < headerSpans.size(); ii += 2) {
            String text = headerSpans.get(ii).text();
            headers.add(text.substring(3, text.length()));
        }
        headers.remove(headers.size() - 1);
        headers.remove(headers.size() - 1);
        return headers;
    }

    public boolean isHonors() {

        return false;
    }

    private Course parseCourse(String text) {
        // "      FL17 CS  1800     4.00 RG  IP Discrete Structures (Hon)    "
        System.out.println("Parsing: " + text);
        return new Course(text.substring(6, 10), // semester
                text.substring(11, 15).trim(), // department
                text.substring(15, 19).trim(), // courseno
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

}
