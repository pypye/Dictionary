package base.basic;

import java.util.ArrayList;
import java.util.Arrays;

public class Trie {

  private static final int alphabetSize = 26;
  public static TrieNode root = new TrieNode();

  public void add(String key) {
    TrieNode trieNode = root;
    for (int i = 0; i < key.length(); i++) {
      char x = key.charAt(i);
      int index = 0;
      if (x >= 'a' && x <= 'z') {
        index = (int) (x - 'a');
      } else if (x >= 'A' && x <= 'Z') {
        index = (int) (x - 'A');
      } else if (x >= '0' && x <= '9') {
        index = (int) (x - '0');
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
    TrieNode trieNode = root;
    for (int i = 0; i < key.length(); i++) {
      char x = key.charAt(i);
      int index = 0;
      if (x >= 'a' && x <= 'z') {
        index = (int) (x - 'a');
      } else if (x >= 'A' && x <= 'Z') {
        index = (int) (x - 'A');
      } else if (x >= '0' && x <= '9') {
        index = (int) (x - '0');
      }
      if (trieNode.getChildren()[index] == null) {
        return null;
      }
      trieNode = trieNode.getChildren()[index];
    }
    return trieNode;
  }

  public String find(TrieNode keyNode) {
    TrieNode trieNode = keyNode;
    if (trieNode == null) {
      return "";
    }
    String ans = (trieNode.isEndOfWord() ? trieNode.getDataOfWord() + "\n" : "");
    for (int i = 0; i < alphabetSize; i++) {
      if (trieNode.getChildren()[i] != null) {
        ans = ans.concat(find(keyNode.getChildren()[i]));
      }
    }
    return ans;
  }

  public ArrayList<String> findAllWord(String key) {
    TrieNode trieNode = this.search(key);
    String[] ans = this.find(trieNode).split("\n");
    ArrayList<String> ansList = new ArrayList<>(Arrays.asList(ans));
    return ansList;
  }

}
