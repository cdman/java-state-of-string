package com.hypefree.javaparse;

final class SimpleCache<T> {
	private final static int CACHE_MASK = 0xFFF;
	@SuppressWarnings("unchecked")
	private final T[] cache = (T[]) new Object[4096];

	T get(T value) {
		int hash = value.hashCode() & CACHE_MASK;
		T proposedValue = cache[hash];
		if (proposedValue != null && proposedValue.equals(value)) {
			return proposedValue;
		}

		cache[hash] = value;
		return value;
	}
}
