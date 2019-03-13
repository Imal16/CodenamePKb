package main.models.interfaces;
import java.util.Comparator;
import java.util.List;
/**
 * List comparator class, it compares a list of objects and then sorts the list from largest to smallest
 * This is used to sort the Power Set of all keywords for the Spymaster to generate its hints.
 * @author Ihsaan Malek
 * @version 03/09/2019
 */

class ListComparator<T extends Comparable<T>> implements Comparator<List<T>>{
		
		public int compare(List<T> obj1, List<T> obj2) {
			return obj2.size()-obj1.size();
		}
}
