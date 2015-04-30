package edu.virginia.pnl8zp;

import java.util.Comparator;

public class MovementVectorComparator implements Comparator<MovementVector> {

	@Override
	public int compare(MovementVector mv1, MovementVector mv2) {
		double c1 = mv1.costFunction();
		double c2 = mv2.costFunction();
		if(c1 > c2) {
			return 1;
		} else if (c1 < c2) {
			return -1;
		} else {
			return 0;
		}
	}

}
