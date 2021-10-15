package base.algorithms;

public class TrieNode {
    private static final int alphabetSize = 26;
    private TrieNode[] children = new TrieNode[alphabetSize];
    private boolean endOfWord;
    private String dataOfWord;

    TrieNode() {
        for (int i = 0; i < alphabetSize; i++) children[i] = null;
        endOfWord = false;
        dataOfWord = "";
    }

    public TrieNode[] getChildren() {
        return children;
    }

    public void setChildren(TrieNode[] children) {
        this.children = children;
    }

    public boolean isEndOfWord() {
        return endOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

    public String getDataOfWord() {
        return dataOfWord;
    }

    public void setDataOfWord(String dataOfWord) {
        this.dataOfWord = dataOfWord;
    }
}
