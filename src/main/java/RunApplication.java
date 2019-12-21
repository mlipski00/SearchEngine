import java.util.Scanner;

class RunApplication {

  private static final String HELLO_MESSAGE = "Hello. Please type file name with documents.";
  private static final String INFO_MESSAGE = "Type a word or type 'quit' to finish the program";
  private static final String QUIT_MESSAGE = "quit";
  private static final String RETRY_MESSAGE = "Please retry.";
  private static final String LOADING_SUCCESS_MESSAGE = "Documents loaded correctly.";
  private static Scanner scan = new Scanner(System.in);

  public static void main(String[] args) {
    SearchEngine searchEngine = new SearchEngine();
    System.out.println(HELLO_MESSAGE);
    boolean isDataLoaded = false;
    while (!isDataLoaded) {
      String filename = scan.nextLine();
      isDataLoaded = searchEngine.loadFile(filename);
      String resultMessage = isDataLoaded ? LOADING_SUCCESS_MESSAGE : RETRY_MESSAGE;
      System.out.println(resultMessage);
    }
    startQueryLoop(searchEngine);
  }

  private static void startQueryLoop(SearchEngine searchEngine) {
    String choice;
    for (;;) {
      System.out.println(INFO_MESSAGE);
      scan.reset();
      choice = scan.nextLine();
      if (choice.equals(QUIT_MESSAGE)) {
        System.exit(0);
      } else {
        searchEngine.findAndPrintData(choice);
      }
    }
  }
}
