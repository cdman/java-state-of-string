package com.hypefree.javaparse;

import java.nio.charset.Charset;

final class BlobBackedCharSequence implements CharSequence {
	private static final Charset ASCII_CHARSET = Charset.forName("ascii");

	private final byte[] buffer;
	private final int start, length;

	BlobBackedCharSequence(byte[] buffer, int start, int end) {
		this.buffer = buffer;
		this.start = start;
		this.length = end - start;
	}

	@Override
	public char charAt(int index) {
		// TODO: add bounds checking
		return (char) buffer[start + index];
	}

	@Override
	public int length() {
		return length;
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		// TODO: add sanity checking (end >= start, they are positive, etc)
		return new BlobBackedCharSequence(buffer, this.start + start,
				this.start + end);
	}

	@Override
	public String toString() {
		return new String(buffer, start, length, ASCII_CHARSET);
	}

	@Override
	public int hashCode() {
		int result = 0;
		final int end = start + length;
		for (int i = start; i < end; ++i) {
			result ^= buffer[i];
			result ^= (result << 21);
			result ^= (result >>> 35);
			result ^= (result << 4);
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BlobBackedCharSequence)) {
			return false;
		}
		BlobBackedCharSequence that = (BlobBackedCharSequence) o;
		if (this.length != that.length) {
			return false;
		}

		int a = this.start, b = that.start;
		for (int i = 0; i < length; ++i) {
			if (this.buffer[a++] != that.buffer[b++]) {
				return false;
			}
		}

		return true;
	}
}
