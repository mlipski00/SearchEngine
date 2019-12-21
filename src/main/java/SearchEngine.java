import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.StringUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

class SearchEngine {

  private Map<String, List<Integer>> invertedIndex = new HashMap<>();
  private List<List<String>> listOfAllDocuments = new ArrayList<>();

  private static final String TERM_NOT_FOUND_MESSAGE = "Term not found";
  private static final String FAILED_MESSAGE = "Data loading failed. Please check name of file.";

  boolean loadFile(String fileName) {
    AtomicInteger counter = new AtomicInteger();
    try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
      lines.forEach(line -> {
        WhitespaceTokenizer instance = WhitespaceTokenizer.INSTANCE;
        String[] lineTokenized = instance.tokenize(line);
        counter.getAndIncrement();
        addTokenToInvertedIndex(counter, lineTokenized);
        listOfAllDocuments.add(Arrays.asList(lineTokenized));
      });
    } catch (IOException exception) {
      System.err.println(FAILED_MESSAGE);
      return false;
    }
    return true;
  }

  void findAndPrintData(String term) {
    if (invertedIndex.containsKey(term)) {
      System.out.println(prepareResult(term));
    } else {
      System.out.println(TERM_NOT_FOUND_MESSAGE);
    }
  }

  private List<Integer> getTerm(String term) {
    if (invertedIndex.containsKey(term)) {
      return invertedIndex.get(term);
    } else {
      return new ArrayList<>();
    }
  }

  private void addTokenToInvertedIndex(AtomicInteger counter, String[] lineTokenized) {
    Arrays.stream(lineTokenized).forEach(word -> {
      word = word.toLowerCase();
      word = word.replaceAll("[^a-zA-Z0-9\\s]", "");
      if (StringUtil.isEmpty(word)) {
        return;
      }
      List<Integer> occurrencesInDocuments = getTerm(word);
      if (occurrencesInDocuments.contains(counter.get())) {
        return;
      }
      occurrencesInDocuments.add(counter.get());
      invertedIndex.put(word, occurrencesInDocuments);
    });
  }

  private List<Result> prepareResult(String term) {
    TFIDFCalculator tfidfCalculator = new TFIDFCalculator();
    List<Result> resultList = new ArrayList<>();
    for (Integer documentNumber : invertedIndex.get(term)) {
      resultList.add(new Result(documentNumber, tfidfCalculator.computeTFIDF(listOfAllDocuments.get(documentNumber - 1), listOfAllDocuments, term)));
      resultList.sort(Comparator.comparingDouble(Result::getTfidf));
    }
    return resultList;
  }

}