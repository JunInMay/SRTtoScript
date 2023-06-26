import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class Logic {

    private File srtFile;
    private static String filePath = "testFileLocation\\test.txt";
    private File txtFile;
    private String content;

    public void readFile(File file) {
        content = null;
        txtFile = null;
        srtFile = file;
    }

    /**
     * @author Chana
     * @description
     * main logic for refining and discerning sentences from an .srt file
     * refine timestamps and indexes and also discern sentences in the .srt File
     * return String as a result
     *
     * @return String
     */
    String discernSentence () {
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
     */
    void makeFile () {
        // set the file path
        try {
            txtFile = new File(filePath);
            if (txtFile.createNewFile()) {
                System.out.println("File created : " + txtFile.getName());
            } else {
                System.out.println("File creation failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        content = sb.toString();

        return content;
    }

    void logic () {
        discernSentence();
        makeFile();


    }

}
