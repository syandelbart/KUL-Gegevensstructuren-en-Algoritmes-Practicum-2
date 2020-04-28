package gna;

import libpract.PriorityFunc;

public class State {
	private State prev_state;
	private State next_state;
	private Board stateBoard;
	private int cost;
	
	//getters
	public Board getBoard() {
		return this.stateBoard;
	}
	
	public State prev() {
		return this.prev_state;
	}
	
	public State next() {
		return this.next_state;
	}
	
	public void setPrev(State prev_state) {
		this.prev_state = prev_state;
	}
	
	public void setNext(State next_state) {
		this.next_state = next_state;
	}
	
	public State getInitialState() {
		State cursor = this;
		while(cursor.prev_state != null) {
			cursor = cursor.prev_state;
		}
		return cursor;
	}
	
	public void setCost(int value) {
		this.cost = value;
	}
	
	public int getCost() {
		return this.cost;
	}
	
	//constructor
	public State(Board stateBoard) {
		this.stateBoard = stateBoard;
	}
	
	
	//get movement length for the whole thing
	public int getLength() {
		int counter = 1;
		if(this.prev_state == null && this.next_state == null) {
			//is only sibling
			return counter;
		} else if(this.prev_state == null) {
			//is first sibling
			State cursor = this;
			while(cursor.next_state != null) {
				cursor = cursor.next_state;
				counter++;
			}
		} else if(this.next_state == null) {
			//is last sibling
			State cursor = this;
			while(cursor.prev_state != null) {
				cursor = cursor.prev_state;
				counter++;
			}
		} else {
			//is somewhere in mid
			State cursor = this;
			//check prev sibling
			while(cursor.prev_state != null) {
				cursor = cursor.prev_state;
				counter++;
			}
			cursor = this;
			while(cursor.next_state != null) {
				cursor = cursor.next_state;
				counter++;
			}
		}
		
		return counter;
	}
	
	public boolean isGoal() {
		return this.stateBoard.isGoal();
	}
}
