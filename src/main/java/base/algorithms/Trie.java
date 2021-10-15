package base.algorithms;

import java.util.ArrayList;
import java.util.Arrays;

public class Trie {
    private static final int alphabetSize = 26;
    public static TrieNode root = new TrieNode();

    public void add(String key) {
        key = key.toLowerCase();
        TrieNode trieNode = root;
        for (int i = 0; i < key.length(); i++) {
            char x = key.charAt(i);
            int index = 0;
            if (x >= 'a' && x <= 'z') {
                index = x - 'a';
            } else if (x >= 'A' && x <= 'Z') {
                index = x - 'A';
            } else if (x >= '0' && x <= '9') {
                index = x - '0';
            }
            if (trieNode.getChildren()[index] == null) {
                trieNode.getChildren()[index] = new TrieNode();
            }
            trieNode = trieNode.getChildren()[index];
        }
        trieNode.setEndOfWord(true);
        trieNode.setDataOfWord(key);
    }

    public TrieNode search(String key) {
        key = key.toLowerCase();
        TrieNode trieNode = root;
        for (int i = 0; i < key.length(); i++) {
            char x = key.charAt(i);
            int index = 0;
            if (x >= 'a' && x <= 'z') {
                index = x - 'a';
            } else if (x >= 'A' && x <= 'Z') {
                index = x - 'A';
            } else if (x >= '0' && x <= '9') {
                index = x - '0';
            }
            if (trieNode.getChildren()[index] == null) {
                return null;
            }
            trieNode = trieNode.getChildren()[index];
        }
        return trieNode;
    }

    public String find(TrieNode keyNode) {
        if (keyNode == null) {
            return "";
        }
        String ans = (keyNode.isEndOfWord() ? keyNode.getDataOfWord() + "\n" : "");
        for (int i = 0; i < alphabetSize; i++) {
            if (keyNode.getChildren()[i] != null) {
                ans = ans.concat(find(keyNode.getChildren()[i]));
            }
        }
        return ans;
    }

    public ArrayList<String> findAllWord(String key) {
        key = key.toLowerCase();
        TrieNode trieNode = this.search(key);
        String[] ans = this.find(trieNode).split("\n");
        return new ArrayList<>(Arrays.asList(ans));
    }

    static boolean isEmpty(TrieNode trieNode) {
        for (int i = 0; i < alphabetSize; i++) {
            if (trieNode.getChildren()[i] != null) {
                return false;
            }
        }
        return true;
    }

    public void delete(String key) {
        key = key.toLowerCase();
        backTrackDelete(root, key, 0);
    }

    public TrieNode backTrackDelete(TrieNode trieNode, String key, int depth) {
        key = key.toLowerCase();
        if (trieNode == null) {
            return null;
        }
        if (depth == key.length()) {
            if (trieNode.isEndOfWord()) {
                trieNode.setEndOfWord(false);
            }
            if (isEmpty(trieNode)) {
                trieNode = null;
            }
            return trieNode;
        }

        char x = key.charAt(depth);
        int index = 0;
        if (x >= 'a' && x <= 'z') {
            index = x - 'a';
        } else if (x >= 'A' && x <= 'Z') {
            index = x - 'A';
        } else if (x >= '0' && x <= '9') {
            index = x - '0';
        }

        trieNode.getChildren()[index] = backTrackDelete(trieNode.getChildren()[index], key, depth + 1);
        if (isEmpty(trieNode) && !trieNode.isEndOfWord()) {
            trieNode = null;
        }
        return trieNode;
    }
}
