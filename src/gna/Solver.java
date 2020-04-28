package gna;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.List;

import libpract.PriorityFunc;

public class Solver
{
	Set<Board> visited_boards = new HashSet<Board>();
	State initial_state;
	State initial_state_solution;
	List<Board> solution = new ArrayList<Board>();
	
	/**
	 * Finds a solution to the initial board.
	 *
	 * @param priority is either PriorityFunc.HAMMING or PriorityFunc.MANHATTAN
	 */
	public Solver(Board initial, PriorityFunc priority)
	{
		StateComparator stateComparison = new StateComparator();
		PriorityQueue<State> priority_queue = new PriorityQueue<State>(5,stateComparison);
		this.initial_state = new State(initial);
		this.initial_state.setCost(0);
		
		State cursor = this.initial_state;
		int moves = 0;
		while(!cursor.getBoard().isGoal()) {
			visited_boards.add(cursor.getBoard());
			Collection<Board> cursor_neighbours = cursor.getBoard().neighbors();
			Iterator<Board> neighbourIterator = cursor_neighbours.iterator();
			while(neighbourIterator.hasNext()) {
				Board neighbour_cursor = neighbourIterator.next();
				if(!visited_boards.contains(neighbour_cursor)) {
					visited_boards.add(neighbour_cursor);
					State next_in_tree = new State(neighbour_cursor);
					//I think this is not working, I might still remove the next attribute
					cursor.setNext(next_in_tree);
					next_in_tree.setPrev(cursor);
					if (priority == PriorityFunc.HAMMING) {
						next_in_tree.setCost(next_in_tree.getBoard().hamming() + moves);
						System.out.println("Cost: " + next_in_tree.getCost());
					} else if(priority == PriorityFunc.MANHATTAN) {
						next_in_tree.setCost(next_in_tree.getBoard().manhattan() + moves);
					} else {
						throw new IllegalArgumentException("Priority function not supported");
					}
					priority_queue.add(next_in_tree);
				}
			}
			cursor = priority_queue.poll();
			moves += 1;
			System.out.println("----------------" + moves + "----------");
		}
		setSolution(cursor);
	}
	

	private void setSolution(State end_state) {
		//this function is used because our results are essentially backwards
		//the end_state has the latest pointers so we cannot start from the initial block
		//as that would have multiple branches
		State cursor = end_state;
		while(cursor != null) {
			this.solution.add(cursor.getBoard());
			cursor = cursor.prev();
		}
		//since I'm not sure how to implement this more efficiently, I'm reversing the list
		Collections.reverse(this.solution);
	}
	
	/**
	 * Returns a List of board positions as the solution. It should contain the initial
	 * Board as well as the solution (if these are equal only one Board is returned).
	 */
	public List<Board> solution()
	{
		return solution;
	}
}


