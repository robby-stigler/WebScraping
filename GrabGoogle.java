import com.jaunt.*;
import com.jaunt.component.*;
import java.io.*;
import java.util.Scanner;

/**
 * Created by Robert on 6/4/2017.
 */
public class GrabGoogle {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("What would you like to search Google for? ");
        String search = sc.nextLine();
        System.out.print("How many pages of results would you like to see? ");
        int numPages = sc.nextInt();
        try {
            getGoogleResults(search, numPages);
        } catch (JauntException e) {         //if an HTTP/connection error occurs, handle JauntException.
            System.err.println(e);
        }
    }

    public static void getGoogleResults(String searchTerm, int numPages) throws JauntException{
        UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
        userAgent.visit("http://google.com");                    //visit google
        userAgent.doc.apply(searchTerm);
        userAgent.doc.submit("Google Search");
        Elements links = userAgent.doc.findEvery("h3 class=r").findEvery("<a>");
        for (Element link : links) {
            System.out.println(link.getAt("href"));
        }
        System.out.println("End of page 1");
        for (int i = 1; i < numPages; i++) {
            System.out.println("----------------------------");
            String nextURL = userAgent.doc.getHyperlink("Next").getHref();
            userAgent.visit(nextURL);
            links = userAgent.doc.findEvery("h3 class=r").findEvery("<a>");
            for (Element link : links){
                System.out.println(link.getAt("href"));
            }
            System.out.println("End of page " + (i + 1));
        }
    }
}