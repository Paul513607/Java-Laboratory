package main;

import algorithms.HopcroftKarpMaximumCardinalityMatchingAlg;
import algorithms.MinimumEdgeCoverAlg;
import catalog.Catalog;
import catalog.CatalogUtil;
import commands.*;
import customexceptions.SameIdItemExistsException;
import customexceptions.customcommandexceptions.CommandException;
import items.*;
import org.jgrapht.alg.matching.HopcroftKarpMaximumCardinalityBipartiteMatching;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Compulsory5 {
    private Catalog createCatalog() {
        Catalog catalog = new Catalog("myCatalog");
        Item book = new Book("knuth67", "The Art Of Programming", "d:/books/programming/tacp.ps", 1967, List.of("Donald E. Knuth"), 20);
        Item article = new Article("java17", "The Java Language Specification", "https://docs.oracle.com/javase/specs/jls/se17/html/index.html", 2021, List.of("James Gosling", "others"), "Oracle");

        try {
            catalog.add(book);
            catalog.add(article);
        } catch (IllegalArgumentException | SameIdItemExistsException err) {
            err.printStackTrace();
        }
        return catalog;
    }

    private void testCreateSave() {
        Catalog catalog = new Catalog("myCatalog");
        Item book = new Book("knuth67", "The Art Of Programming", "d:/books/programming/tacp.ps", 1967, List.of("Donald E. Knuth"), 20);
        Item article = new Article("java17", "The Java Language Specification", "https://docs.oracle.com/javase/specs/jls/se17/html/index.html", 2021, List.of("James Gosling", "others"), "Oracle");

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

    private void testAddCommand(Catalog inputCatalog) throws CommandException {
        // add command test
        System.out.println("Add command test:");
        Item book1 = new Book("aknuth50", "The Art Of Programming", "https://profs.info.uaic.ro/~acf/java/labs/lab_05.html", 1967, List.of("Donald E. Knuth"), 20);
        List<Object> addList = new ArrayList<>();
        addList.add(inputCatalog);
        addList.add(book1);
        Command<List<Object>, String> addCmd = new AddCommand<>(addList);
        addCmd.execute();
        inputCatalog.printCatalog();
        System.out.println(addCmd.getCommandOutput());
        System.out.println();
    }

    private void testListCommand(Catalog inputCatalog) throws CommandException{
        // list command test
        System.out.println("List command test:");
        Command<Catalog, String> listCmd = new ListCommand<>(inputCatalog);
        listCmd.execute();
        System.out.println(listCmd.getCommandOutput());
        System.out.println();
    }

    private void testSaveCommand(Catalog inputCatalog, String path) throws CommandException{
        // save command test
        System.out.println("Save command test:");
        List<Object> saveList = new ArrayList<>();
        saveList.add(inputCatalog);
        saveList.add(path);
        Command<List<Object>, String> saveCmd = new SaveCommand<>(saveList);
        saveCmd.execute();
        System.out.println(saveCmd.getCommandOutput());
        System.out.println();
    }

    private void testLoadCommand(String path) throws CommandException{
        // load command test
        System.out.println("Load command test:");
        Catalog loadedCatalog;
        Command<String, Catalog> loadCmd = new LoadCommand<>(path);
        loadCmd.execute();
        loadedCatalog = new Catalog(loadCmd.getCommandOutput());
        loadedCatalog.printCatalog();
        System.out.println();
    }

    private void testViewCommand(Item item) throws CommandException{
        // view command test
        System.out.println("View command test:");
        Command<Item, String> viewCmd = new ViewCommand<>(item);
        viewCmd.execute();
        System.out.println(viewCmd.getCommandOutput());
        System.out.println();
    }

    private void testReportCommand(Catalog inputCatalog) throws CommandException {
        // report command test
        System.out.println("Report command test:");
        Command<Catalog, String> reportCmd = new ReportCommand<>(inputCatalog);
        reportCmd.execute();
        System.out.println(reportCmd.getCommandOutput());
        System.out.println();
    }

    private void testInfoCommand(String path) throws CommandException {
        // info command test
        System.out.println("Info command test:");
        Command<String, String> infoCmd = new InfoCommand<>(path);
        infoCmd.execute();
        System.out.println(infoCmd.getCommandOutput());
        System.out.println();
    }

    private ItemGraph<Object, ItemLink> getItemGraph() {
        RandomItemGraphGenerator randomItemGraphGenerator = new RandomItemGraphGenerator();
        ItemGraph<Object, ItemLink> itemGraph = new ItemGraph<>(randomItemGraphGenerator.generateItemClassifications());
        // System.out.println("Graph before (number of nodes: " + itemGraph.getNumberOfNodes() + "):");
        itemGraph.printGraph();
        System.out.println();
        return itemGraph;
    }

    private void testMaximumMatchingAlg(ItemGraph<Object, ItemLink> itemGraph) {
        HopcroftKarpMaximumCardinalityMatchingAlg hopcroftKarpMaximumCardinalityMatchingAlg = new HopcroftKarpMaximumCardinalityMatchingAlg(itemGraph);
        System.out.println("Maximum cardinality matching (number of edges: " + hopcroftKarpMaximumCardinalityMatchingAlg.hopcroftKarpMaximumCardinalityMatchingAlgRun().size() + "):");
        hopcroftKarpMaximumCardinalityMatchingAlg.hopcroftKarpMaximumCardinalityMatchingAlgRun()
                .forEach(itemLink -> {
                    if (itemLink != null)
                        System.out.println(itemLink.getItem().getId() + " -> " + itemLink.getType());
                });
        System.out.println();
    }

    private void testMinimumEdgeCoverAlg(ItemGraph<Object, ItemLink> itemGraph) {
        HopcroftKarpMaximumCardinalityMatchingAlg hopcroftKarpMaximumCardinalityMatchingAlg = new HopcroftKarpMaximumCardinalityMatchingAlg(itemGraph);
        Set<ItemLink> maxCardMatching = hopcroftKarpMaximumCardinalityMatchingAlg.hopcroftKarpMaximumCardinalityMatchingAlgRun();

        MinimumEdgeCoverAlg minimumEdgeCoverAlg = new MinimumEdgeCoverAlg(itemGraph, maxCardMatching);
        System.out.println("Minimum edge cover (number of edges: " + minimumEdgeCoverAlg.minimumEdgeCoverAlgRun().size() + "):");
        minimumEdgeCoverAlg.minimumEdgeCoverAlgRun()
                .forEach(itemLink -> {
                    if (itemLink != null)
                        System.out.println(itemLink.getItem().getId() + " -> " + itemLink.getType());
                });
        System.out.println();
    }

    private static void testCommandsFromArgs(Compulsory5 app, String[] args) throws CommandException {
        Catalog catalog = app.createCatalog();
        for (int index = 0; index < args.length; ++index) {
            String command = args[index].toLowerCase();
            switch (args[index]) {
                case "add":
                    app.testAddCommand(catalog);
                    break;
                case "list":
                    app.testListCommand(catalog);
                    break;
                case "save":
                    app.testSaveCommand(catalog, "/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Java-Laboratory/Laboratory5/Compulsory5/src/main/resources/formatted-catalog/catalog.json");
                    break;
                case "load":
                    app.testLoadCommand("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Java-Laboratory/Laboratory5/Compulsory5/src/main/resources/formatted-catalog/catalog.json");
                    break;
                case "view":
                    app.testViewCommand(catalog.getItemList().get(1));
                    break;
                case "report":
                    app.testReportCommand(catalog);
                    break;
                case "info":
                    app.testInfoCommand("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Java-Laboratory/Laboratory5/Compulsory5/src/main/resources/formatted-catalog/catalog.json");
            }
        }
    }

    public static void main(String[] args) {
        Compulsory5 app = new Compulsory5();
        // app.testCreateSave();
        // app.testLoadCatalog();

        if (args.length >= 1) {
            try {
                testCommandsFromArgs(app, args);
            } catch (CommandException e) {
                e.printStackTrace();
            }
        }

        ItemGraph<Object, ItemLink> itemGraph = app.getItemGraph();
        app.testMaximumMatchingAlg(itemGraph);
        app.testMinimumEdgeCoverAlg(itemGraph);
    }
}
