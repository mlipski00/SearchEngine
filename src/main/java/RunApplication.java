import java.util.Scanner;

/**
 * Search engine implementing inverted indexing.
 * Results of queries are sorted by term frequency–inverse document frequency.
 *
 * @author Michał Lipski
 */
class RunApplication {

  private static final String HELLO_MESSAGE = "Hello. Please type name of the file containing the documents or type 'quit' to finish the program.";
  private static final String INFO_MESSAGE = "Type a search word or type 'quit' to finish the program.";
  private static final String QUIT_MESSAGE = "quit";
  private static final String RETRY_MESSAGE = "Please retry.";
  private static final String LOADING_SUCCESS_MESSAGE = "Documents loaded correctly.";
  private static Scanner scan = new Scanner(System.in);
  private static SearchEngine searchEngine = new SearchEngine();

  public static void main(String[] args) {
    System.out.println(HELLO_MESSAGE);
    loadDocumentsLoop();
    startQueryLoop();
  }

  private static void loadDocumentsLoop() {
    boolean isDataLoaded = false;
    while (!isDataLoaded) {
      String userChoice = scan.nextLine();
      if (userChoice.equals(QUIT_MESSAGE)) {
        System.exit(0);
      }
      isDataLoaded = searchEngine.loadFile(userChoice);
      String resultMessage = isDataLoaded ? LOADING_SUCCESS_MESSAGE : RETRY_MESSAGE;
      System.out.println(resultMessage);
    }
  }

  private static void startQueryLoop() {
    String userQuery;
    while (true) {
      System.out.println(INFO_MESSAGE);
      scan.reset();
      userQuery = scan.nextLine();
      if (userQuery.equals(QUIT_MESSAGE)) {
        System.exit(0);
      } else {
        searchEngine.findAndPrintData(userQuery);
      }
    }
  }
}
