package main.models.interfaces;

/**
 * Strategy interface
 * @author Zijian Wang
 *
 */
public interface PickCardStrategy {
		void execute();
		void execute(String word);
}
