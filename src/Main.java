import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Chris on 11/18/2017.
 */
public class Main {
    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect("http://google.com").get();
            System.out.println(document.html());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
