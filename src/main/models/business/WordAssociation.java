package main.models.business;

import java.util.Objects;

public class WordAssociation {
    private String word;
    private int count;

    /**
     * Normal constructor
     * @param word
     * @param count
     */
    public WordAssociation(String word, int count) {
        this.word = word;
        this.count = count;
    }

    /**
     * Getter
     * @return word String
     */
    public String getWord() {
        return word;
    }

    /**
     * Getter
     * @return count int
     */
    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordAssociation that = (WordAssociation) o;
        return Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {

        return Objects.hash(word);
    }
}
