package main.models.business;

public class Word {
    String word;
    WordAssociation[] association;

    public Word() {
        this.word = "ABC";
        this.association = new WordAssociation[0];
    }

    public Word(String word, WordAssociation[] association) {
        this.word = word;
        this.association = association;

        System.out.println("current: "+ word);
        for (WordAssociation ass_word :
                association) {
            System.out.println("\tASS "+ass_word.getWord());
            System.out.println("\tCOUNT "+ass_word.getCount());
        }
    }

    public String getWord() {
        return word;
    }

    public WordAssociation[] getAssociation() {
        return association;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                '}';
    }
}
