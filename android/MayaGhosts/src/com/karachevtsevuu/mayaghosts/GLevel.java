package com.karachevtsevuu.mayaghosts;

import java.util.Vector;

import android.util.Log;

public class GLevel {
	private Vector<Vector<Integer>> data;
	private Vector<Integer> curLine;
	private String name;
	private String note_eng;
	private String note_rus;
	private Object number;
	private Object title_eng;
	private Object title_rus;
	public GLevel() {
		data = new Vector<Vector<Integer>>();
	}
	public void createLine() {
		curLine = new Vector<Integer>();
	}
	public void addToLine(int value) {
		curLine.add(Integer.valueOf(value));
	}
	public void endLine() {
		data.add(curLine);
	}
	
	public Vector<Vector<Integer>> getData() {
		return data;
	}
	public Vector<Integer> getLine(int ind) {
		return data.get(ind);
	}
	public int getValue(int x, int y) {
		int type = 0;
		try {
			type = data.get(y).get(x).intValue();
		} catch(Exception ex) {
			Log.d("GameDebug", ex.toString());
		}
		return type;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String pname) {
		name = pname;		
	}
	public void setNoteEng(String pnote_eng) {
		note_eng = pnote_eng;
	}
	public void setNoteRus(String pnote_rus) {
		note_rus = pnote_rus;		
	}
	public void setLevelNumber(String pnumber) {		
		number = pnumber;
	}
	public void setTitleEng(String ptitle_eng) {
		title_eng = ptitle_eng;
	}
	public void setTitleRus(String ptitle_rus) {
		title_rus = ptitle_rus;
	}
}
