package main.models.interfaces;

import main.models.business.Board;
import main.models.business.Operative;
import main.models.business.RelationGraph;
import org.jgrapht.alg.lca.NaiveLCAFinder;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

/**
 * Strategy for Spymaster to form a hint
 *
 * @author John Paulo, Ihsaan, Zijian
 */
public class SmartHintStrategy implements HintStrategy {

    private Board board;
    private Operative operative;
    private RelationGraph teamGraph;

    /**
     * Normal constructor
     * @param board Board
     * @param operative Operative
     */
    public SmartHintStrategy(Board board, Operative operative) {
        this.board = board;
        this.operative = operative;
        if (this.operative.getTeam() == 1) {
            this.teamGraph = this.board.getRedGraph();
        } else {
            this.teamGraph = this.board.getBlueGraph();
        }
    }

    /**
     * teamGraph getter
     * @return teamGraph RelationGraph
     */
    public RelationGraph getGraph() {
        return teamGraph;
    }

    /**
     * performs the strategy
     * calls checkForHints with team's card list
     * @return hint HashMap<Integer frequency, String word>
     */
    @Override
    public HashMap<Integer, String> execute() {
        HashMap<Integer, String> hint;
        if (this.operative.getTeam() == 1) {
            hint = checkForHints(this.board.getRedCards());
        } else {
            hint = checkForHints(this.board.getBlueCards());
        }

        return hint;
    }

    /**
     * Spymaster's method to choose a hint using the team's graph
     *
     * @param teamCards List<String> team's Codenames
     * @return hint HashMap<Integer frequency, String word>
     */
    public HashMap<Integer, String> checkForHints(List<String> teamCards) {
        HashMap<Integer, String> hint = new HashMap<Integer, String>();            //hint consisting of an integer=Number of words available for the specific clue provided
        List<List<String>> PowerSet = new ArrayList<List<String>>();

        PowerSet = powerSet(teamCards);                                //Calls a function that generates a powerset of all the Code names
        Collections.sort(PowerSet, new ListComparator<>());        //Sorts the Powerset in terms of Largest to Smallest

        for (List<String> set : PowerSet) {                            //Iterate throught the powerset
            if (set.size() > 2) {
                hint = LCAMultiple(set);                                //IF the length of the subset if Larger than two, we Call LCAMultiple
                if (!hint.isEmpty()) {                                //If An ancestor is found of that subset return the hint

                    return hint;
                }
            }
            if (set.size() == 2) {                                        //IF subset is of size 2, use JGraphT's  built-in function for find the set of Common Ancestors/Clues
                NaiveLCAFinder<String, DefaultWeightedEdge> lca = new NaiveLCAFinder<String, DefaultWeightedEdge>(this.getGraph().getGraphObj());
                List<String> listofhints = new ArrayList<String>();            //Above is Necessary to  find Clues/Ancestors, need to create an instance of an object of NaiveLCAFinder
                try {
                    Set<String> setofhints = new HashSet<String>();
                    setofhints = lca.getLCASet(set.get(0), set.get(1));    //Gets the LCA of two elements of  the subset
                    listofhints.addAll(setofhints);                    //Converts the set to a list
                    Collections.shuffle(listofhints);                //Shuffle, allows randomization if there is multiple available clue words
                    hint.put(2, listofhints.get(0));                    //Makes the hint, adds to the HashMap, saying 2 words can be selected, with the hint word
                    return hint;
                } catch (Exception e) {
                }
            }
            if (set.size() == 1) {                                                                //IF the subset only has 1 word, it cannot have a common Clue/Ancestor with
                Set<DefaultWeightedEdge> ancestoredges = new HashSet<DefaultWeightedEdge>();        //Another word, therefor, a random clue word is selected from
                List<String> listofhintsforasingle = new ArrayList<String>();                        //its own set of clue words
                ancestoredges = this.getGraph().getGraphObj().edgesOf(set.get(0));
                for (DefaultWeightedEdge edge1 : ancestoredges) {
                    String Sourcevert = this.getGraph().getGraphObj().getEdgeSource(edge1);
                    listofhintsforasingle.add(Sourcevert);
                    Collections.shuffle(listofhintsforasingle);
                    hint.put(1, listofhintsforasingle.get(0));
                    return hint;
                }

            }
        }
        return hint;

    }

    //Function that Return the power of the list of all available codenames

    /**
     * power of the list of all available Codenames
     *
     * @param teamCards
     * @return combinations
     */
    private static List<List<String>> powerSet(List<String> teamCards) {
        List<List<String>> combinations = new ArrayList<List<String>>();

        if (teamCards.isEmpty()) {
            combinations.add(new ArrayList<String>());
            return combinations;
        }

        List<String> subset = new ArrayList<String>(teamCards);
        String head = subset.get(0);

        List<String> restoflist = new ArrayList<String>(subset.subList(1, subset.size()));

        for (List<String> set : powerSet(restoflist)) {
            List<String> newSet = new ArrayList<String>();
            newSet.add(head);
            newSet.addAll(set);
            combinations.add(newSet);
            combinations.add(set);
        }
        return combinations;

    }

