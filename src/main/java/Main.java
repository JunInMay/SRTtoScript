import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        File srtFile = new File("testFiles/[English] I'm a Gay Pastor. Ask Me Anything [DownSub.com].srt");

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        try (BufferedReader br = new BufferedReader(new FileReader(srtFile))){
            String line;
            boolean emptyLine = true;
            StringBuilder text = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    emptyLine = true;
                    continue;
                }
                if (line.contains("-->")) {
                    emptyLine = false;
                    continue;
                }
                if (emptyLine && line.matches("\\d+")) {
                    continue;
                }
                text.append(line).append(' ');
            }

            Annotation document = new Annotation(text.toString());
            pipeline.annotate(document);

            List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
            for (CoreMap sentence : sentences) {
                System.out.println(sentence);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
