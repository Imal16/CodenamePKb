package main.models.interfaces;

import main.application.GameManager;
import main.models.business.Board;
import main.models.business.Operative;
import main.models.business.RelationGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

/**
 * CodenamePKb
 * Operative's pick card strategy using Codename word
 * relations through graph operations
 *
 * @author John Paulo, Ihsaan, Zijian
 * @date 16-Mar 3-19
 */
public class SmartPickCardStrategy implements PickCardStrategy {

    public Board board;
    private RelationGraph teamGraph;
    private Operative op;
    private PickRandomCardStrategy randStrat;

    /**
     * Normal constructor
     * @param board Board team's board
     * @param op Operative
     */
    public SmartPickCardStrategy(Board board, Operative op) {
        this.board = board;
        this.op = op;
        this.randStrat = new PickRandomCardStrategy(this.board);
        if (op.getTeam() == GameManager.RED) {
            this.teamGraph = board.getRedGraph();
        } else {
            this.teamGraph = board.getBlueGraph();
        }
    }

    /**
     * teamGraph getter
     * @return teamGraph
     */
    public RelationGraph getTeamGraph() {
        return teamGraph;
    }

    @Override
    public void execute() {

    }

    /**
     * Performs pick strategy, calls OperativeChoices
     * @param hint
     */
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
        List<String> wordsChosen = new ArrayList<String>();
        Set<Integer> numGuessSet = new HashSet<Integer>();
        ArrayList<String> descendants = new ArrayList<String>();
        numGuessSet = hint.keySet();                                //extract the #ofguesses
        for (Integer key : numGuessSet) {
            Set<DefaultWeightedEdge> descendantEdges = new HashSet<DefaultWeightedEdge>();
            String clue = hint.get(key);                                //Gets the clue  word
            ///equivalent of getting parent/hypernym.
            try {
                descendantEdges = this.getTeamGraph().getGraphObj().edgesOf(clue);                        //Gets the edges connecting to the clueword (set)
//                System.out.println("Clue word : " + clue);
                //System.out.println("Set of descendants edges: "+ this.OperativesGraph.edgesOf(clue).toString());
                for (DefaultWeightedEdge edge1 : descendantEdges) {                                //iterate throught the edges
                    String srcVertex = this.teamGraph.getGraphObj().getEdgeTarget(edge1);
                    if (descendants.contains(srcVertex) == false) {                        //Handling if there are duplicates
                        descendants.add(srcVertex);
                    }
                }

                Collections.shuffle(descendants);                                    //Assuming there are more than one possible word to select, randomize
                try {
                    wordsChosen = descendants.subList(0, key);                                //Select # guesses elements
                } catch (Exception e) {
                    if (wordsChosen.isEmpty()) {                                                //if there is an error with the key/ #of guesses to choose
                        wordsChosen.add(descendants.get(0));                                    //just  choose the first element of the list as a safe option
                    }
                }

                this.board.pickCardAt(wordsChosen.get(0));

                //if none found, use default strategy: PickRandomCardStrategy
            } catch (IllegalArgumentException e) {
                System.out.println("Clue not in database");
                this.randStrat.execute();
                String word = this.randStrat.pick;
                this.teamGraph.deletevertex(word);
                if (op.getTeam() == GameManager.RED) {
                    this.board.getRedCards().remove(word);
                } else {
                    this.board.getBlueCards().remove(word);
                }
            } catch (Exception e) {
                this.randStrat.execute();
                String word = this.randStrat.pick;
                this.teamGraph.deletevertex(word);
                if (op.getTeam() == GameManager.RED) {
                    this.board.getRedCards().remove(word);
                } else {
                    this.board.getBlueCards().remove(word);
                }
            }

        }

    }

}
