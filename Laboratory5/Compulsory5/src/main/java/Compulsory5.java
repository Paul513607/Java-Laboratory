import catalog.*;
import items.*;
import customexceptions.*;

import java.io.IOException;
import java.util.List;

public class Compulsory5 {
    private void testCreateSave() {
        Catalog catalog = new Catalog("myCatalog");
        var book = new Book("knuth67", "The Art Of Programming", "d:/books/programming/tacp.ps", 1967, List.of("Donald E. Knuth"), 20);
        var article = new Article("java17", "The Java Language Specification", "https://docs.oracle.com/javase/specs/jls/se17/html/index.html", 2021, List.of("James Gosling", "others"), "Oracle");

        try {
            catalog.add(book);
            catalog.add(article);
        } catch (IllegalArgumentException | SameIdItemExistsException err) {
            err.printStackTrace();
        }
        // catalog.findItemById("java17").addReference("Programming", catalog.getItemList().get(0));

        System.out.println(catalog.getName());
        catalog.getItemList().stream()
                        .forEach(System.out::println);
        System.out.println();

        CatalogUtil.save(catalog, "/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Java-Laboratory/Laboratory4/catalog.json");
    }

    private void testLoadCatalog() {
        Catalog catalog = new Catalog();
        try {
            catalog = CatalogUtil.load("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Java-Laboratory/Laboratory4/catalog.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(catalog.getName());
        catalog.getItemList().stream()
                .forEach(System.out::println);
        System.out.println();
    }

    public static void main(String[] args) {
        Compulsory5 app = new Compulsory5();
        app.testCreateSave();
        app.testLoadCatalog();
    }
}
