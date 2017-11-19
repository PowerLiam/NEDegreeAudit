package AuditParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AuditParser {
    private Document document;

    public AuditParser() {}

    public void setDocument(String path) throws IOException {
        document = Jsoup.parse(new File(path), "UTF-8");
    }

    private boolean isHeaderText(String text) {
        String sub = text.substring(0, 3);
        return (sub.equals("  -") || sub.equals("  +") || sub.equals("IP-") || sub.equals("IP+"));
    }
    public ArrayList<Header> getHeaders() throws Exception {
        if (document == null) throw new Exception();
        ArrayList<Header> headers = new ArrayList<>();
        Element pre = document.getElementsByTag("pre").get(0);
        Elements headerElems = pre.children();
        headerElems.removeIf((element) -> !element.className().equals("auditPreviewText"));
        headerElems.removeIf((element) -> (element.text().contains("ADDITIONAL COURSE INFORMATION")
                || element.text().contains("MAJOR GPA REQUIREMENT")
                || element.text().contains("REQUIRED GENERAL ELECTIVES")
                || element.text().contains("NUpath")));
        headerElems.removeIf((element) -> (element.textNodes().size() != 0));
        //Get the header names
        for (int ii = 0; ii < headerElems.size(); ii++) {
            // get header name
            String headerName = headerElems.get(ii).children().get(0).textNodes().get(0).getWholeText();
            Elements previewTexts = document.getElementsByClass("auditPreviewText");
            previewTexts.removeIf((element) -> {
                List<TextNode> nodes = element.textNodes();
                if (nodes.size() == 0) return true;
                else {
                    return !nodes.get(0).getWholeText().equals(headerName);
                }
            });
            Element headerRoot = previewTexts.get(1);
            System.out.println("Parsing the header: " + headerRoot.ownText());
            headers.add(parseHeader(headerRoot));
        }

        return headers;
    }

    public Header getUniversityRequirements() {
        String[] names = {
                "NATURAL and DESIGNED WORLD (ND)",
                "CREATIVE EXPRESSION/INNOVATION (EI)",
                "INTERPRETING CULTURE (IC)",
                "FORMAL and QUANTITATIVE REASONING (FQ)",
                "SOCIETIES and INSTITUTIONS (SI)",
                "ANALYZING/USING DATA (AD)",
                "DIFFERENCE and DIVERSITY (DD)",
                "ETHICAL REASONING (ER)",
                "FIRST YEAR WRITING (WF)",
                "WRITING INTENSIVE (WI)",
                "ADVANCED WRITING IN THE DISCIPLINES (WD)",
                "INTEGRATION EXPERIENCE (EX)",
                "CAPSTONE EXPERIENCE (CE)"
        };
        ArrayList<RequirementSection> reqs = new ArrayList<>();
        for (int ii = 0; ii < names.length; ii++) {
            Elements previewTexts = document.getElementsByClass("auditPreviewText");
            final int index = ii;
            previewTexts.removeIf((element) -> !element.ownText().contains(names[index]));
            RequirementSection req = getRequirementSection(previewTexts.get(1).parent().nextElementSibling().children().get(0).children().get(0));
            req.setTitle(names[index]);
            reqs.add(req);
        }
        return new Header(
                "University Requirements",
                "",
                reqs
        );
    }

    private Header parseHeader(Element headerRoot) {
        String headerText = headerRoot.textNodes().get(0).getWholeText();
        ArrayList<RequirementSection> reqs = new ArrayList<>();
        Element current = headerRoot.parent().nextElementSibling();
        while (!current.tagName().equals("hr")) {
            System.out.println("The tag name of current is: " + current.tagName());
            if (current.children().size() > 0) {
                Element headerElem = current.children().get(0).children().get(0);
                if (isHeaderText(headerElem.textNodes().get(0).getWholeText())) {
                    System.out.println("Getting requirement section of " + headerElem.ownText());
                    reqs.add(getRequirementSection(headerElem));
                }
            }
            current = current.nextElementSibling();
        }
        Header header = new Header(
                headerText.substring(7, headerText.length()),
                headerText.substring(0, 3).trim(),
                reqs
        );
        return header;
    }

    public int getNumberInParens(String text) {
        Pattern pattern = Pattern.compile("\\((\\d*)\\)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            return 0;
        }
    }

    public RequirementSection getRegisteredCourses() {
        Elements previewTexts = document.getElementsByClass("auditPreviewText");
        previewTexts.removeIf((element) -> !element.ownText().contains("THE FOLLOWING COURSES ARE CURRENT REGISTERED"));
        return getRequirementSection(previewTexts.get(0).parent().parent().children().get(0).children().get(0));
    }

    public RequirementSection getGeneralElectives() {
        Elements previewTexts = document.getElementsByClass("auditPreviewText");
        previewTexts.removeIf((element) -> !element.ownText().equals("GENERAL ELECTIVES"));
        //return parseRegisteredCourses(previewTexts.get(1).parent().nextElementSibling().nextElementSibling());
        RequirementSection req = new RequirementSection("", "", 0, parseRegisteredCourses(previewTexts.get(1).parent().nextElementSibling().nextElementSibling()), new ArrayList<String>());
        req.setTitle("General Electives");
        return req;
    }

    public RequirementSection getRequiredGeneralElectives() {
        Elements previewTexts = document.getElementsByClass("auditPreviewText");
        previewTexts.removeIf((element) -> !element.ownText().contains("REQUIRED GENERAL ELECTIVES"));
        //return parseRegisteredCourses(previewTexts.get(1).parent().nextElementSibling());
        RequirementSection req = parseHeader(previewTexts.get(1)).getRequirements().get(0);
        req.setTitle("Required General Electives");
        return req;
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
        children.removeIf((element) -> (element.children().size() > 0)); // remove nodes w/ more than one child
        ArrayList<Course> courses = new ArrayList();
        for (Element element : children) {
            Course course = parseCourse(element.textNodes().get(0).getWholeText());
            if (!course.getDepartment().equals("")) { // avoids weird courses that don't "count"
                courses.add(course);
            }
        }
        return courses;
    }

    private int parseNumRequired(Element parent) {
        Elements children = parent.children();
        children.removeIf((element) -> !element.text().contains("Complete"));
        if (children.size() > 0) {
            return getNumberInParens(children.get(0).text());
        } else return 0;
    }


    public RequirementSection getRequirementSection(Element headerElem) {
        String headerText = headerElem.textNodes().get(0).getWholeText();
        String title = "";
        if (headerText.toUpperCase().equals(headerText)) {
            // is a proper title
            title = headerText.substring(8, headerText.length()).trim();
        }
        // "IP-     COMPUTER SCIENCE FUNDAMENTAL COURSES                     "
        return new RequirementSection(
                title, // title of section, if there is one
                headerText.substring(0, 3).trim(),
                parseNumRequired(headerElem.parent().parent()), // number required
                parseRegisteredCourses(headerElem.parent().parent()), // registered courses
                new ArrayList()); // TODO: options to fulfill requirements
    }

    public int[] getTotalHours() {
        int array[] = {0, 0};
        Elements elements = document.getElementsByClass("auditPreviewText");
        elements.removeIf((element) -> !element.ownText().contains("total semester hours required."));
        Element totalHoursElement = elements.get(0);
        Pattern pattern = Pattern.compile("(\\d*)");
        Matcher matcher = pattern.matcher(totalHoursElement.ownText());
        if (matcher.find()) {
            array[1] = Integer.parseInt(matcher.group(1));
        }
        matcher = pattern.matcher(totalHoursElement.parent().nextElementSibling().ownText());
        if (matcher.find()) {
            array[0] = Integer.parseInt(matcher.group(1));
        }
        return array;
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
