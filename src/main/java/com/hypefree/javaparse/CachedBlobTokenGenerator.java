package com.hypefree.javaparse;

final class CachedBlobTokenGenerator extends BlobTokenGenerator {
	private final SimpleCache<BlobBackedCharSequence> cache = new SimpleCache<BlobBackedCharSequence>();

	@Override
	public CharSequence subSequence(int start, int end) {
		return cache.get(new BlobBackedCharSequence(buffer, start, end));
	}
}
