package main.models.interfaces;

import java.util.HashMap;

/**
 * Strategy interface
 * @author Zijian Wang
 *
 */
public interface PickCardStrategy {
		void execute();
		void execute(HashMap<Integer, String> hint);
}
