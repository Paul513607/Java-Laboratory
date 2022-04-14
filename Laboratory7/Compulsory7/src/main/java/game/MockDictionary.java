package game;

import lombok.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/** A regular dictionary of words. */
@Getter(onMethod_ = {@Synchronized})
@Setter(onMethod_ = {@Synchronized})
@ToString
@EqualsAndHashCode
public class MockDictionary implements Dictionary {
    private Set<String> words = new TreeSet<>();

    public MockDictionary() {
    }

    @Override
    public void readDictionaryFromFile(String filePath) throws FileNotFoundException {
        File dictionaryFile = new File(filePath);

        Scanner scanner = new Scanner(dictionaryFile);

        while (scanner.hasNextLine()) {
            String currLine = scanner.nextLine();
            currLine = currLine.strip();

            int indexOfSpace = currLine.indexOf(' ');
            int prevIndex = 0;
            while (indexOfSpace != -1) {
                String currWord = currLine.substring(prevIndex, indexOfSpace);
                if (currWord.length() > 7)
                    return;
                words.add(currWord);
                currLine = currLine.substring(indexOfSpace + 1);

                prevIndex = indexOfSpace;
                indexOfSpace = currLine.indexOf(' ');
            }

            int tempIndex = currLine.indexOf('/');
            if (tempIndex != -1)
                currLine = currLine.substring(0, tempIndex);
            tempIndex = currLine.indexOf('\'');
            if (tempIndex != -1)
                currLine = currLine.substring(0, tempIndex);

            currLine = currLine.toUpperCase();
            words.add(currLine);
        }
    }

    @Override
    public boolean isWord(String wordMaybe) {
        return words.contains(wordMaybe);
    }

    @Override
    public synchronized void removeWord(String word) {
        words.remove(word);
    }

    @Override
    public List<String> searchForGivenPrefix(String prefix) {
        return words.parallelStream()
                .filter(word -> (word.startsWith(prefix)))
                .collect(Collectors.toList());
    }
}
