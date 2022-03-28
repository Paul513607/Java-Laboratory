package catalog;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/** A utility class to save the catalog to a file, or load one from a file. */
public class CatalogUtil {
    public static void save(Catalog catalog, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(filePath), catalog);
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public static Catalog load(String filePath) throws Exception {
        Catalog catalog;
        ObjectMapper objectMapper = new ObjectMapper();

        catalog = objectMapper.readValue(new File(filePath), Catalog.class);
        return catalog;
    }
}
