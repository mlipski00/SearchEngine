import java.util.List;

class TFIDFCalculator {

  double computeTFIDF(List<String> document, List<List<String>> allDocuments, String term) {
    return tf(document, term) * idf(allDocuments, term);
  }

  private double tf(List<String> document, String term) {
    double counter = 0;
    for (String word : document) {
      if (term.equals(word)) {
        counter++;
      }
    }
    return counter / document.size();
  }

  private double idf(List<List<String>> allDocuments, String term) {
    double counter = 0;
    for (List<String> document : allDocuments) {
      for (String word : document) {
        if (term.equals(word)) {
          counter++;
          break;
        }
      }
    }
    counter = counter > 0 ? counter : 1;
    return Math.log(allDocuments.size() / counter);
  }
}


