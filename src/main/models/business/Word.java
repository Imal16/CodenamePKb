package main.models.business;

import java.util.HashSet;

public class Word {
    private String word;
    private HashSet<WordAssociation> association;

    public Word() {
        this.word = "ABC";
        this.association = new HashSet<WordAssociation>();
    }

    public Word(String word, HashSet association) {
        this.word = word;
        this.association =  new HashSet<>(association);
    }

    public String getWord() {
        return word;
    }

    public HashSet<WordAssociation> getAssociation() {
        return association;
    }
}
