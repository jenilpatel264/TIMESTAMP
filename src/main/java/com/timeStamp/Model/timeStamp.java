package com.timeStamp.Model;

public class timeStamp {

	private int start;

	public int getStart() {
		return start;
	}

	@Override
	public String toString() {
		return "timeStamp [start=" + start + ", end=" + end + "]";
	}

	public timeStamp(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	private int end;

}
