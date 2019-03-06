package main.models.business;

import java.util.HashSet;

/**
 *
 * Word class
 *
 * @author John Paulo Valerio
 * @date 06/03/19
 *
 */

public class Word {

    private String word;
    private HashSet<WordAssociation> association;


    /**
     * Default constructor
     */
    public Word() {
        this.word = "ABC";
        this.association = new HashSet<WordAssociation>();
    }

    /**
     * Normal constructor
     * @param word String
     * @param association HashSet
     */
    public Word(String word, HashSet association) {
        this.word = word;
        this.association =  new HashSet<>(association);
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
     * @return association HashSet<WordAssociation>
     */
    public HashSet<WordAssociation> getAssociation() {
        return association;
    }

    /**
     * Intersection of 2 word association set
     * @param otherWord Word object to compare with
     * @return intersectSet HashSet<WordAssociation>
     */
    public HashSet<WordAssociation> intersection(Word otherWord){
        HashSet<WordAssociation> intersectSet = this.association;
        intersectSet.retainAll(otherWord.getAssociation());

        return intersectSet;
    }
}
