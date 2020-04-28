package gna;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import libpract.PriorityFunc;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * A number of JUnit tests for Solver.
 *
 * Feel free to modify these to automatically test puzzles or other functionality
 */
public class UnitTests {
  
  @Test
  public void testNeighbours() {
	  	int[][] testTiles1 = new int[][] { {8,1,3},{4,0,2},{7,6,5} };
		Board testBoard1 = new Board(testTiles1);
		
	  	int[][] testTiles2 = new int[][] { {0,1,3},{4,2,5},{7,8,6} };
		Board testBoard2 = new Board(testTiles2);
		
		
		Collection<Board> neighbours = testBoard1.neighbors();
		Iterator<Board> neighbiter = neighbours.iterator();
		//System.out.println("--------------");
		//while(neighbiter.hasNext()) {
			//System.out.print(neighbiter.next().toString());
			//System.out.println("--------------");
		//}
		
		//testing working of board
		Solver solution = new Solver(testBoard2,PriorityFunc.HAMMING);
		System.out.println("finished");
		for(int i = 0;i < solution.solution().size();i++) {
			System.out.print(solution.solution().get(i).toString());
		}
		
  }
}
