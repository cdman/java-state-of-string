package com.hypefree.javaparse;

import java.util.zip.ZipEntry;

public final class Token {
	private final int row, column;
	private final CharSequence content;
	private final ZipEntry source;
	
	Token(int row, int column, CharSequence content, ZipEntry source) {
		this.row = row;
		this.column = column;
		this.content = content;
		this.source = source;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public CharSequence getContent() {
		return content;
	}

	public ZipEntry getSource() {
		return source;
	}
	
	@Override
	public String toString() {
		return String.format("%s@%d,%d", content, row, column);
	}
}
