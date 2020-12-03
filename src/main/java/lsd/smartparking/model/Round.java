package lsd.smartparking.model;

public class Round {
	
	private int start;
	private int end;
	
	public Round() { }
	
	public Round(int start, int end, boolean closed) {
		this.setStart(start);
		this.setEnd(end);
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
