
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryTransactionsRegister {
  private Map<String, List<Double>> categoryTransactions; // Category, transactions
  private CategoryKeywordsRegister categoryKeywordsRegister; 
  private double sum; // Income - expenses
  private double incomes; 
  private double expenses;
  
  /*
   * Constructor
   */
  public CategoryTransactionsRegister() {
    this.categoryTransactions = new HashMap<>(); 
    this.categoryKeywordsRegister = MainProgram.categoryKeywordsRegister;
  }

  /*
   * Updates the transaction list categories with the categories from the categoryKeywordsRegister
   * and creates empty lists for them
   */
  public void updatetransactionList() {
    for (String category : categoryKeywordsRegister.getCategoryKeywords().keySet()) { // go through all categories in the categoryKeywordsRegister
      if (!this.categoryTransactions.containsKey(category)) { // if the category has not yet been added to the transaction list, add it in the form category, new ArrayList
        this.categoryTransactions.put(category, new ArrayList<Double>());
      }
    }
  }

  /*
   * Returns the transaction list for a category
   */
  public ArrayList<Double> getCategoryTransactions(String category) {
    category = category.toLowerCase();
    if (!this.categoryTransactions.containsKey(category)) { // if the transaction list does not contain the category, return an empty list
      return new ArrayList<Double>();
    }
    return (ArrayList<Double>) this.categoryTransactions.get(category); // otherwise, return the category's transaction list
  }

  /*
   * Returns the sum of transactions for a category
   */
  public double getCategoryTransactionsSum(String category) {
    category = category.toLowerCase();
    if (!this.categoryTransactions.containsKey(category)) { // if the transaction list does not contain the category, return 0
      return 0;
    }
    double sum = 0;
    for (double transaction : this.categoryTransactions.get(category)) { // go through the category's transactions and calculate their sum
      sum += transaction;
    }
    return sum;
  }

  /*
   * Returns the transaction list
   */
  public Map<String, List<Double>> getCategoryTransactions() {
    return this.categoryTransactions;
  }

  /*
   * Returns the sum of the transactions
   */
  public double getSum() {
    return this.sum;
  }

  /*
   * Returns the incomes from the transaction list
   */
  public double getIncomes() {
    return this.incomes;
  }

  /*
   * Returns the expenses
   */
  public double getExpenses() {
    return this.expenses;
  }

  /*
   * Resets the transaction list for each category
   * as well as incomes, expenses, and sum.
   * This is used whenever an account statement is read again.
   * to be updated later for better functionality
   */
  public void reset() {
    for (String category : this.categoryTransactions.keySet()) { // go through all categories in the transaction list and clear them
      this.categoryTransactions.get(category).clear();
    }
    this.sum = 0;
    this.incomes = 0;
    this.expenses = 0;
  }

  /*
   * Adds an transaction to the category's transaction list
   */
  public void setTransaction(String category, double transaction) {
    category = category.toLowerCase();
    if (!this.categoryTransactions.containsKey(category)) { // if the transaction list does not contain the category, return
      return;
    }
    this.categoryTransactions.get(category).add(transaction); // add the transaction to the category's transaction list
  }

  /*
   * Adds the transaction to the sum
   */
  public void addSum(double transaction) {
    this.sum += transaction;
    if (transaction < 0) {
      this.expenses += -transaction;
    } else {
      this.incomes += transaction;
    }
  }
}
