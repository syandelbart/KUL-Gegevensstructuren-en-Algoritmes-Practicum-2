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
		//copy tiles & board
		Board hamming_board = new Board(this.tiles);
		
		//getting zero tile to bottom
		while((int) (hamming_board.getValuePosition(0) / this.tiles.length) != this.tiles.length-1) {
			hamming_board.switchTiles(hamming_board.getValuePosition(0), hamming_board.getValuePosition(0) + this.tiles.length);
		}
		
		//getting zero tile to right
		while(hamming_board.getValuePosition(0) != Math.pow(this.tiles.length, 2) - 1) {
			hamming_board.switchTiles(hamming_board.getValuePosition(0), hamming_board.getValuePosition(0) + 1);
		}
		
		//only returns blocks out of place, distance is calculated in Solver
		int out_of_place = 0;
		for(int counter = 0;counter < Math.pow(this.tiles.length, 2);counter++) {
			if(hamming_board.tiles[(int) (counter / this.tiles.length)][counter % this.tiles.length] != counter+1 && hamming_board.tiles[(int) (counter / this.tiles.length)][counter % this.tiles.length] != 0) {
				out_of_place++;
			}
		}		
		
		return out_of_place;
	}
	
	// return sum of Manhattan distances between blocks and goal
	public int manhattan()
	{
		//copy tiles & board
		Board hamming_board = new Board(this.tiles);
		
		//getting zero tile to bottom
		while((int) (hamming_board.getValuePosition(0) / this.tiles.length) != this.tiles.length-1) {
			hamming_board.switchTiles(hamming_board.getValuePosition(0), hamming_board.getValuePosition(0) + this.tiles.length);
		}
		
		//getting zero tile to right
		while(hamming_board.getValuePosition(0) != Math.pow(this.tiles.length, 2) - 1) {
			hamming_board.switchTiles(hamming_board.getValuePosition(0), hamming_board.getValuePosition(0) + 1);
		}
		
		System.out.print(hamming_board.toString());
		
		int total_wrong_distance = 0;
		for(int counter = 0;counter < Math.pow(this.tiles.length, 2);counter++) {
			int expected = counter+1;
			int actual = hamming_board.getPositionValue(counter);
			if(actual != expected && hamming_board.tiles[(int) (counter / this.tiles.length)][counter % this.tiles.length] != 0) {
				int actual_value_on_spot = hamming_board.getPositionValue(counter);
				
				int index_of_expected_value = hamming_board.getValuePosition((counter+1));
				//actual index is counter
				total_wrong_distance += Math.abs((int) (index_of_expected_value / this.tiles.length) - (int) (counter / this.tiles.length));
				total_wrong_distance += Math.abs((index_of_expected_value % this.tiles.length) - (counter % this.tiles.length));
			}
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
			if(index % 3 == 0) {
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
		//copy tiles & board
		Board hamming_board = new Board(this.tiles);
		
		//getting zero tile to bottom
		while((int) (hamming_board.getValuePosition(0) / this.tiles.length) != this.tiles.length-1) {
			hamming_board.switchTiles(hamming_board.getValuePosition(0), hamming_board.getValuePosition(0) + this.tiles.length);
		}
		
		//getting zero tile to right
		while(hamming_board.getValuePosition(0) != Math.pow(this.tiles.length, 2) - 1) {
			hamming_board.switchTiles(hamming_board.getValuePosition(0), hamming_board.getValuePosition(0) + 1);
		}
		
		
		int sumupperfraction = 0;
		int sumlowerfraction = 0;
		for(int j=0;j < Math.pow(this.tiles.length, 2);j++) {
			for(int i=0;i < j;i++) {
				sumupperfraction += (hamming_board.getValuePosition(j) - hamming_board.getValuePosition(i));
				sumlowerfraction += (j - i);
			}
		}
		
		return sumupperfraction/sumlowerfraction > 0;
		
	}
	
	public int[][] getCheckableState() {
		return null;
//
	}
	
	private int getValuePosition(int value) {
		int counter = 0;
		while(value != this.tiles[(int) (counter / this.tiles.length)][counter % this.tiles.length]) {
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

