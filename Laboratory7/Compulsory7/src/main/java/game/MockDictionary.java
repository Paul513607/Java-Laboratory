package game;

/** A regular dictionary of words. */
public class MockDictionary implements Dictionary {
    @Override
    public boolean isWord(String str) {
        return true;
    }
}
