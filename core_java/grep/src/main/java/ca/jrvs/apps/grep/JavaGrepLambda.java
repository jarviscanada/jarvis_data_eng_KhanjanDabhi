package ca.jrvs.apps.grep;

import com.sun.org.slf4j.internal.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class JavaGrepLambda extends JavaGrepImp {

    public static void main(String[] args) {
        if (args.length != 3) {
            //Creating JavaGrepLambda
            JavaGrepLambda javaGrepLambda = new JavaGrepLambda();
            // set the arguments
            javaGrepLambda.setRegex(args[0]);
            javaGrepLambda.setRootPath(args[1]);
            javaGrepLambda.setOutFile(args[2]);

            try {
                javaGrepLambda.process();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Read a file and return all the lines
     *
     * @param inputFile file to be read
     * @return lines
     */
    @Override
    public List<String> readLines(File inputFile) {
        List<String> result;
        try (Stream<String> lines = Files.lines(Paths.get(String.valueOf(inputFile)))) {
            result = lines.collect(Collectors.toList());
        }catch(Exception e){
            result = null;
        }
        return result;
    }

    /**
     * Traverse a given directory and return all files
     *
     * @param rootDir input directory
     * @return files under the rootDir
     */
    @Override
    public List <File> listFiles(String rootDir){
        try {
            List<File> files = Files.list(Paths.get(rootDir)).map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            Logger logger = null;
            logger.error("Error unable to process",e);
        }
        return listFiles(rootDir);
    }

}