package gna;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.List;

import libpract.PriorityFunc;

public class Solver
{
	PriorityQueue<State> priority_queue = new PriorityQueue<State>();
	Set<Board> visited_boards = new HashSet<Board>();
	State initial_state;
	State first_state_solution = null;
	PriorityFunc priority;
	
	/**
	 * Finds a solution to the initial board.
	 *
	 * @param priority is either PriorityFunc.HAMMING or PriorityFunc.MANHATTAN
	 */
	public Solver(Board initial, PriorityFunc priority)
	{
		this.initial_state = new State(initial);
		this.initial_state.setCost(0);
		
		priority_queue.add(this.initial_state);
		// Use the given priority function (either PriorityFunc.HAMMING
		// or PriorityFunc.MANHATTAN) to solve the puzzle.
		State cursor = this.initial_state;
		int moves = 1;
		while(!cursor.getBoard().isGoal()) {
			visited_boards.add(cursor.getBoard());
			Collection<Board> cursor_neighbours = cursor.getBoard().neighbors();
			Iterator<Board> neighbourIterator = cursor_neighbours.iterator();
			while(neighbourIterator.hasNext()) {
				Board neighbour_cursor = neighbourIterator.next();
				if(!visited_boards.contains(neighbour_cursor)) {
					visited_boards.add(neighbour_cursor);
					State next_in_tree = new State(neighbour_cursor);
					cursor.setNext(next_in_tree);
					next_in_tree.setPrev(cursor);
					if (priority == PriorityFunc.HAMMING) {
						next_in_tree.setCost(next_in_tree.getBoard().hamming() + moves);
					} else if(priority == PriorityFunc.MANHATTAN) {
						next_in_tree.setCost(next_in_tree.getBoard().manhattan() + moves);
					} else {
						throw new IllegalArgumentException("Priority function not supported");
					}
					
				}
			}
			cursor = priority_queue.poll();
			moves += 1;
		}
	}
	

	/**
	 * Returns a List of board positions as the solution. It should contain the initial
	 * Board as well as the solution (if these are equal only one Board is returned).
	 */
	public List<Board> solution()
	{
		
		List<Board> solution = new ArrayList<Board>();
		return null; // TODO
	}
}


