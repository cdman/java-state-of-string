package com.hypefree.javaparse;

import java.nio.charset.Charset;

class StringTokenGenerator implements TokenGenerator {
	protected String string;
	
	@Override
	public void setBuffer(byte[] buffer) {
		string = new String(buffer, Charset.forName("ascii"));
	}

	@Override
	public int length() {
		return string.length();
	}

	@Override
	public char charAt(int i) {
		return string.charAt(i);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return string.substring(start, end);
	}
}
