package commands;

import catalog.Catalog;
import customexceptions.customcommandexceptions.CommandException;
import customexceptions.customcommandexceptions.ReportCommandException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/** Class for creating a report html file based on the given Catalog object. */
public class ReportCommand<T extends Catalog, V extends String> implements Command<T, V> {
    private int statusCode = 1;
    private T catalog;
    private String outputString;

    public ReportCommand(T catalog) {
        this.catalog = catalog;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public void setArgs(T catalog) {
        this.catalog = catalog;
    }

    @Override
    public void printArgs() {
        System.out.println("Catalog name: " + catalog.getName() + "\nCatalog content:");
        catalog.getItemList().stream()
                .forEach(System.out::println);
    }

    private File createHtmlFile(String path) {
        File newHtmlFile = new File(path);
        try {
            newHtmlFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newHtmlFile;
    }

    private Configuration getConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
        try {
            configuration.setDirectoryForTemplateLoading(new File("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Java-Laboratory/Laboratory5/Compulsory5/src/main/resources/templates"));
        } catch (IOException e) {
            statusCode = 0;
            e.printStackTrace();
        }
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);
        configuration.setFallbackOnNullLoopVariable(false);
        configuration.setAPIBuiltinEnabled(true);

        return configuration;
    }

    private void openReport(File reportFile) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(reportFile.toURI());
        } catch (IOException e) {
            statusCode = 0;
            e.printStackTrace();
        }
    }

    @Override
    public void execute() throws CommandException {
        // set up the configuration
        Configuration configuration = getConfiguration();

        // create the data module
        Map<String, Object> root = new HashMap<>();
        root.put("name", catalog.getName());
        root.put("items", catalog.getItemList());

        // create the template
        Template template = null;
        try {
            template = configuration.getTemplate("catalog.ftlh");
        } catch (IOException e) {
            statusCode = 0;
            e.printStackTrace();
        }

        // merge the template with the data module
        File reportFile = createHtmlFile("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Java-Laboratory/Laboratory5/Compulsory5/src/main/resources/html-reports/index.html");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(reportFile);
        } catch (FileNotFoundException e) {
            statusCode = 0;
            e.printStackTrace();
        }

        if (fileOutputStream == null) {
            statusCode = 0;
            throw new ReportCommandException(new Exception("The file output stream for the template is null!"));
        }

        OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
        try {
            template.process(root, writer);
            fileOutputStream.close();
        } catch (TemplateException | IOException e) {
            statusCode = 0;
            e.printStackTrace();
        }

        // open the report file
        openReport(reportFile);
    }

    @Override
    public V getCommandOutput() {
        if (statusCode == 1) {
            return (V) "Command successfully executed.";
        }
        return (V) "An error has occurred while listing the catalog content.";
    }
}
