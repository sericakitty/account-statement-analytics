import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MainProgram {

  static CategoryKeywordsRegister categoryKeywordsRegister = new CategoryKeywordsRegister();
  static CategoryTransactionsRegister categoryTransactionsRegister = new CategoryTransactionsRegister();
  static Scanner reader = new Scanner(System.in);
  static File accountStatementFile = new File("AccountStatement.csv");
  static File categoryKeywordsFile = new File("CategoryKeywords.csv");
  static DecimalFormat df = new DecimalFormat("0.00");
  static int terminalWidth = 25;

  public static void printMenu() {
    System.out.println();
    System.out.println("What would you like to do?");
    System.out.println("1 - List categories and keywords");
    System.out.println("-------------------");
    System.out.println("2 - Add category");
    System.out.println("3 - Add keyword");
    System.out.println("-------------------");
    System.out.println("4 - Fetch sum for each category");
    System.out.println("5 - Fetch sum for a single category");
    System.out.println("-------------------");
    System.out.println("6 - Save Category and keywords to file");
    // System.out.println("7 - Save transactions data to file"); // not yet implemented
    System.out.println("-------------------");
    System.out.println("Enter - Print menu again");
    System.out.println("0 - Exit program");
  }

  public static void askUser() throws InterruptedException, FileNotFoundException {
    
    boolean continueAsking = true;

    while (continueAsking) {

      printMenu();

      System.out.println();
      System.out.print("Enter choice: ");
      String choice = reader.nextLine();
      try {

        int choiceNumber = Integer.parseInt(choice);

        switch (choiceNumber) {
          case 0:
            System.out.println("Exiting program");
            continueAsking = false;
            break;
          case 1:
            printCategoriesAndKeywords();
            break;

          case 2:
            addCategory();
            break;

          case 3:
            addKeyword();
            break;

          case 4:
            printSumForEachCategory();
            break;

          case 5:
            printSumForASingleCategory();
            break;

          case 6:
            saveCategoriesAndKeywordsToCsvFile();
            break;

          default:
            printMenu();
            break;
            
        }
        
      } catch (NumberFormatException e) {
        System.out.println("Invalid choice, please try again");
        
      }
      
    }

  }

  /**
   * This method prints all categories and keywords.
   */
  public static void printCategoriesAndKeywords() {
    System.out.println(String.format("%-" + terminalWidth + "s%-" + terminalWidth + "s" , "Category:", "Keywords:"));
    System.out.println();
    for (String category : categoryKeywordsRegister.getCategoryKeywords().keySet()) {
      String categoryString = category.substring(0, 1).toUpperCase() + category.substring(1);
      System.out.println(String.format("%-" + terminalWidth + "s%-" + terminalWidth + "s" , categoryString, categoryKeywordsRegister.getCategoryKeywords().get(category)));
      System.out.println("---------------------------------------------------------------");
    }
  }

  /**
   * This method adds a category to the categoryKeywordsRegister.
   */
  public static void addCategory() {
    System.out.print("Enter category name: ");
    String category = reader.nextLine();

    if (categoryKeywordsRegister.doesCategoryExist(category)) {
      System.out.println("Category already exists");
      return;
    }

    try {
      categoryKeywordsRegister.addCategory(category);
      System.out.println("Category added");
    } catch (Exception e) {
      System.out.println("Adding category failed");
    }

    categoryTransactionsRegister.updatetransactionList();
  }

  /**
   * This method adds a keyword to the categoryKeywordsRegister.
   * If the category does not exist, you will able to add it.
   * 
   */
  public static void addKeyword() {
    System.out.print("Enter category name: ");
    String category = reader.nextLine();
    if (!categoryKeywordsRegister.doesCategoryExist(category)) {
      System.out.println("Category does not exist");
      System.out.print("Do you want to add the category? (y/n): ");
      String answer = reader.nextLine();
      if (answer.equals("y")) {
        categoryKeywordsRegister.addCategory(category);
        System.out.println("Category added");
      } else {
        return;
      }
    }
    System.out.println();
    System.out.println("Enter keyword name (empty to stop)\n(hint: the closer the keyword matches\nthe title on the transaction,\nthe more likely it is to be categorized here): ");
    while (true) {
      
      String keyword = reader.nextLine();
      if (keyword.isEmpty()) {
        break;
      }
      if (categoryKeywordsRegister.doesKeywordExist(category, keyword)) {
        System.out.println("keyword already exists");
        continue;
      }
      categoryKeywordsRegister.addKeyword(category, keyword);
    }

    categoryTransactionsRegister.updatetransactionList();
  }

  /*
  * This method fetches all transaction data from the account statement file and prints them by category.
  */
  public static void printSumForEachCategory() {

      for (String category : categoryTransactionsRegister.getCategoryTransactions().keySet()) {
        
        System.out.print(String.format("%-" + terminalWidth + "s", category.substring(0, 1).toUpperCase() + category.substring(1) + ":"));
        System.out.println(df.format(categoryTransactionsRegister.getCategoryTransactionsSum(category)));
        System.out.println("-------------------------------------------------");
      }
      
      // these prints can be enabled by removing the '//' in front
      // System.out.println(String.format("%-" + terminalWidth + "s%-" + terminalWidth + "s", "Total income: ", categoryTransactionsRegister.getIncome()));
      // System.out.println("-------------------------------------------------");

      // System.out.println(String.format("%-" + terminalWidth + "s%-" + terminalWidth + "s", "Total expenses: ", categoryTransactionsRegister.getExpenses()));
      // System.out.println("-------------------------------------------------");

      System.out.println(String.format("%-" + terminalWidth + "s%-" + terminalWidth + "s", "Total income - expenses: ", df.format(categoryTransactionsRegister.getSum())));
      System.out.println("-------------------------------------------------");
    
  }

  /*
   * This method fetches Sum for a single category and prints them.
   */
  public static void printSumForASingleCategory() throws FileNotFoundException {
    
    // categoryTransactionsRegister.reset();
    // Scanner fileReader = null;

    try {
      // fileReader = new Scanner(accountStatementFile);

      int categoriesCount = 0;

      for (String category : categoryKeywordsRegister.getCategoryKeywords().keySet()) {
          System.out.print(category.substring(0, 1).toUpperCase() + category.substring(1));

          if (categoriesCount != categoryKeywordsRegister.getCategoryKeywords().keySet().size() - 1) {
              System.out.print(", ");
          }

          categoriesCount++;
          
          if (categoriesCount % 4 == 0) {
              System.out.println(); 
          }

      }
      
      System.out.println();
      System.out.print("Enter category name: ");
      String category = reader.nextLine().toLowerCase();
      
      if (!categoryKeywordsRegister.doesCategoryExist(category)) {
        System.out.println("Category does not exist");
      } else {
        System.out.println("Total sum for category " + category + ": " + df.format(categoryTransactionsRegister.getCategoryTransactionsSum(category)));
      }

      // fileReader.close();
    } catch (Exception e) {
        System.out.println(e);
        System.out.println("File not found");
    } 

    
    
  }

  /*
   * This method saves the categories and keywords from categoryKeywordsRegister to a csv file.
   */
  public static void saveCategoriesAndKeywordsToCsvFile() throws FileNotFoundException {
    PrintWriter fileWriter = null;
    try {
      // write to csv file
      fileWriter = new PrintWriter(categoryKeywordsFile);

      ArrayList<String> categoryKeys = new ArrayList<String>(categoryKeywordsRegister.getCategoryKeywords().keySet());
      categoryKeys.sort(String.CASE_INSENSITIVE_ORDER);

      fileWriter.println("Category:Keywords;");
      
      for (String category : categoryKeys) {
        fileWriter.print(category.substring(0, 1).toUpperCase() + category.substring(1) + ":");
        for (String tranaction : categoryKeywordsRegister.getCategoryKeywords().get(category)) {
          fileWriter.print(tranaction);
          if (categoryKeywordsRegister.getCategoryKeywords().get(category).indexOf(tranaction) != categoryKeywordsRegister.getCategoryKeywords().get(category).size() - 1) {
            fileWriter.print(",");
          }
        }
        fileWriter.print(";");
        fileWriter.println();
      }

      System.out.println("File written");
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
      return;
    } finally {
      if (fileWriter != null) {
        fileWriter.close();
      }
    }
  }

  public static void fetchAllData() throws FileNotFoundException {
    Scanner fileReader = null;

    try {
      fileReader = new Scanner(accountStatementFile);
      String line = fileReader.nextLine();
      String[] titles = line.split(";");
      int amountIndex = 0;
      int titleIndex = 0;
      for (int i = 0; i < titles.length; i++) {
        if (titles[i].toLowerCase().contains("amount") || titles[i].toLowerCase().contains("määrä")) {
          amountIndex = i;
        }
        if (titles[i].toLowerCase().contains("title") || titles[i].toLowerCase().contains("otsikko")) {
          titleIndex = i;
        }
      }
      while (fileReader.hasNext()) {
        line = fileReader.nextLine();
        String[] cells = line.split(";");
        String title = cells[titleIndex].toLowerCase();
        String amountNow = cells[amountIndex];
        String[] amountCells = amountNow.split(",");
        String totalAmountString = amountCells[0] + "." + amountCells[1];
        double amountLineDouble = Double.parseDouble(totalAmountString);

        ArrayList<String> categoryList = categoryKeywordsRegister.getCategories(title);
        for (String category : categoryList) {
          categoryTransactionsRegister.setTransaction(category, amountLineDouble);
        }

        categoryTransactionsRegister.addSum(amountLineDouble);
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    } finally {
      if (fileReader != null) {
        fileReader.close();
      }
    }
  }

  /*
   * This method reads categories and keywords from CategoryKeywords csv file and
   * adds them to categoryKeywordsRegister.
   * The structure of the csv file is as follows:
   * Category contains the word to which the keywords are related.
   * Keywords are comma-separated keywords related to the title of transaction in the account statement file.
   */
  public static void readCategoriesAndKeywordsFromCsvFile() throws FileNotFoundException {
    

    Scanner fileReader = null;
    
    try {
      
      fileReader = new Scanner(categoryKeywordsFile);
      String line = fileReader.nextLine();
      
      while (fileReader.hasNextLine()) {
        line = fileReader.nextLine().trim();
        line = line.replace(";", "");
       
        String[] parts = line.split(":");
        
        if (parts.length > 1) {
          String category = parts[0].trim().toLowerCase();
          String[] keywordArray = parts[1].toLowerCase().split(",");
  
          for (String keywordString : keywordArray) {
            categoryKeywordsRegister.addKeyword(category, keywordString);
          }
       }
      }
 
    
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
      
    } finally {
      if (fileReader != null) {
        fileReader.close();
      }
      categoryTransactionsRegister.updatetransactionList();
    }
     
  }
  
  public static void main(String[] args) throws InterruptedException, FileNotFoundException {
    System.out.println("Welcome to account statement analytics");
    System.out.println("This program helps you to categorize your account statement transactions");
    System.out.println("and to get a sum for each category");
    System.out.println("-------------------------------------------------");
    readCategoriesAndKeywordsFromCsvFile();
    fetchAllData();
    askUser();
    reader.close();
  }
}
