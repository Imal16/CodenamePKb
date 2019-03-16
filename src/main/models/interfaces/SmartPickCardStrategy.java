package main.models.interfaces;

import main.models.business.Board;
import main.models.business.Operative;
import main.models.business.RelationGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

/**
 * CodenamePKb
 *
 * @author John Paulo Valerio
 * @date 16-Mar 3-19
 */
public class SmartPickCardStrategy implements PickCardStrategy {

    public Board board;
    RelationGraph graph;
    Operative op;
    PickRandomCardStrategy randStrat;

    /**
     * Normal constructor
     * @param board
     * @param op
     */
    public SmartPickCardStrategy(Board board, Operative op) {
        this.board = board;
        this.op = op;
        this.randStrat = new PickRandomCardStrategy(this.board);
        if (op.getTeam() == 1) {
            this.graph = board.getRedGraph();
        } else {
            this.graph = board.getBlueGraph();
        }
    }

    /**
     * graph getter
     * @return graph
     */
    public RelationGraph getGraph() {
        return graph;
    }

    @Override
    public void execute() {

    }

    @Override
    public void execute(HashMap<Integer, String> hint) {
        OperativeChoices(hint);
    }

    //Function for the Operative to chose the word(s) to select given the hint (format of int(#guesses), string(Clue word))

    /**
     * Selects Operatives pick from Spymasters hint
     * @param hint hashmap<Integer frequency, String word>
     */
    public void OperativeChoices(HashMap<Integer, String> hint) {
        List<String> wordschosen = new ArrayList<String>();
        Set<Integer> numberoguessesset = new HashSet<Integer>();
        ArrayList<String> Decendents = new ArrayList<String>();
        numberoguessesset = hint.keySet();                                //extract the #ofguesses
        for (Integer key : numberoguessesset) {
            Set<DefaultWeightedEdge> decendentedges = new HashSet<DefaultWeightedEdge>();
            String clue = hint.get(key);                                //Gets the clue  word
            ///equvalent of getting parent/hypernym.
            try {
                decendentedges = this.getGraph().getGraphObj().edgesOf(clue);                        //Gets the edges connecting to the clueword (set)
                System.out.println("Clue word : " + clue);
                //System.out.println("Set of decscendent edges: "+ this.OperativesGraph.edgesOf(clue).toString());
                for (DefaultWeightedEdge edge1 : decendentedges) {                                //iterate throught the edges
                    String Sourcevert = this.graph.getGraphObj().getEdgeTarget(edge1);
                    if (Decendents.contains(Sourcevert) == false) {                        //Handling if there are duplicates
                        Decendents.add(Sourcevert);
                    }
                }

                Collections.shuffle(Decendents);                                    //Assuming there are more than one possible word to select, randomize
                try {
                    wordschosen = Decendents.subList(0, key);                                //Select # guesses elements
                } catch (Exception e) {
                    if (wordschosen.isEmpty()) {                                                //if there is an error with the key/ #of guesses to choose
                        wordschosen.add(Decendents.get(0));                                    //just  choose the first element of the list as a safe option
                    }
                }

                SearchAndFlip(wordschosen.get(0));

                //if none found, use default strategy: PickRandomCardStrategy
            } catch (IllegalArgumentException e) {
                System.out.println("Clue not in database");
                this.randStrat.execute();
            } catch (Exception e) {
                this.randStrat.execute();
            }

        }

    }

    /**
     * Iterates through board and reveal given Codename
     *
     * @param word
     */
    private void SearchAndFlip(String word) {
        System.out.println(word);
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (this.board.board[row][col].getWord() == word) {
                    this.board.pickCardAt(row, col);
                    this.graph.deletevertex(word);
                    if (op.getTeam() == 1) {
                        this.board.getRedCards().remove(word);
                    } else {
                        this.board.getBlueCards().remove(word);
                    }
                }
            }
        }
    }

}
