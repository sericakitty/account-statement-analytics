# Account Statement Analytics - Project

With this application, you can scan your account statement file and analyze your expenses and incomes.

## Initializing
In the root directory, you should have two files:

- **AccountStatement.csv**  
  This file includes your bank's account statement as a CSV file.
  The file's first line should include, among other fields, the following words:

<pre>
Amount;Title;
</pre>

Or in finnish:
<pre>
Määrä;Otsikko;
</pre>

Here's example: EntryDate;Amount;Payer;Payee;Name;Title;ReferenceNumber;Currency;


- **CategoryKeywords.csv**  
This file includes categories and a list which contains keywords used to search for the titles of the account transactions.  
The first line is as follows:
<pre>
Category:Keywords;
</pre>
Here is what they mean:
<pre>
Category: (title of category, e.g., "Restaurant")
Keywords: (list of transaction title keywords, e.g., [mcdonalds, pizzahut])
</pre>

## Features
The program offers the following features:

1. **List Categories and Keywords:** Displays all categories and associated keywords, derived from either user inputs or the `CategoryKeywords.csv` file.
2. **Add Category:** Allows adding a new category to the CategoryKeywordsRegistry.
3. **Add Keyword:** You can add a keyword to an existing category in the CategoryKeywordsRegistry. If the category does not exist, this function allows creating a new category.
4. **Fetch All Categories:** Fetches all transactions from the `AccountStatement.csv` file and associates them with the corresponding categories in the CategoryKeywordsRegistry.
5. **Fetch Sum for a Single Category:** Fetches and displays the sum for a selected category from the `AccountStatement.csv` file and associates it with the corresponding category in the CategoryKeywordsRegistry.
6. **Save Category and List of Keywords to the File:** Saves the categories and their keywords from the CategoryKeywordsRegistry to the `CategoryKeywords.csv` file.

## Instructions
Select a function from the main menu by entering the desired function number and pressing Enter. You can reprint the menu at any time by pressing the Enter key without entering a number. The program execution ends by entering `0`.