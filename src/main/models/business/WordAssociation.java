package main.models.business;

public class WordAssociation {
    String word;
    int count;

    public WordAssociation(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }
}
