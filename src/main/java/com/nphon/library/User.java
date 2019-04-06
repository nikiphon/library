package com.nphon.library;

/*
 * This class is used for the users of the library.
 */
public class User {
    private final String name;
    private final String username;
    private final Book books[] = new Book[5];  // Store book objects, set to 5 amount.
    private int index = 0;                     // Count the book that check out or check in to the book array

    public User(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public Book[] getBooks() {
        return books;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public void setBookIndex(int bookIndex) {
        this.index = bookIndex;
    }

    public int getBookIndex() {
        return index;
    }

    /*
     * This method is used to check out the book from the library.
     * @param book the book that's needed to check out.
     * @param library the library system
     * @return true if it's success otherwise false;
     */
    public boolean checkoutBook(Book book, Library library) {
        boolean isCheckout = false;
        if (library.isBookAvailable(book.getIsbn())) {
            if (index < books.length) {
                Book newBook = library.checkoutBook(book);
                books[index++] = newBook;
                isCheckout = true;
            }
        }

        return isCheckout;
    }

    public int getBookIndex(String bookIsbn) {
        int bookIndex = 0;
        for (Book thisBook : books) {
            if (thisBook != null && thisBook.getIsbn().equalsIgnoreCase(bookIsbn)) {
                break;
            }
            bookIndex++;
        }
        return bookIndex;
    }

    /*
     * This method is used to check in the book to the library.
     * @param book the book that's needed to check out.
     * @param library the library system
     * @return true if it's success otherwise false;
     */
    public boolean checkinBook(Book book, Library library) {
        boolean isCheckIn = false;
        boolean thisBookIndex = false ;
        int bookIndex = 0;
        for (Book thisBook : books) {
            if (thisBook != null && thisBook.getIsbn().equalsIgnoreCase(book.getIsbn())) {
                thisBookIndex = true;
                break;
            }
            bookIndex++;
        }

        if (thisBookIndex && books[bookIndex] != null) {
            library.checkinBook(books[bookIndex]);
            books[bookIndex--] = null;
            index--;
            isCheckIn = true;
        }
        return isCheckIn;
    }

    /*
     * This method is used to check to see if the book is available in the users checked out.
     * @param strIsbn the book ISBN
     * @return true if the book exists otherwise false
     */
    public boolean isBookAvailable(String strIsbn) {
        if (strIsbn != null) {
            for (Book book : books) {
                if (book != null && strIsbn.equalsIgnoreCase(book.getIsbn())) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * This method is used to lend book to other users in the system
     * @param borrower the other user in the system
     * @param strIsbn the book ISBN
     * @return true if it's success otherwise false
     */
    public boolean lendBook(User theUser, User borrower, String strIsbn) {
        boolean isLendSuccess = false;
        if (isBookAvailable(strIsbn)) {
            int bookIndex = this.getBookIndex(strIsbn);
            Book loanBook = books[bookIndex];
            //Book loanBook = theUser.getBooks()[bookIndex]
            int loanIndex = borrower.getBookIndex();
            borrower.getBooks()[loanIndex++] = loanBook;       // Add book to borrower
            borrower.setBookIndex(loanIndex);                  // Change the borrower book index

            books[bookIndex] = null;                            // Clear book from loaner
            index--;
            isLendSuccess = true;
        }

        return isLendSuccess;
    }

    /*
     * This method is used to display the checked out status.
     */
    public void displayCheckoutStatus() {
        System.out.println("USER: " + this.getName() + " (" + this.getBookIndex() + ")");
        System.out.println("***************************************************************************");
        System.out.println("   TITLE" + "\tAUTHOR" + "\tISBN" + "\tFORMAT" + "\tGENRE" + "\tLANGUAGE" + "\tAVAILABILITY");
        System.out.println("---------------------------------------------------------------------------");
        int printIndex  = 1;
        for (Book book : books) {
           if (book != null) {
               System.out.println(printIndex + ". " + book);
               printIndex++;
           }
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return name + " " + username;
    }
}
