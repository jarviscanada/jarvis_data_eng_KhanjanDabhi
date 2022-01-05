package ca.jrvs.apps.grep.grep;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep{
    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);
    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) throws IllegalAccessException {
        if (args.length != 3) {
            throw new IllegalAccessException("USAGE: JavaGrep regex rootPath outFile");
        }
        JavaGrepImp javaGrepImp = new JavaGrepImp();
        JavaGrepImp.setRegex(args[0]);
        JavaGrepImp.setRootPath(args[1]);
        JavaGrepImp.setOutFile(args[2]);
        BasicConfigurator.configure();

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }
    }

    @Override
    public void process() throws IOException {
        try {

            List<String> matchedLines = new ArrayList<>();
            for (File file : listFiles(getRootPath())) {
                for (String line : readLines(file)) {
                    if (containsPattern(line)) {
                        matchedLines.add(line);
                    }
                }
            }
            writeToFile(matchedLines);
        } catch (Exception e)
        {
            //log error
            logger.error("Error: Unable to write file to a disk", e);
        }
    }

    @Override
    public List<File> listFiles(String rootDir) {
        return null;
    }

    @Override
    public List<String> readLines(File inputFile) {
        return null;
    }

    @Override
    public boolean containsPattern(String line) {
        Pattern p = Pattern.compile(getRegex());
        Matcher m = p.matcher(line);
        return m.find();
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {

    }

    @Override
    public String getRootPath() {
        return this.rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return this.regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return this.outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}
