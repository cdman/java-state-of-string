package com.hypefree.javaparse;

class CachedStringTokenGenerator extends StringTokenGenerator {
	private final SimpleCache<String> cache = new SimpleCache<String>();
	
	@Override
	public CharSequence subSequence(int start, int end) {
		String str = string.substring(start, end);
		return cache.get(str);
	}
}
