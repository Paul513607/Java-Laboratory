package commands;

import customexceptions.customcommandexceptions.CommandException;
import customexceptions.customcommandexceptions.InfoCommandException;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BasicContentHandlerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.*;

/** Class for parsing the metadata of a file and format it into a Catalog object. */
public class InfoCommand<T extends String, V extends String> implements Command<T, V> {
    private int statusCode = 1;
    String filePath;

    public InfoCommand(T args) {
        this.filePath = args;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public void setArgs(T args) {
        filePath = args;
    }

    @Override
    public void printArgs() {
        System.out.println(filePath);
    }

    @Override
    public void execute() throws CommandException {
        BasicContentHandlerFactory basicHandlerFactory = new BasicContentHandlerFactory(
                BasicContentHandlerFactory.HANDLER_TYPE.TEXT, -1);
        File fileToParse = new File(filePath);

        Parser parser = new AutoDetectParser();
        ContentHandler handler = basicHandlerFactory.getNewContentHandler();
        Metadata metadata = new Metadata();

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileToParse);
        } catch (FileNotFoundException e) {
            statusCode = 0;
            e.printStackTrace();
        }
        
        if (inputStream == null)
            throw new InfoCommandException(new Exception("File to parse not found!"));

        ParseContext parseContext = new ParseContext();
        try {
            parser.parse(inputStream, handler, metadata, parseContext);
            System.out.println("File content:");
            System.out.println(handler.toString());
        } catch (IOException | SAXException | TikaException e) {
            statusCode = 0;
            e.printStackTrace();
        }

        System.out.println("Metadata:");
        String[] metadataNames = metadata.names();
        for (String name : metadataNames)
            System.out.println(name + ": " + metadata.get(name));

    }

    @Override
    public V getCommandOutput() {
        if (statusCode == 1) {
            return (V) "Command successfully executed.";
        }
        return (V) "An error has occurred while listing the catalog content.";
    }
}
