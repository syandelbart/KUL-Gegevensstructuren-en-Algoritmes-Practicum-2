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
		Collection<Board> neighbours = testBoard1.neighbors();
		System.out.print(testBoard1.toString());
		System.out.println("Hamming: " + testBoard1.hamming());
		System.out.println("Manhattan: " + testBoard1.manhattan());
		Iterator<Board> neighbiter = neighbours.iterator();
		//System.out.println("--------------");
		//while(neighbiter.hasNext()) {
			//System.out.print(neighbiter.next().toString());
			//System.out.println("--------------");
		//}
		
  }
}
