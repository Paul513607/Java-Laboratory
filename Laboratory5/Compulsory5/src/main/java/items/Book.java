package items;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.*;

public class Book extends Item {
    private Integer publishedYear;
    private List<String> authors = new ArrayList<>();
    private Integer numberOfChapters;

    public Book() {
        super();
    }

    public Book(String id, String name, String location, Integer publishedYear, List<String> authors, Integer numberOfChapters) {
        super(id, name, location);
        validatePublishedYear(publishedYear);
        this.publishedYear = publishedYear;
        this.authors = authors;
        this.numberOfChapters = numberOfChapters;
    }

    public Book(String id, String title, String location, Map<String, Object> tags, Integer publishedYear, List<String> authors, Integer numberOfChapters) {
        super(id, title, location, tags);
        this.publishedYear = publishedYear;
        this.authors = authors;
        this.numberOfChapters = numberOfChapters;
    }

    private static void validatePublishedYear(Integer publishedYear) throws IllegalArgumentException {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        if (publishedYear > year)
            throw new IllegalArgumentException("Published year can't be greater than the current year!");
    }

    public void addAuthor(String author) {
        authors.add(author);
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public Integer getNumberOfChapters() {
        return numberOfChapters;
    }

    public void setNumberOfChapters(Integer numberOfChapters) {
        this.numberOfChapters = numberOfChapters;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", tags=" + tags +
                ", publishedYear=" + publishedYear +
                ", authors=" + authors +
                ", numberOfChapters=" + numberOfChapters +
                '}';
    }
}
