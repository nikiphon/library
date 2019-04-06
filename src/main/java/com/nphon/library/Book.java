package com.nphon.library;

/*
 * This class is used for data model and it stores only information.
 */
public class Book {
    private final String title;
    private final String author;
    private final String isbn;
    private final String format;
    private final String genre;
    private final String language;
    private boolean availability;

    public Book(String title, String author, String isbn, String format, String genre, String language, boolean availability) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.format = format;
        this.genre = genre;
        this.language = language;
        this.availability = availability;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() { return isbn; }

    public String getFormat() {
        return format;
    }

    public String getGenre() {
        return genre;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return title + "\t" + author + "\t" + isbn + "\t" + genre + "\t" + language + "\t" + availability;
    }
}
