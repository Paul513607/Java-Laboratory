package game.prefixtreeutil;

import com.fasterxml.jackson.databind.ObjectMapper;
import game.PrefixTreeDictionary;

import java.io.File;
import java.io.IOException;

/** Class for saving/loading a prefix tree dictionary. */
public class PrefixTreeManager {

    public PrefixTreeManager() {
    }

    public void saveDictionary(PrefixTreeDictionary treeDictionary) {
        File dictionaryFile = new File("src/main/resources/dictionary-words/dictionary/prefix-dictionary.json");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(dictionaryFile, treeDictionary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PrefixTreeDictionary loadDictionary() {
        File dictionaryFile = new File("src/main/resources/dictionary-words/dictionary/prefix-dictionary.json");

        PrefixTreeDictionary treeDictionary = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            treeDictionary = objectMapper.readValue(dictionaryFile, PrefixTreeDictionary.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return treeDictionary;
    }
}
