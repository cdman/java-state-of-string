package com.hypefree.javaparse;

class BlobTokenGenerator implements TokenGenerator {
	protected byte[] buffer;

	@Override
	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	@Override
	public int length() {
		return buffer.length;
	}

	@Override
	public char charAt(int i) {
		return (char)buffer[i];
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return new BlobBackedCharSequence(buffer, start, end);
	}
}
