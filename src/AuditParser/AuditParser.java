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
        for (int ii = 6; ii < headerSpans.size(); ii += 2) {
            headers.add(headerSpans.get(ii).text());
        }
        return headers;
    }
}
