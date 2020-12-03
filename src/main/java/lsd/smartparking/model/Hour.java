package lsd.smartparking.model;

import java.util.HashMap;

public class Hour {
	
	private int start;
	private int end;
	private boolean closed;
	private HashMap<Integer, Round> rounds;
	
	public Hour() { }
	
	public Hour(int start, int end, boolean closed) {
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
