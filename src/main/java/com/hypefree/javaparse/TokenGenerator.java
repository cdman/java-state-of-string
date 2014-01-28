package com.hypefree.javaparse;

interface TokenGenerator {
	void setBuffer(byte[] buffer);

	int length();

	char charAt(int i);

	CharSequence subSequence(int start, int end);
}
