package com.nphon.library;

public class User {
    private final String name;
    private final String username;
    private final Book books[] = new Book[5];
    private int index = 0;

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

    public boolean checkoutBook(Book book, Library library) {
        boolean isCheckout = false;
        if (library.isBookAvailable(book.getIsbn())) {
            if (index < books.length) {
                Book newBook = library.checkoutBook(book);
                books[index++] = newBook;
                isCheckout = true;
            } else {
                System.out.println("**********You have reached your checked out amount " + books.length + ".**********");
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

    public boolean lendBook(User theUser, User borrower, String strIsbn) {
        boolean isLendSuccess = false;
        if (isBookAvailable(strIsbn)) {
            int bookIndex = this.getBookIndex(strIsbn);
            Book loanBook = books[bookIndex];
            int loanIndex = borrower.getBookIndex();
            borrower.getBooks()[loanIndex++] = loanBook;       // Add book to borrower
            borrower.setBookIndex(loanIndex);

            books[bookIndex] = null;                            // Clear book from loaner
            index--;
            System.out.println("You lend book with ISBN '" + strIsbn + "' to " + borrower.getName() + ".");
            isLendSuccess = true;
        }

        return isLendSuccess;
    }

    public void displayCheckoutStatus() {
        //if (index > 0) {
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

    public Book getBookByIsbn(String strIsbn) {
        if (strIsbn != null) {
            for (Book book : books) {
                if (book != null && strIsbn.equalsIgnoreCase(book.getIsbn())) {
                    return book;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name + " " + username;
    }
}
