
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryKeywordsRegister {
  private Map<String, List<String>> categoryKeywords;

  /*
   * Constructor
   */
  public CategoryKeywordsRegister() {
    this.categoryKeywords = new HashMap<>();
  }

  /*
   * Returns categoryKeywords
   */
  public Map<String, List<String>> getCategoryKeywords() {
    return this.categoryKeywords;
  }

  /*
   * Adds a categoryTitle to the categoryKeywords list if it is not already present
   */
  public void addCategory(String category) {
    category = category.toLowerCase();
    if (!this.categoryKeywords.containsKey(category)) { // if categoryKeywords does not contain the category,
                                                           // add it, along with a new empty list
      this.categoryKeywords.put(category, new ArrayList<String>());
    }
  }

  /*
   * Adds an keyword to the categoryKeywords list if it is not already present
   */
  public void addKeyword(String category, String keyword) {
    category = category.toLowerCase();
    keyword = keyword.toLowerCase();
    if (!this.categoryKeywords.containsKey(category)) { // if categoryKeywords does not contain the category,
                                                           // add it, along with a new empty list
      this.categoryKeywords.put(category, new ArrayList<String>());
    }

    if (this.categoryKeywords.get(category).contains(keyword)) { // if the category already contains
                                                                         // the keyword, do not add it again
      return;
    }

    this.categoryKeywords.get(category).add(keyword);
  }
  
  /*
   * Checks if a category exists
   */
  public boolean doesCategoryExist(String category) {
    category = category.toLowerCase();
    return this.categoryKeywords.containsKey(category);
  }

  /*
   * Checks if an keyword exists
   */
  public boolean doesKeywordExist(String category, String keyword) {
    category = category.toLowerCase();
    keyword = keyword.toLowerCase();
    return this.categoryKeywords.get(category).contains(keyword);
  }

  public boolean isKeywordInList(String category) {
    category = category.toLowerCase();
    
    if (this.categoryKeywords.containsKey(category)) {
        return true;
    }
    return false;
  }

  /*
   * Returns a list containing the exact category for an keyword, or a list of categories that are suitable for the keyword
   * exact match is preferred, next is the category that contains the most common words with the keyword
   * equals > contains
   */
  public ArrayList<String> getCategories(String keyword) {
    ArrayList<String> suitableCategories = new ArrayList<>();
    ArrayList<String> exactMatchCategories = new ArrayList<>();
    ArrayList<String> commonWordCategories = new ArrayList<>();

    if (keyword == null) {
        return suitableCategories;
    }
    keyword = keyword.toLowerCase();
    String bestCategory = null;
    int mostCommonWords = -1;

    // Loop through all categories and their titles
    for (Map.Entry<String, List<String>> entry : this.categoryKeywords.entrySet()) {
        String category = entry.getKey();
        List<String> titles = entry.getValue();

        for (String title : titles) {
            title = title.toLowerCase();

            // If keyword exactly matches the category's title, add the category to the list
            if (keyword.equals(title)) {
                exactMatchCategories.add(category);
                // continue to the next category, because the exact match can fit into multiple categories
                continue;

            // otherwise, the keyword is not exactly the same but contains the category title
            } else if (keyword.contains(title)) {
              int commonWordCount = countCommonWords(keyword, title); // calculate the number of common words
              if (commonWordCount > mostCommonWords) {
                  mostCommonWords = commonWordCount;
                  bestCategory = category; // set the best category if the number of common words is higher than the previous best
              } else if (commonWordCount == mostCommonWords) {
                  commonWordCategories.add(category); // add the category to the list of suitable categories if the number of common words is the same as the previous best
              } 

          }

        }
    }
    
    // add the exact match categories to the list of suitable categories
    suitableCategories.addAll(exactMatchCategories);
    
    // add the common word categories to the list of suitable categories if it is not empty
    if (!commonWordCategories.isEmpty()) {
      suitableCategories.addAll(commonWordCategories);
    }

    // add the best category to the list of suitable categories if it is not null
    if (bestCategory != null) {
        suitableCategories.add(bestCategory);
    }
    
    return suitableCategories;
}

/*
 *  Counts the number of common words between two titles
 */
private int countCommonWords(String keyword, String accountstatementTitle) {
  String[] keywords = keyword.split("\\s+"); // Split words based on whitespace
  String[] accountstatementTitleWords = accountstatementTitle.split("\\s+"); // Split words based on whitespace
  int matchCount = 0;
  for (String word : keywords) {
      for (String titleWord : accountstatementTitleWords) {
          if (word.equals(titleWord)) {
              matchCount++;
              break; // When a match is found, no need to continue checking this word
          }
      }
  }
  return matchCount;
}



}
