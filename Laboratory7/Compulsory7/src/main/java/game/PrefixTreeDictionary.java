package game;

import game.prefixtreeutil.PrefixTreeEdge;
import game.prefixtreeutil.PrefixTreeNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PrefixTreeDictionary implements Dictionary {
    List<PrefixTreeNode> nodes = new ArrayList<>();
    Set<PrefixTreeEdge> edges = new HashSet<>();

    @Override
    public void readDictionaryFromFile(String filePath) throws FileNotFoundException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1000);

        nodes.add(new PrefixTreeNode(""));
        for (Character ch = 'A'; ch <= 'Z'; ++ch) {
            PrefixTreeNode newNode = new PrefixTreeNode(ch.toString());
            nodes.add(newNode);
            edges.add(new PrefixTreeEdge(nodes.get(0), newNode, ch));
        }

        File dictionaryFile = new File(filePath);

        Scanner scanner = new Scanner(dictionaryFile);
        while (scanner.hasNextLine()) {
            String currWord = scanner.nextLine();
            currWord = currWord.strip();

            int tempIndex = currWord.indexOf('/');
            if (tempIndex != -1)
                currWord = currWord.substring(0, tempIndex);
            tempIndex = currWord.indexOf('\'');
            if (tempIndex != -1)
                currWord = currWord.substring(0, tempIndex);

            currWord = currWord.toUpperCase();
            String finalCurrWord = currWord;
            executor.submit(() -> addNewWord(finalCurrWord));
        }

        try {
            executor.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    private synchronized void addNewWord(String word) {
        if (containsString(word)) {
            nodes.get(getIndexForGivenPrefix(word)).setWord(true);
            return;
        }

        int prefixIndex = 1;
        String prefix = word.substring(0, prefixIndex);
        int currPrefixIndex = getIndexForGivenPrefix(prefix);
        int prevIndex = currPrefixIndex;
        while (currPrefixIndex != -1) {
            prefixIndex++;
            prefix = word.substring(0, prefixIndex);
            prevIndex = currPrefixIndex;
            currPrefixIndex = getIndexForGivenPrefix(prefix);
        }

        for (int missingIndex = prefixIndex - 1; missingIndex < prefix.length(); ++missingIndex) {
            char edgeValue = prefix.charAt(missingIndex);
            PrefixTreeNode newNode = new PrefixTreeNode(prefix.substring(0, missingIndex + 1));

            edges.add(new PrefixTreeEdge(nodes.get(prevIndex), newNode, edgeValue));
            nodes.add(newNode);
            prevIndex = nodes.indexOf(newNode);
        }

        nodes.get(prevIndex).setWord(true);
    }

    private boolean containsString(String str) {
        PrefixTreeNode node = new PrefixTreeNode(str);
        return nodes.contains(node);
    }

    @Override
    public boolean isWord(String str) {
        int indexForNode = getIndexForGivenPrefix(str);

        return indexForNode != -1 && nodes.get(indexForNode).isWord();
    }

    @Override
    public void removeWord(String word) {
        PrefixTreeNode node = new PrefixTreeNode(word);
        int parentIndex = indexOfParent(node);
        if (parentIndex == -1)
            return;

        char currLetter = node.getContent().charAt(node.getContent().length() - 1);
        nodes.remove(node);
        PrefixTreeNode parentNode = nodes.get(parentIndex);
        removeEdge(parentNode, currLetter);
        while (!parentNode.isWord() && !edgesOfNode(parentNode).isEmpty()) {
            parentIndex = indexOfParent(parentNode);
            if (parentIndex == -1)
                return;

            currLetter = parentNode.getContent().charAt(parentNode.getContent().length() - 1);
            nodes.remove(parentNode);
            parentNode = nodes.get(parentIndex);
            removeEdge(parentNode, currLetter);
        }
    }

    private void removeEdge(PrefixTreeNode parent, Character letter) {
        PrefixTreeEdge toRemove = null;
        for (PrefixTreeEdge edge : edgesOfNode(parent))
            if (edge.getLetter() == letter) {
                toRemove = edge;
                break;
            }
        if (toRemove != null)
            edges.remove(toRemove);
    }

    private synchronized Set<PrefixTreeEdge> edgesOfNode(PrefixTreeNode currentNode) {
        Set<PrefixTreeEdge> currentEdges = new HashSet<>();
        for (PrefixTreeEdge edge : edges)
            if (edge.getParent().equals(currentNode))
                currentEdges.add(edge);
        return currentEdges;
    }

    private synchronized int indexOfParent(PrefixTreeNode node) {
        for (PrefixTreeEdge edge : edges)
            if (edge.getChild().equals(node))
                return nodes.indexOf(edge.getParent());
        return -1;
    }

    private synchronized PrefixTreeEdge getEdgeForLetter(PrefixTreeNode currentNode, Character letter) {
        for (PrefixTreeEdge edge : edgesOfNode(currentNode))
            if (edge.getLetter() == letter)
                return edge;
        return null;
    }

    private synchronized int getIndexForGivenPrefix(String prefix) {
        PrefixTreeNode prefixNode = new PrefixTreeNode(prefix);
        if (!nodes.contains(prefixNode))
            return -1;

        PrefixTreeNode currentNode = nodes.get(0);
        for (int index = 0; index < prefix.length(); ++index) {
            PrefixTreeEdge edge = getEdgeForLetter(currentNode, prefix.charAt(index));
            if (edge == null)
                return -1;

            currentNode = edge.getChild();
        }
        return nodes.indexOf(currentNode);
    }

    @Override
    public List<String> searchForGivenPrefix(String prefix) {
        PrefixTreeNode prefixNode = new PrefixTreeNode(prefix);
        if (!nodes.contains(prefixNode))
            return List.of();

        PrefixTreeNode currentNode = nodes.get(0);
        for (int index = 0; index < prefix.length(); ++index) {
            PrefixTreeEdge edge = getEdgeForLetter(currentNode, prefix.charAt(index));
            if (edge == null)
                return List.of();

            currentNode = edge.getChild();
        }

        List<String> wordsOfPrefix = new ArrayList<>();
        addWordsOfNode(wordsOfPrefix, currentNode);

        return wordsOfPrefix;
    }

    private synchronized void addWordsOfNode(List<String> wordsOfNode, PrefixTreeNode node) {
        if (node.isWord())
            wordsOfNode.add(node.getContent());
        for(PrefixTreeEdge edge : edgesOfNode(node))
            addWordsOfNode(wordsOfNode, edge.getChild());
    }

    public synchronized void printWords() {
        for (PrefixTreeNode node : nodes)
            if (node.isWord())
                System.out.print(node.getContent() + " ");
    }
}