    /**
     * Finds the Lowest Common Ancestor of multiple nodes/vertices in a graph
     * Takes in the list of nodes or in this case the list of available  team cards.
     *
     * @param teamCards List<String> team's Codenames
     * @return hint
     */
    private HashMap<Integer, String> LCAMultiple(List<String> teamCards) {
        HashMap<Integer, String> hint = new HashMap<Integer, String>();
        HashMap<Integer, ArrayList<String>> hashListHints = new HashMap<Integer, ArrayList<String>>();
        ArrayList<String> listHints = new ArrayList<String>();
        if (teamCards.size() > 0) {
            String first = teamCards.get(0);
            ArrayList<String> firstA = getAllAncestors(first);            //Takes the first element of the list and gets a list of the ancestors of that word/node


            for (int k = 1; k < teamCards.size() && !firstA.isEmpty(); k++) {
                String currentVertex = teamCards.get(k);                                    //Iterates through the rest of the  list of Codenames
                hashListHints = lookForCommonAncestors(teamCards, firstA, currentVertex);    //And check if there are common ancestors between the Codenames
            }
            if (!firstA.isEmpty()) {                                    //If the list is not empty then common word(s) have been found
                for (int key : hashListHints.keySet()) {            //The  key will be the number of guesses for the hint
                    listHints = hashListHints.get(key);
                    if (!listHints.isEmpty()) {
                        Collections.shuffle(listHints);            //Because a hint can only be one word, generate a random element of the list
                        hint.put(key, listHints.get(0));

                    }
                }
            }
            firstA.clear();            //clear memory
        }

        return hint;

    }


    /**
     * finds all ancestors/clue words for a given Codename
     *
     * @param word String word to be processed
     * @return ancestors
     */
    private ArrayList<String> getAllAncestors(String word) {

        ArrayList<String> ancestors = new ArrayList<String>();

        Set<DefaultWeightedEdge> ancestoredges = new HashSet<DefaultWeightedEdge>();
        ///equvalent of getting parent/hypernym.
        ancestoredges = this.getGraph().getGraphObj().edgesOf(word);                //finds all  connecting edges of a word (set)
        for (DefaultWeightedEdge edge1 : ancestoredges) {
            String Sourcevert = this.getGraph().getGraphObj().getEdgeSource(edge1);    //Iterate through the set and find the ancestor/clue word
            if (ancestors.contains(Sourcevert) == false) {
                //System.out.println("adding hypernym: "+ Sourcevert);
                ancestors.add(Sourcevert);                                    //If the word is not already in the list of ancestors add it to the list
            }
        }
        return ancestors;
    }

    /**
     * finds common ancestors between the list of Codenames
     * provided and the List of Ancestors of a word/Codename
     *
     * @param teamCards List<String> team's Codenames
     * @param commonAncestors ArrayList<String>
     * @param currentVertex String
     * @return hintWords
     */
    private HashMap<Integer, ArrayList<String>> lookForCommonAncestors(List<String> teamCards, ArrayList<String> commonAncestors, String currentVertex) {
        ArrayList<String> commonbetweenall = new ArrayList<String>(commonAncestors);
        ArrayList<String> commonbetweenallbackup = new ArrayList<String>(commonAncestors);

        HashMap<Integer, ArrayList<String>> Hintwords = new HashMap<Integer, ArrayList<String>>();        //clue in format integer(#guesses), string(word)
        int numberofguesses = 0;
        for (int i = 1; i < teamCards.size(); i++) {        //Iterate from the second element  of the list of teamcards to the end

            String vert1 = teamCards.get(i);
            ArrayList<String> ancestors2 = new ArrayList<String>();
            ancestors2 = getAllAncestors(vert1);                                            //find the ancestors/clue words for the ith element in the list
            commonbetweenall.retainAll(ancestors2);                            //keeps  the common elements between the two lists

            if (commonbetweenall.isEmpty() == false) {                        //if the common list is not empty-> common clues
                if (Hintwords.containsKey(numberofguesses + 1)) {
                    Hintwords.remove(numberofguesses + 1);                        //remove first entry to provide the correct hint
                }
                commonbetweenallbackup = commonbetweenall;
                numberofguesses++;                                                //the more commonality between lists, means more words to be guesses for that clue
                Hintwords.put(numberofguesses + 1, commonbetweenallbackup);            //gets the clue consisting the #guesses and clue word

            }
            if (commonbetweenall.isEmpty() == true) {                                    //if no commonality if found, no clue word, return a empty list
                return Hintwords;
            }
        }
        return Hintwords;

    }

}
