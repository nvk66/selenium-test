package nlp;

import java.io.IOException;
import java.util.Map;

public class ExecutableNlp {

    private static final String FILE_PATH = "src/main/resources/text.txt";
    private static final String RESULT_DIRECTORY_PATH = "src/main/java/nlp/result/";

    public static void main(String[] args) throws IOException {
        NlpFileExecutor fileExecutor = new NlpFileExecutor();
        NlpExecutor nlpExecutor = new NlpExecutor();
        String text = fileExecutor.getTextFromFile(FILE_PATH);
        fileExecutor.createResultDirectory();
        writeArrayToFile(fileExecutor, RESULT_DIRECTORY_PATH + "tokens", nlpExecutor.getTokensFromText(text));
        writeArrayToFile(fileExecutor, RESULT_DIRECTORY_PATH + "sentences", nlpExecutor.getSentences(text));
        writeMapToFile(fileExecutor, nlpExecutor.getLemmasAndStemers(text));
    }

    private static void writeArrayToFile(NlpFileExecutor worker,
                                         String filePath,
                                         String[] array) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String str : array) {
            sb.append(str)
                    .append("\n");
        }
        worker.writeToFile(sb.toString(), filePath);
    }

    private static void writeMapToFile(NlpFileExecutor worker,
                                       Map<String, String> map) throws IOException {
        StringBuilder sb = new StringBuilder();
        map.forEach((stemmer, lemma) ->
            sb.append("Stemmer: ").append(stemmer)
                    .append("\n")
                    .append("Lemma: ").append(lemma)
                    .append("\n")
        );
        worker.writeToFile(sb.toString(), RESULT_DIRECTORY_PATH + "lemmas");
    }

}
