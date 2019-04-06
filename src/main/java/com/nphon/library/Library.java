package com.nphon.library;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * This class is used to perform some functionalities and load all books when it's initializing.
 */
public class Library {
    private static List<Book> books;

    public Library() {
        books = loadBooks();
        //books = this.buildBookDataSet();
    }

    /*
     * This method is used to get all the books in the library.
     */
    public List<Book> getAllBooks() {
        return books;
    }

    /*
     * Check to see if the book in the library exists by ISBN.
     *
     * @param strBookIsbn a book isbn
     * @return true if book exists otherwise false
     *
     */
    public boolean isBookAvailable(String strBookIsbn) {
        for (Book thisBook : books) {
            if (thisBook.getIsbn().equals(strBookIsbn)) {
                if (thisBook.isAvailability()) {
                    return true;
                }
            }
        }

        return false;
    }

    /*
     * This method is used to check out the book from the library.
     * @param book book that the user needs to check out.
     * @return Book if it's success otherwise null.
     *
     */
    public Book checkoutBook(Book book) {
        Book book1 = null;
        if (isBookAvailable(book.getIsbn())) {
            book.setAvailability(false);
            book1 = book;
        }
        return book1;
    }

    /*
     * This method is used to check in the book back to the library.
     * @param book the book that's the user needs to check back in.
     *
     */
    public void checkinBook(Book book) {
        for (Book thisBook : getAllBooks()) {
            if (thisBook.getIsbn().equals(book.getIsbn())) {
                thisBook.setAvailability(true);
            }
        }
    }

    /*
     * This method is used to get the book based on the ISBN.
     * @param isbn the book ISBN
     * @return Book if it match the ISBN otherwise null.
     */
    public Book getBookByIsbn(String isbn) {
        if (isbn != null) {
            for (Book book : books) {
                if (book.getIsbn().equals(isbn)) {
                    return book;
                }
            }
        }
        return null;
    }

    /*
     * This method is used to load the books to the library.
     * @return the list of books.
     */
    private List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Civil War", "Joe Schmoo", "11111", "pdf", "War", "Engish", true));
        books.add(new Book("Mockingbird", "Jane Doe", "22222", "Hardcopy", "Children", "English", true));
        books.add(new Book("Unknown", "John Smith", "33333", "pdf", "History", "Engish", true));
        books.add(new Book("It", "Steven King", "44444", "Hardcover", "Horror", "Engish", true));
        books.add(new Book("Lord of the Ring", "Unknown", "55555", "Hardcover", "Sci-Fi", "Engish", true));
        books.add(new Book("Stars War", "Unknown", "66666", "Hardcover", "Sci-Fi", "Engish", true));
        books.add(new Book("300", "Unknown", "77777", "Video", "History", "Engish", true));
        books.add(new Book("Cold Lake", "Jeff Carson", "B00PTR6YUY", "Kindle", "Thriller", "Engish", true));
        books.add(new Book("The Mamba Mentality: How I Play", "Kobe Bryant", "0374201234", "Hardcover", "Sport", "Engish", true));
        books.add(new Book("The Forgotten 500", "Gregory A. Freeman", "0451224957", "Paperback", "War", "Engish", true));
        books.add(new Book("The Last Punisher", "Kevin Lacz", "B0176M3PUG", "Kindle", "War", "Engish", true));
        books.add(new Book("Ghost Soldiers", "Hampton Sides", "038549565X", "Paperback", "War", "Engish", true));

        return books;
    }

    /*
     * This method is used to load the books based on the inputBooks.csv file.
     * @return the list of books.
     */
    private List<Book> buildBookDataSet() {
        List<Book> books = new ArrayList<>();
        try {
            //ClassPathResource resource = new ClassPathResource("classpath:inputBooks.csv");
            //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            File file = ResourceUtils.getFile("classpath:inputBooks.csv");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            books = bufferedReader
                    .lines()        // all lines
                    .skip(1)        // skip header
                    .map(line -> {
                        String[] arrayLine = line.split(",");
                        return new Book(arrayLine[0], arrayLine[1], arrayLine[2], arrayLine[3], arrayLine[4], arrayLine[5],
                                Boolean.valueOf(arrayLine[6]));
                    })              // map each line to Book
                    .collect(Collectors.toList());  // convert it into a List

            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }
}
