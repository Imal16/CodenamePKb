package main.models.interfaces;

import main.models.business.Board;
import main.models.business.Operative;
import main.models.business.RelationGraph;
import org.jgrapht.alg.lca.NaiveLCAFinder;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class SmartHintStrategy implements HintStrategy{

    Board board;
    int teamCode;
    Operative operative;

    int hintNum;
    String hintWord;


    public RelationGraph getGraph() {
        return graph;
    }

    RelationGraph graph;

    public SmartHintStrategy(Board board, Operative operative,int teamCode) {
        this.board = board;
        this.operative = operative;
        this.teamCode = teamCode;
        if(this.teamCode == 2){
            this.graph = this.board.getRedGraph();
        }
        else {
            this.graph = this.board.getBlueGraph();
        }
    }

    @Override
    public void execute() {
        HashMap<Integer, String> hint;
        if(this.teamCode == 2){
            hint = checkforhints(this.board.getRedCards());
        }
        else{
             hint = checkforhints(this.board.getBlueCards());
        }
        for (Map.Entry<Integer, String> foo :
                hint.entrySet()) {
            this.hintNum = foo.getKey();
            this.hintWord = foo.getValue();
//            System.out.println(foo.getKey() + " - " + foo.getValue());
        }

    }

    public HashMap<Integer, String> checkforhints(List<String> teamcards) {
        HashMap<Integer, String> hint = new HashMap<Integer, String>();            //hint consisting of an integer=Number of words available for the specific clue provided
        List<List<String>> PowerSet = new ArrayList<List<String>>();

        PowerSet = powerSet(teamcards);                                //Calls a function that generates a powerset of all the Code names
        Collections.sort(PowerSet, new ListComparator<>());        //Sorts the Powerset in terms of Largest to Smallest

        //System.out.println("PowerSet: "+PowerSet );
        for (List<String> set : PowerSet) {                            //Iterate throught the powerset
            if (set.size() > 2) {
                hint = LCAmultiple(set);                                //IF the length of the subset if Larger than two, we Call LCAMultiple
                if (!hint.isEmpty()) {                                //If An ancestor is found of that subset return the hint

                    //System.out.println("Hint provided by LCA: "+hint );
                    return hint;
                }
            }
            if (set.size() == 2) {                                        //IF subset is of size 2, use JGraphT's  built-in function for find the set of Common Ancestors/Clues
                NaiveLCAFinder<String, DefaultWeightedEdge> lca = new NaiveLCAFinder<String, DefaultWeightedEdge>(this.getGraph().getGraphObj());
                List<String> listofhints = new ArrayList<String>();            //Above is Necessary to  find Clues/Ancestors, need to create an instance of an object of NaiveLCAFinder
                try {
                    Set<String> setofhints = new HashSet<String>();
                    setofhints = lca.getLCASet(set.get(0), set.get(1));    //Gets the LCA of two elements of  the subset
                    //System.out.println(setofhints);
                    listofhints.addAll(setofhints);                    //Converts the set to a list
                    Collections.shuffle(listofhints);                //Shuffle, allows randomization if there is multiple available clue words
                    hint.put(2, listofhints.get(0));                    //Makes the hint, adds to the HashMap, saying 2 words can be selected, with the hint word
                    //System.out.println("Hint provided: "+ hint);
                    return hint;
                } catch (Exception e) {
//                    System.out.println("No common ancestors");
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
                    //System.out.println("Hint provided: "+ hint);
                    return hint;
                }

            }
        }


        //System.out.println("Hint provided by LCA: "+hint );

        return hint;

    }

    //Function that Return the power of the list of all available codenames
    public static List<List<String>> powerSet(List<String> teamcards) {
        List<List<String>> combinations = new ArrayList<List<String>>();

        if (teamcards.isEmpty()) {
            combinations.add(new ArrayList<String>());
            return combinations;
        }

        List<String> subset = new ArrayList<String>(teamcards);
        String head = subset.get(0);
//        System.out.println(subset);

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

    //Function finds the Lowest Common Ancestor of multiple nodes/vertices in a graph
    //Takes in the list of nodes or in this case the list of available  team cards.
    public HashMap<Integer, String> LCAmultiple(List<String> teamcards) {
        HashMap<Integer, String> hint = new HashMap<Integer, String>();
        HashMap<Integer, ArrayList<String>> hashlistofhints = new HashMap<Integer, ArrayList<String>>();
        ArrayList<String> Listofhints = new ArrayList<String>();
        if (teamcards.size() > 0) {
            String first = teamcards.get(0);
//            System.out.println("first vert: " + first);
            ArrayList<String> firstA = getallancestors(first);            //Takes the first element of the list and gets a list of the ancestors of that word/node
//            System.out.println("First list of ancestors: " + firstA);


            for (int k = 1; k < teamcards.size() && !firstA.isEmpty(); k++) {
                String currentvert = teamcards.get(k);                                    //Iterates through the rest of the  list of Codenames
//                System.out.println("current vert: " + currentvert);
                hashlistofhints = lookForCommonAncestors(teamcards, firstA, currentvert);    //And check if there are common ancestors between the Codenames
            }
            if (!firstA.isEmpty()) {                                    //If the list is not empty then common word(s) have been found
                for (int key : hashlistofhints.keySet()) {            //The  key will be the number of guesses for the hint
                    Listofhints = hashlistofhints.get(key);
                    if (!Listofhints.isEmpty()) {
                        Collections.shuffle(Listofhints);            //Because a hint can only be one word, generate a random element of the list
                        hint.put(key, Listofhints.get(0));
//                        System.out.println("Hint! :");
//                        System.out.println(hint);

                    }
                }
            }
            firstA.clear();            //clear memory
        }

        if (hint.isEmpty()) {
            //System.out.println("No hint  found!!!!!!!!!");			//IF hashmap is empty then there was no common ancestors in this set of codenames

        }
        //System.out.println(hint);

        return hint;

    }

    //Function that finds all ancestors/clue words for a given Codename
    public ArrayList<String> getallancestors(String word) {

        ArrayList<String> ancestors = new ArrayList<String>();

        Set<DefaultWeightedEdge> ancestoredges = new HashSet<DefaultWeightedEdge>();
        ///equvalent of getting parent/hypernym.
        ancestoredges = this.getGraph().getGraphObj().edgesOf(word);                //finds all  connecting edges of a word (set)
        //System.out.println("New key word: "+ word);
        for (DefaultWeightedEdge edge1 : ancestoredges) {
            String Sourcevert = this.getGraph().getGraphObj().getEdgeSource(edge1);    //Iterate through the set and find the ancestor/clue word
            //System.out.println("Individual Source edge: "+ Sourcevert);
            if (ancestors.contains(Sourcevert) == false) {
                //System.out.println("adding hypernym: "+ Sourcevert);
                ancestors.add(Sourcevert);                                    //If the word is not already in the list of ancestors add it to the list
            }
        }

        //System.out.println("List of hypernym: "+ ancestors);
        return ancestors;
    }

    //Function that finds common ancestors between the list of Codenames provided and the List of Ancestors of a word/Codename
    //Essentially, takes the list of Ancestors from the first Codename from the Teamcards list and then tries to find commmon Clue words between
    //that list  and the rest of the words in the teamcards list.
    public HashMap<Integer, ArrayList<String>> lookForCommonAncestors(List<String> teamcards, ArrayList<String> commonAncestors, String CurrentVert) {
        ArrayList<String> commonbetweenall = new ArrayList<String>(commonAncestors);
        ArrayList<String> commonbetweenallbackup = new ArrayList<String>(commonAncestors);

        HashMap<Integer, ArrayList<String>> Hintwords = new HashMap<Integer, ArrayList<String>>();        //clue in format integer(#guesses), string(word)
        int numberofguesses = 0;
        for (int i = 1; i < teamcards.size(); i++) {        //Iterate from the second element  of the list of teamcards to the end

            String vert1 = teamcards.get(i);
            //System.out.println("Look for common: "+ vert1);

            ArrayList<String> ancestors2 = new ArrayList<String>();
            ancestors2 = getallancestors(vert1);                                            //find the ancestors/clue words for the ith element in the list
            //System.out.println("Ancestors of: "+ vert1 + " = "+ancestors2);

            commonbetweenall.retainAll(ancestors2);                            //keeps  the common elements between the two lists

            if (commonbetweenall.isEmpty() == false) {                        //if the common list is not empty-> common clues
                if (Hintwords.containsKey(numberofguesses + 1)) {
                    Hintwords.remove(numberofguesses + 1);                        //remove first entry to provide the correct hint
                }
                commonbetweenallbackup = commonbetweenall;
                numberofguesses++;                                                //the more commonality between lists, means more words to be guesses for that clue
                //System.out.println("Number of hints "+ numberofguesses);
                Hintwords.put(numberofguesses + 1, commonbetweenallbackup);            //gets the clue consisting the #guesses and clue word

            }
            if (commonbetweenall.isEmpty() == true) {                                    //if no commonality if found, no clue word, return a empty list
//                System.out.println("No common set: " + Hintwords);

                return Hintwords;
            }
        }
//        System.out.println("Found common set: " + Hintwords);

        return Hintwords;

    }

}
