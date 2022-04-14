package game;

import java.io.FileNotFoundException;
import java.util.List;

/** Interface for a Dictionary of words. */
public interface Dictionary {
    void readDictionaryFromFile(String filePath) throws FileNotFoundException;
    boolean isWord(String str);
    void removeWord(String word);
    List<String> searchForGivenPrefix(String prefix);
}
