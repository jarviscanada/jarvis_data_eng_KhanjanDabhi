package ca.jrvs.apps.grep.practice;
import javax.sound.sampled.Line;
import java.util.regex.*;
public class RegexImplementation implements RegexExc{
    public static void main(String[] args) {

        System.out.println(new RegexImplementation().matchJpeg("c.jpg"));
        System.out.println(new RegexImplementation().matchIP("0.999.0.999"));
        System.out.println(new RegexImplementation().isLineEmpty(" "));
    }



    public boolean matchJpeg(String filename) {

        Pattern p = Pattern.compile("([^\\s]+(\\.(?i)(jpe?g))$)");
        Matcher m = p.matcher(filename);
        return m.matches();
    }


    public boolean matchIP(String ip) {
        Pattern p = Pattern.compile("((?:[0-9]{1,3}\\.){3}[0-9]{1,3})");
        Matcher m = p.matcher(ip);
        return m.matches();
    }


    public boolean isLineEmpty(String line) {
        Pattern p = Pattern.compile("\\s");
        Matcher m = p.matcher(line);
        return m.matches();
    }
}
