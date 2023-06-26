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

    void readFile(File file) {
        srtFile = file;
    }

    /**
     * @Author Chana
     * @Description
     * main logic for refining and discerning sentences from an .srt file
     */
    List discernSentence() {
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
        return sentences;
    }

    /**
     * @Author Chana
     * @Description
     * make Files that will contain the texts from the .srt file.
     */
    void makeFile() {
        // set the file path
        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                System.out.println("File created : " + file.getName());
            } else {
                System.out.println("File creation failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void logic() {
        List<CoreMap> sentences = discernSentence();
        makeFile();

        /*
        write the string to the file made before
         */
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            StringBuilder sb = new StringBuilder();
            for (CoreMap sentence : sentences) {
                sb.append(sentence).append('\n');
            }
            fileWriter.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
