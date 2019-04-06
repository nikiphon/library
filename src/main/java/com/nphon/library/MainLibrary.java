package com.nphon.library;

import java.util.*;

public class MainLibrary {
    private static List<User> users = Arrays.asList(new User("John", "john"),
            new User("Niki", "niki"),
            new User("Henry", "henry"),
            new User("Tom", "tom"),
            new User("Brady", "brady"),
            new User("Jack", "jack"),
            new User("Daniel", "daniel"),
            new User("Hennessey", "hennessey"),
            new User("Johnny", "johnny"),
            new User("Blue", "blue"));

    private static HashMap<User, List<Book>> userListBook = new HashMap<>();
    private static List<User> borrowerList = new ArrayList<>();
    private static Library library = new Library();
    private static User theUser;

    private static Scanner firstInput = new Scanner(System.in);
    private static Scanner userNameInput = new Scanner(System.in);
    private static Scanner choiceInput = new Scanner(System.in);
    private static Scanner userInput = new Scanner(System.in);
    private static Scanner loanInput = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("***************************************************************************");
        System.out.println("                Welcome to Library Management System                       ");
        System.out.println("***************************************************************************");
        System.out.println("Please select from the following options:\n");

        viewFirstOptionMenu();
    }

    public static void viewFirstOptionMenu() {
        int firstChoice = 0;
        while (firstChoice != 2) {
            System.out.println("1: Login");
            System.out.println("2: Exit");
            System.out.println("Enter Choice: ");
            firstChoice = firstInput.nextInt();

            switch (firstChoice) {
                case 1:
                    System.out.println("Enter Username: ");
                    String username = userNameInput.nextLine();
                    theUser = getUserByName(username);
                    if (theUser != null) {
                        viewOptionMenu();
                    } else {
                        System.out.println("The username is not in the system.  Please try again.");
                    }
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        }
    }

    public static void viewOptionMenu() {
        int iChoice = 0;
        while (iChoice != 7) {
            System.out.println("***************************************************************************");
            System.out.println("USER: " + theUser.getName() + " (" + theUser.getBookIndex() + ")");
            System.out.println("1: Display all books");
            System.out.println("2: Display all users");
            System.out.println("3: Check out book");
            System.out.println("4: Check in book");
            System.out.println("5: Lend books to other users");
            System.out.println("6: Display all checked out information");
            System.out.println("7: Exit");
            iChoice = choiceInput.nextInt();

            switch (iChoice) {
                case 1: // Display all books in the library.
                    displayAllBooks();
                    break;
                case 2: // Display all users in the system.
                    displayAllUsers();
                    break;
                case 3: // Check out books
                    displayCheckOutOption();
                    break;
                case 4: // Check in books
                    displayCheckInOption();
                    break;
                case 5: // Lend books to the other user
                    displayLendBooksOption();
                    break;
                case 6: // Display all checked out from all users
                    displayAllCheckOutInformation();
                    break;
                case 7: // Exit the system.
                    System.exit(0);
                    break;
                default:
            }
        }
    }

    public static void displayAllBooks() {
        System.out.println("***************************************************************************");
        System.out.println("   TITLE" + "\tAUTHOR" + "\tISBN" + "\tFORMAT" + "\tGENRE" + "\tLANGUAGE" + "\tAVAILABILITY");
        System.out.println("----------------------------------------------------------------------------");
        int listBookIndex = 1;
        for (Book book : library.getAllBooks()) {
            System.out.println(listBookIndex + ". " + book);
            listBookIndex++;
        }
        System.out.println("***************************************************************************\n");

        viewOptionMenu();
    }

    public static void displayAllUsers() {
        System.out.println("***************************************************************************");
        System.out.println("   USER" + "\tUSERNAME");
        int displayUserIndex = 0;
        for (User user : users) {
            System.out.println(displayUserIndex + ". " + user);
            displayUserIndex++;
        }
        System.out.println("***************************************************************************\n");

        viewOptionMenu();
    }

    public static void displayCheckOutOption() {
        System.out.println("\nPlease enter ISBN to check out: ");
        String isbn = userInput.nextLine(); //read the ISBN from input

        Book book = library.getBookByIsbn(isbn);
        if (book != null && book.isAvailability()) {
            boolean getCheckout = theUser.checkoutBook(book, library);

            if (getCheckout) {
                System.out.println("You have successfully checked out the book!");
            }
        } else {
            System.out.println("No book for ISBN " + isbn + " found or it's not available.");
        }

        viewOptionMenu();
    }

    public static void displayCheckInOption() {
        if (theUser.getBookIndex() != 0) {
            System.out.println("\nPlease enter ISBN to be checked in: ");
            String strBookCheckInIsbn = userInput.nextLine();
            if (theUser.isBookAvailable(strBookCheckInIsbn)) {
                Book bookCheckIn = library.getBookByIsbn(strBookCheckInIsbn);
                if (bookCheckIn != null) {
                    boolean isCheckIn = theUser.checkinBook(bookCheckIn, library);
                    if (isCheckIn) {
                        System.out.println("The book ISBN '" + strBookCheckInIsbn + "' has been checked in!");
                    }
                }
            } else {
                System.out.println("The book ISBN '" + strBookCheckInIsbn + "' is not in the library.");
            }
        } else {
            System.out.println("You have nothing to check in.\n");
        }

        viewOptionMenu();
    }

    public static void displayLendBooksOption() {
        if (theUser.getBookIndex() != 0) {
            System.out.println("Please enter the borrower: ");
            String strName = userInput.nextLine();
            User borrower = getUserByName(strName);
            if (borrower != null) {
                System.out.println("Please enter how many books: ");
                int totalBooks = userInput.nextInt();
                if (totalBooks <= theUser.getBookIndex()) {
                     if (totalBooks + borrower.getBookIndex() > theUser.getBooks().length) {
                        System.out.println("Borrower will have over the maximum (" + borrower.getBooks().length + ") amount of books.  " +
                                "Please enter less than or equal to " + (borrower.getBooks().length - borrower.getBookIndex()) + " amount: ");
                    } else {
                        int theIndex = 1;
                        while (totalBooks > 0 && totalBooks >= theIndex) {
                            System.out.println(theIndex + ". Please enter the ISBN to be loaned or 0 back to main menu: ");
                            String loanIsbn = loanInput.nextLine();
                            if (!loanIsbn.equalsIgnoreCase("0")) {
                                if (theUser.isBookAvailable(loanIsbn)) {
                                    boolean isLendSucess = theUser.lendBook(theUser, borrower, loanIsbn);
                                    theIndex++;
                                }
                            } else {
                                viewOptionMenu();
                            }
                        }
                    }
                } else {
                    System.out.println("You entered more books than you have.  Please try again.");
                }
                removeOldBorrowerThenAddNewBorrower(borrower);
            } else {
                System.out.println("Unable to find " + strName + " in the user list.\n");
            }
        } else {
            System.out.println("You don't have any book to loan.\n");
        }
        viewOptionMenu();
    }

    public static User getUserByName(String username) {
        if (username != null) {
            for (User user : users) {
                if (user.getName().equalsIgnoreCase(username))
                    return user;
            }
        }
        return null;
    }

    public static void displayAllCheckOutInformation() {
        boolean isBookCheckout = false;
        if (theUser.getBookIndex() > 0) {
            theUser.displayCheckoutStatus();        // The main user
            isBookCheckout = true;
        }
        for (User borrower : borrowerList) {
            borrower.displayCheckoutStatus();
            isBookCheckout = true;
        }

        if (!isBookCheckout) {
            System.out.println("There is no book checking out.\n");
        }
    }

    public static void removeOldBorrowerThenAddNewBorrower(User borrower) {
        boolean isBorrowerExist = false;
        int borrowerIndex = 0;
        for (User thisBorrower : borrowerList) {
            if (thisBorrower.getUsername().equalsIgnoreCase(borrower.getUsername())) {
                isBorrowerExist = true;
               break;
            }
            borrowerIndex++;
        }
        if (isBorrowerExist) {
            borrowerList.remove(borrowerIndex); // remove the old borrower
        }

        borrowerList.add(borrower);
    }
}
