package gna;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;

public class Board
{
	private int[][] tiles;

	// construct a board from an N-by-N array of tiles
	public Board( int[][] tiles )
	{
		int[][] copytiles = new int[tiles.length][tiles[0].length];
		for(int y = 0;y < tiles.length;y++) {
			for(int x = 0;x < tiles[0].length;x++) {
				copytiles[y][x] = tiles[y][x];
			}
		}
		this.tiles = copytiles;
		
		
		
		// TODO (make a deep copy of tiles and store it into this.tiles)
		//throw new RuntimeException("not implemented");
	}
	
	// return number of blocks out of place
	public int hamming()
	{
		//only returns blocks out of place, distance is calculated in Solver
		int out_of_place = 0;
		for(int counter = 0;counter < Math.pow(this.tiles.length, 2);counter++) {
			if(this.tiles[(int) (counter / this.tiles.length)][counter % this.tiles.length] != counter+1 && this.tiles[(int) (counter / this.tiles.length)][counter % this.tiles.length] != 0) {
				out_of_place++;
			}
		}		
		
		return out_of_place;
	}
	
	// return sum of Manhattan distances between blocks and goal
	public int manhattan()
	{
		
		int expected_value = 1;
		int total_wrong_distance = 0;
		int actual_index;
		int actual_value;
		int expected_index;
		for(actual_index = 0;actual_index < Math.pow(this.tiles.length, 2)-1;actual_index++) {
			actual_value = this.getPositionValue(actual_index);
			expected_index = this.getValuePosition(expected_value);
			if(actual_value == 0) {

			} else if(actual_value == expected_value) {
				expected_value++;

			} else if(actual_value != expected_value) {
				int expected_index_y = (int) (expected_index / this.tiles.length);
				int expected_index_x = expected_index % this.tiles.length;
				int actual_index_y = (int) (actual_index / this.tiles.length);
				int actual_index_x = actual_index % this.tiles.length;
				
				total_wrong_distance += Math.abs(expected_index_y - actual_index_y) + Math.abs(expected_index_x - actual_index_x);
			}
			
		}
		expected_value = 0;
		actual_value = this.getPositionValue(actual_index);
		if(actual_value != 0) {
			expected_index = this.getValuePosition(expected_value);
			int expected_index_y = (int) (expected_index / this.tiles.length);
			int expected_index_x = expected_index % this.tiles.length;
			int actual_index_y = (int) (actual_index / this.tiles.length);
			int actual_index_x = actual_index % this.tiles.length;
			total_wrong_distance += Math.abs(expected_index_y - actual_index_y) + Math.abs(expected_index_x - actual_index_x);
		}
		
		return total_wrong_distance;
	}
	
	public Board Copy() {
		return new Board(this.tiles.clone());
	}
	
	// Does this board equal y. Two boards are equal when they both were constructed
	// using tiles[][] arrays that contained the same values.
	@Override
	public boolean equals(Object y)
	{
		if ( !(y instanceof Board) )
			return false;

		Board other = (Board)y;
		return Arrays.deepEquals(tiles, other.tiles);
	}

	// Since we override equals(), we must also override hashCode(). When two objects are
	// equal according to equals() they must return the same hashCode. More info:
	// - http://stackoverflow.com/questions/27581/what-issues-should-be-considered-when-overriding-equals-and-hashcode-in-java/27609#27609
	// - http://www.ibm.com/developerworks/library/j-jtp05273/
    @Override
    public int hashCode()
	{
		return Arrays.deepHashCode(tiles);
	}
	
	// return a Collection of all neighboring board positions
	public Collection<Board> neighbors()
	{
		Collection<Board> neighbours = new ArrayList<Board>();
		int zero_position = this.getValuePosition(0);
		int zero_x = zero_position % this.tiles.length;
		int zero_y = (int) (zero_position / this.tiles.length);
		Board cursor = null;
		//check neighbours individually
		//x-left
		if(zero_x - 1 >= 0) {
			cursor = this.Copy();
			cursor.switchTiles(zero_position, zero_position - 1);
			neighbours.add(cursor);
		}
		//x-right
		if(zero_x + 1 <= this.tiles.length-1) {
			cursor = this.Copy();
			cursor.switchTiles(zero_position, zero_position + 1);
			neighbours.add(cursor);
		}
		//y-top
		if(zero_y - 1 >= 0) {
			cursor = this.Copy();
			cursor.switchTiles(zero_position, zero_position - this.tiles.length);
			neighbours.add(cursor);
		}
		//y-bot
		if(zero_y + 1 <= this.tiles.length-1) {
			cursor = this.Copy();
			cursor.switchTiles(zero_position, zero_position + this.tiles.length);
			neighbours.add(cursor);
		}	
		return neighbours;
	}
	
	// return a string representation of the board
	public String toString()
	{
		String result = new String();
		for(int index=0;index<Math.pow(this.tiles.length, 2);index++) {
			if(index % this.tiles.length == 0) {
				result += "\n";
			}
			result += this.getPositionValue(index) + " ";
		}
		return result + "\n";
	}

	// is the initial board solvable? Note that the empty tile must
	// first be moved to its correct position.
	public boolean isSolvable()
	{
		int inversions = 0;
		for(int c1 = 0;c1 < Math.pow(this.tiles.length, 2)-1;c1++) {
			for(int c2 = c1+1;c2 < Math.pow(this.tiles.length, 2);c2++) {
				if(this.getPositionValue(c2) > 0 && this.getPositionValue(c1) > 0 && this.getPositionValue(c1) > this.getPositionValue(c2)) {
					inversions++;
				}
			}
		}
		
		//if grid has odd N in both directions
		//reference https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/
		if(this.tiles.length % 2 == 1) {
			return inversions % 2 == 0;
		} else {
			if(this.getValuePosition(0) % 2 == 1) {
				return inversions % 2 == 0;
			} else {
				return inversions % 2 == 1;
			}
		}
	}
	
	private int getValuePosition(int value) {
		int counter = 0;
		while(value != this.getPositionValue(counter)) {
			counter++;
		}
		return counter;
	}
	
	private void switchTiles(int index1,int index2) {
		int index1value = this.getPositionValue(index1);
		this.setValue(index1, this.getPositionValue(index2));;
		this.setValue(index2, index1value);
	}
	
	private int getPositionValue(int index) {
		return this.tiles[(int) (index / this.tiles.length)][index % this.tiles.length];
	}
	
	private void setValue(int index,int value) {
		this.tiles[(int) (index / this.tiles.length)][index % this.tiles.length] = value;
	}
	
	
	public boolean isGoal() {
		boolean valid = true;
		int counter = 0;
		while(valid && counter < Math.pow(this.tiles.length, 2)-1) {
			valid = (this.tiles[(int) (counter / this.tiles.length)][counter % this.tiles.length] == (counter+1));
			counter++;
		}
		return valid;
		
	}
	
	
	
}

