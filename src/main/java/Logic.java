import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logic {
    /**
     * @author Chana
     * @description
     * main logic for refining and discerning sentences from an .srt file
     * refine timestamps and indexes and also discern sentences in the .srt File
     * return String as a result
     *
     * @return String
     */
    String discernSentence (File srtFile) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation document = null;
        try (BufferedReader br = new BufferedReader(new FileReader(srtFile))){
            String line;
            boolean emptyLine = true;
            StringBuilder text = new StringBuilder();
            while ((line = br.readLine()) != null) {
                /*
                srt file refining process
                 */
                // delete empty line
                if (line.trim().isEmpty()) {
                    emptyLine = true;
                    continue;
                }
                // delete time stamps line
                if (line.contains("-->")) {
                    emptyLine = false;
                    continue;
                }
                // delete line that contains only numbers
                if (emptyLine && line.matches("\\d+")) {
                    continue;
                }
                // put together in just one string
                text.append(line).append(' ');
            }
            document = new Annotation(text.toString());
            pipeline.annotate(document);

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        return constructContent(sentences);
    }

    /**
     * @author Chana
     * @description
     * makes Files that will contain the texts which were from the .srt file.
     * - if the filename exists, then generate next name adding '_number' at the end of the file's name. (Ex. test.txt --> test_1.txt)
     * - if the directory for the file doesn't exist, then it creates the directory first.
     */
    void makeFile (String filePath, String fileName, String extension, String content) {
        String filePathWithName = new StringBuilder()
                .append(filePath)
                .append('\\')
                .append(fileName)
                .append(extension)
                .toString();

        // set the file path
        try {
            Path directoryPath = Paths.get(filePath);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            File txtFile = new File(filePathWithName);
            if (txtFile.exists()) {
                String nextFileName = getNextFileName(filePath, fileName, extension);
                filePathWithName = new StringBuilder()
                        .append(filePath)
                        .append('\\')
                        .append(nextFileName)
                        .append(extension)
                        .toString();
                txtFile = new File(filePathWithName);
            }

            if (txtFile.createNewFile()) {
                System.out.println("File created : " + txtFile.getName());
                writeFile(filePathWithName, content);
            } else {
                System.out.println("File creation failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile (String filePath, String content) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath, true))) {
            out.println(content);
        }
    }

    /**
     * @author Chana
     * @param filePath
     * @param fileName
     * @param extension
     * @description
     * if a file to make already exists, then it makes the next file name.
     * note that it doesn't make actual file, it just generates the next name.
     * if a file 'test.txt' exists in the directory, then it makes 'test_1.txt'.
     * if two file of 'test.txt' and 'test_100.txt' exists, then it makes 'test_2.txt' for its name.
     *
     * @return newFileName
     */
    private String getNextFileName (String filePath, String fileName, String extension) {
        File directory = new File(filePath);

        File[] files = directory.listFiles();

        int max = 0;
        Pattern pattern = Pattern.compile(Pattern.quote(fileName) + "_(\\d+)" + extension);

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String name = file.getName();
                    Matcher matcher = pattern.matcher(name);

                    if (matcher.matches()) {
                        int number = Integer.parseInt(matcher.group(1));
                        if (number > max) {
                            max = number;
                        }
                    }
                }
            }
        }

        int nextNumber = max + 1;

        String newFileName = new StringBuilder()
                .append(fileName)
                .append('_')
                .append(nextNumber)
                .toString();

        return newFileName;
    }

    /**
     * @author Chana
     * @description
     * constructs an actual contents of strings for the result file
     * - configure member variable "content" in this class
     *
     * @return content
     */
    private String constructContent(List<CoreMap> sentences) {
        StringBuilder sb = new StringBuilder();
        for (CoreMap sentence : sentences) {
            sb.append(sentence).append('\n');
        }

        return sb.toString();
    }

}
