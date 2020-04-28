package gna;

import java.util.Comparator;

public class StateComparator implements Comparator<State> {
	@Override
	public int compare(State state1,State state2) {
		return state1.getCost() - state2.getCost();
	}
}
