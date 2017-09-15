package ru.relz.javacore2017;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.io.File;
import java.io.FileNotFoundException;

class TopWords {
	public static void main(String[] args) {
		if (args.length < 1) {
			printlnError("Input file not specified");
		}
		String inputFileName = args[0];
		forEachFileWord(inputFileName, new FileReading());
	}

	public static void printlnError(String msg) {
		System.out.println(String.format("Error: %s", msg));
		System.exit(1);
	}
	
	public static void forEachFileWord(String fileName, FileReadingInterface callback) {
		try {
			Scanner scanner = new Scanner(new  File(fileName));
			while (scanner.hasNext()) {
				callback.onWordRead(scanner.next());
			}
			callback.onFileReadingFinished();
		} catch (FileNotFoundException e) {
			printlnError("Input file " + fileName + " not found");
		}
	}

	interface FileReadingInterface {
		void onWordRead(String word);
		void onFileReadingFinished();
	}

	private static class FileReading implements FileReadingInterface {
		HashMap<String, Integer> wordFrequenceMap = new HashMap<String, Integer>();
		List<HashSet<String>> frequenceWordsList = new ArrayList<HashSet<String>>();

		@Override
		public void onWordRead(String word) {
			int oldWordFrequence = 0;
			int newWordFrequence = 1;
			if (wordFrequenceMap.containsKey(word)) {
				oldWordFrequence = wordFrequenceMap.get(word);
				newWordFrequence = oldWordFrequence + 1;
			}
			wordFrequenceMap.put(word, newWordFrequence);
			if (oldWordFrequence != 0) {
				frequenceWordsList.get(oldWordFrequence - 1).remove(word);
			}
			if (frequenceWordsList.size() < newWordFrequence) {
				frequenceWordsList.add(new HashSet<String>());
			}
			frequenceWordsList.get(newWordFrequence - 1).add(word);
		}
	
		@Override
		public void onFileReadingFinished() {
			ListIterator<HashSet<String>> listIterator = frequenceWordsList.listIterator(frequenceWordsList.size());
			while (listIterator.hasPrevious()) {
				HashSet<String> wordsSet = listIterator.previous();
				for (String word: wordsSet) {
					System.out.println(word);
				}
			}
		}
	}
}

