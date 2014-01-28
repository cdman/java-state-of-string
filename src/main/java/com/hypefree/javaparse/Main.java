package com.hypefree.javaparse;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.zip.*;

public final class Main {
	private static Logger LOGGER = Logger.getLogger(Main.class.getName());

	private final TokenGenerator tokenGenerator;

	public Main(TokenGenerator stringTokenGenerator) {
		this.tokenGenerator = stringTokenGenerator;
	}

	private void processBuffer(byte[] buffer, List<Token> tokens,
			TokenGenerator tokenGenerator, ZipEntry zipEntry) {
		tokenGenerator.setBuffer(buffer);

		final int length = tokenGenerator.length();
		int row = 1, column = 0;
		int i = 0;
		while (i < length) {
			char c = tokenGenerator.charAt(i++);
			++column;

			if (c == '\n') {
				++row;
				column = 0;
			} else if (Character.isJavaIdentifierStart(c)) {
				int tokenStartI = i;
				while (i < length - 1) {
					c = tokenGenerator.charAt(++i);
					column++;
					if (!Character.isJavaIdentifierPart(c)) {
						break;
					}
				}
				tokens.add(new Token(row, column, tokenGenerator.subSequence(
						tokenStartI, i), zipEntry));
			} else {
				while (i < length - 1) {
					c = tokenGenerator.charAt(++i);
					column++;
					if (Character.isJavaIdentifierStart(c)
							|| Character.isWhitespace(c)) {
						break;
					}
				}
			}
		}

	}

	private List<Token> parseJavaFilesFromZip(String fileName) throws Exception {
		ZipFile zipFile = new ZipFile(fileName);
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		List<Token> tokens = new ArrayList<Token>(20 * 1024 * 1024);
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			if (!entry.getName().endsWith(".java")) {
				continue;
			}
			if (entry.isDirectory()) {
				continue;
			}

			InputStream input = zipFile.getInputStream(entry);
			byte[] buffer = new byte[(int) entry.getSize()];
			input.read(buffer);

			LOGGER.info("Processing " + entry.getName());
			processBuffer(buffer, tokens, tokenGenerator, entry);
		}
		zipFile.close();
		return tokens;
	}

	public static void main(String[] args) throws Exception {
		Main parser = new Main(new NullTokenGenerator());
//		Main parser = new Main(new StringTokenGenerator());
//		Main parser = new Main(new CachedStringTokenGenerator());
//		Main parser = new Main(new InternedStringTokenGenerator());
//		Main parser = new Main(new BlobTokenGenerator());
//		Main parser = new Main(new CachedBlobTokenGenerator());

		long time = System.currentTimeMillis();
		List<Token> tokens = parser
				.parseJavaFilesFromZip("/home/cdman/Downloads/jdk8-src.zip");
		time = (System.currentTimeMillis() - time) / 1000;

		for (int i = 0; i < 3; ++i) {
			LOGGER.info("Trying to GC really hard...");
			System.gc();
			Thread.sleep(300);
		}

		long usedMemoryMB = (Runtime.getRuntime().totalMemory() - Runtime
				.getRuntime().freeMemory()) / (1024 * 1024);
		LOGGER.info(String.format(
				"Generated %d tokens in %d seconds (using %d MB memory)",
				tokens.size(), time, usedMemoryMB));
	}
}
