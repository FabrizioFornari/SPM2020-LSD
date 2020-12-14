package lsd.smartparking.model;

/* A day can have multiple rounds. For instance, if a park is opened from 4 PM to 6 PM it is okay.
 * But if we want to customize hours, such as from 4 PM to 5 PM, then from 7 PM to 8 PM, we
 * need to setup rounds. */
public class Round {
	
	private int start;
	private int end;
	
	public Round() { }
	
	public Round(int start, int end) {
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
