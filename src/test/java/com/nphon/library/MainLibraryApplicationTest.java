package com.nphon.library;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MainLibraryApplicationTest {
    List<Book> books;
    List<User> users;
    User theUser;
    User borrower1;
    User borrower2;
    Library library;

    @Before
    public void beforeTestMethod() {
        library = new Library();
        users = Arrays.asList( new User("Niki", "niki"),
                new User("John", "john"),
                new User("Henry", "henry"),
                new User("Tom", "tom"),
                new User("Brady", "brady"));
        theUser = users.get(0);
        borrower1 = new User("Tom", "tom");
        borrower2 = new User("Brady", "brady");

        books = new ArrayList<>();
        books.add(new Book("Civil War", "Joe Schmoo", "11111", "pdf", "War", "Engish", true));
        books.add(new Book("Mockingbird", "Jane Doe", "22222", "Hardcopy", "Children", "English", true));
        books.add(new Book("Unknown", "John Smith", "33333", "pdf", "History", "Engish", true));
        books.add(new Book("Cold Lake", "Jeff Carson", "B00PTR6YUY", "Kindle", "Thriller", "Engish", true));
        books.add(new Book("The Mamba Mentality: How I Play", "Kobe Bryant", "0374201234", "Hardcover", "Sport", "Engish", true));
        books.add(new Book("The Forgotten 500", "Gregory A. Freeman", "0451224957", "Paperback", "War", "Engish", true));
    }

    @After
    public void afterTestMethod() {
        library = null;
        theUser = null;
        borrower1 = null;
        borrower2 = null;
        books = null;
    }

    @Test
    public void testCheckOutWrongISBNFail() {
        assertEquals(false, library.isBookAvailable("111111"));
    }

    @Test
    public void testCheckOutCorrectISBNSuccess() {
        assertEquals(true, library.isBookAvailable("33333"));
    }

    @Test
    public void testCheckOutNoAmountSuccess() {
        assertEquals(0, theUser.getBookIndex());
    }

    @Test
    public void testCheckOutMultipleAmountSuccess() {
        theUser.checkoutBook(books.get(0), library);
        theUser.checkoutBook(books.get(1), library);
        assertEquals(true, theUser.getBookIndex() > 1);
    }

    @Test
    public void testCheckOutOverLimitAmountFail() {
        theUser.checkoutBook(books.get(0), library);
        theUser.checkoutBook(books.get(1), library);
        theUser.checkoutBook(books.get(2), library);
        theUser.checkoutBook(books.get(3), library);
        theUser.checkoutBook(books.get(4), library);
        assertEquals(false, theUser.checkoutBook(books.get(5), library));
    }

    @Test
    public void testCheckOutThenLendToOtherUserSuccess() {
        theUser.checkoutBook(books.get(0), library);
        theUser.checkoutBook(books.get(1), library);

        theUser.lendBook(theUser, borrower1, books.get(0).getIsbn());

        // theUser should have 1 book now.
        assertEquals(1, theUser.getBookIndex());
    }

    @Test
    public void testCheckOutTheSameBookFail() {
        assertEquals(true, books.get(1).isAvailability());  // available is true
        library.checkoutBook(books.get(1));                          // then check out....available is set to false
        assertEquals(false, books.get(1).isAvailability());  // try to check out again should be false;
    }

    @Test
    public void testCheckInWithNoCheckOutAmountFail() {
        assertEquals(false, theUser.checkinBook(books.get(0), library));
    }

    @Test
    public void testCheckInSuccess() {
        theUser.checkoutBook(books.get(0), library);
        theUser.checkoutBook(books.get(1), library);
        assertEquals(true, theUser.checkinBook(books.get(0), library));
    }

    @Test
    public void testLendBooksWithNoBookToLendFail() {
        assertEquals(false, theUser.lendBook(theUser, borrower1, books.get(0).getIsbn()));
    }

    @Test
    public void testLendBooksToBorrowerSuccess() {
        theUser.checkoutBook(books.get(0), library);
        theUser.checkoutBook(books.get(1), library);
        theUser.checkoutBook(books.get(2), library);
        theUser.checkoutBook(books.get(3), library);
        assertEquals(4, theUser.getBookIndex());

        theUser.lendBook(theUser, borrower1, books.get(0).getIsbn());
        theUser.lendBook(theUser, borrower1, books.get(1).getIsbn());
        assertEquals(2, borrower1.getBookIndex());          // Borrower #1
        assertEquals(2, theUser.getBookIndex());            // The lender, amount is 2 now

        theUser.lendBook(theUser, borrower2, books.get(2).getIsbn()); // Borrower #1
        assertEquals(1, theUser.getBookIndex());            // The lender, amount is 1 now
    }
}
