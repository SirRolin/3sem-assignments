package week01.opgave6;


import java.security.InvalidParameterException;

public class Book {
    private String title;
    private String author;
    private int year;
    private int pages;
    private float rating;

    public Book(String title, String author, int year, int pages, float rating) throws InvalidParameterException {
        setTitle(title);
        setAuthor(author);
        setYear(year);
        setPages(pages);
        setRating(rating);
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) throws InvalidParameterException {
        if(year < 0){
            throw new InvalidParameterException("Must be a positive number!");
        }
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) throws InvalidParameterException {
        if(pages < 0){
            throw new InvalidParameterException("Must be a positive number!");
        }
        this.pages = pages;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) throws InvalidParameterException {
        if(rating > 5 || rating < 0){
            throw new InvalidParameterException("Must be between 0 and 5.");
        }
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("Title: %s - Author: %s - Year: %d - Pages: %d - Rating: %.2f", getTitle(), getAuthor(), getYear(), getPages(), getRating());
    }
}
