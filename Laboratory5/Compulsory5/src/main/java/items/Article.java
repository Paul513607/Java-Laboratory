package items;

import java.util.*;

public class Article extends Item {
    private Integer publishedYear;
    private List<String> authors = new ArrayList<>();
    private String publisher;

    public Article() {
        super();
    }

    public Article(String id) {
        super(id);
    }

    public Article(String id, String name, String location, Integer publishedYear, List<String> authors, String publisher) throws IllegalArgumentException {
        super(id, name, location);
        validatePublishedYear(publishedYear);
        this.publishedYear = publishedYear;
        this.authors = authors;
        if (publisher != null)
            this.publisher = publisher;
        else
            this.publisher = "main author";
    }

    public Article(String id, String title, String location, Map<String, Object> tags, Integer publishedYear, List<String> authors, String publisher) {
        super(id, title, location, tags);
        this.publishedYear = publishedYear;
        this.authors = authors;
        this.publisher = publisher;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", tags=" + tags +
                ", publishedYear=" + publishedYear +
                ", authors=" + authors +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
