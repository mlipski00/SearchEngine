class Result {

  private int documentNumber;
  private double tfidf;

  double getTfidf() {
    return tfidf;
  }

  @Override
  public String toString() {
    return "{document: " + documentNumber +
        ", tfidf: " + tfidf +
        '}';
  }

  Result(int documentNumber, double tfidf) {
    this.documentNumber = documentNumber;
    this.tfidf = tfidf;
  }
}
