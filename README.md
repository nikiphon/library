### Library Account System

Here are my the approach to design this system based on the requirement.

For this system, we have 4 main classes here: 
* Users - is used to check out, check in and lend books to other users.
* Book - has some attributes like ISBN which is used for searching and ‘available’ which is used to check whether the book is available to check out.  Most of the fields are immutable since we only need available field to track the status.
* Library - use to contains books and check out and check in methods which are used to change the available.
* MainLibrary - use to run the application and some other functionalities from the Scanner inputs.

We have 2 main option menus.  The first option is the Login and Exit which is used to find out the main user and exit the system..  The second option we have Display all books, Display all users, Check out book, Check in book, Lend books to other users, Display all the checked out information and Exit.
```
1. Display all books -  display all books in the library where we can use the ISBN.
2. Display all users - display all users in the system so we can select who to lend books to.
3. Check out book - is used to check out the books from the library.
4. Check in book - is used to check in the books to the library.
5. Lends books to other users - is used to lend books to other users in the system.
6. Display all the checked out information - display all users checked  out in the system.
7. Exit - exit the system.
```

The first 3 capabilities are kind of straight forward.  For the lending option, we need to add more functionalities.  For example, we need to load multiple users to the system and have the login option since we need to find out the lender.  We also need to calculate the borrower amount of books so that he can’t accept any more books that is greater than the N amount.  If the loaner tries to loan more books to the borrower, the system will alert the loaner.  We use an array of books instead of list since we already know the N amounts from the requirement. This force me to introduce the ‘index’ variable to count the number of books the user has.  The variable changes when either check out, check in and loan books to other users.

For testing,  we have 7 methods to test the check out functionalities.  There are only 2 tests for check in either to check in with no check out amount or check in is success. As for lending, I have 3 tests inside a method.  I do not have the test for Login since the login function is only needed username and no password.  I can have the users passwords but it’s going to be hardcoded in either the external file or the MainLibrary when application is started.

### Below are the assumptions:
```
1. User only visit one library.
2. Unable to switch to other users while in the system.
3. User class only has name and usename and no password.
4. No password need for the login since we only need the main user.
5. Add new attributes to the book for searching.
6. Add the amount of books to the loaner and borrower next to the name for clarity.
7. I’ve tried to use one Scanner in the application for all the input but no luck.
```

### How to run
* Clone the repository (https://github.com/nikiphon/library.git)
* Make sure you are using JDK 1.8 and Gradle
* You can build the project and run it with below method:
```
./gradlew build && java -jar build/libs/library-0.0.1-SNAPSHOT.jar
```
