package lsd.smartparking.model;

import java.util.HashMap;

/* Day, from 0 to 7 in the database, can contain opening hour and closing hour.
 * Plus, it can be customized with multiple rounds.
 * However you can also set the entire day, for instance Tuesday (2), closed. */
public class Day {
	
	private int start;
	private int end;
	private boolean closed;
	private HashMap<Integer, Round> rounds;
	
	public Day() { }
	
	public Day(int start, int end, boolean closed) {
		this.setStart(start);
		this.setEnd(end);
		this.setClosed(closed);
		this.setRounds(new HashMap<Integer, Round>());
	}

	public HashMap<Integer, Round> getRounds() {
		return rounds;
	}

	public void setRounds(HashMap<Integer, Round> round) {
		this.rounds = round;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}
}
