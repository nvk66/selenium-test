package nlp;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

public class NlpExecutor {
    private final SentenceDetectorME sentenceDetectorME;
    private final TokenizerME tokenizerME;
    private final POSModel posModel;
    private final DictionaryLemmatizer dictionaryLemmatizer;

    public NlpExecutor() {
        try {
            this.sentenceDetectorME =
                    new SentenceDetectorME(
                            new SentenceModel(
                                    new FileInputStream("src/main/resources/bins/en-sent.bin")));
            this.tokenizerME =
                    new TokenizerME(
                            new TokenizerModel(
                                    new FileInputStream("src/main/resources/bins/en-token.bin")));
            this.posModel =
                    new POSModel(
                            new FileInputStream("src/main/resources/bins/en-pos-maxent.bin"));
            this.dictionaryLemmatizer =
                    new DictionaryLemmatizer(
                            new FileInputStream("src/main/resources/bins/en-lemmatizer.dict"));
        } catch (IOException e) {
            throw new RuntimeException("Can't initialize Executor");
        }
    }

    public String[] getSentences(String text) {
        return sentenceDetectorME.sentDetect(text);
    }

    public String[] getTokensFromText(String text) {
        return tokenizerME.tokenize(text);
    }

    public Map<String, String> getLemmasAndStemers(String text) {
        Map<String, String> result = new TreeMap<>();
        POSTaggerME posTagger = new POSTaggerME(posModel);
        String[] tokens = getTokensFromText(text);
        String[] tags = posTagger.tag(tokens);
        String[] lemmas = dictionaryLemmatizer.lemmatize(tokens, tags);
        for (int i = 0; i < lemmas.length && i < tags.length; i++) {
            if (!lemmas[i].equals("O")) {
                result.put(tokens[i], lemmas[i]);
            }
        }
        return result;
    }
}
