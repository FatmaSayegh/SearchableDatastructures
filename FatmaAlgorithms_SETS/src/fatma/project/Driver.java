package fatma.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {

    // File that contains 20k integers, each on a line.
    // these are then inserted innto the different datastructures
	public static String FILE_PATH = "/Users/Fatma/Desktop/int20k.txt";
	public static void main(String[] args) {

		File f = new File(FILE_PATH); // the file has 20k integers each on a line

		BinarySearchSet<Integer> bst = new BinarySearchSet<>();
		DoublySet<Integer> ds = new DoublySet<>();

		try {
			Scanner scanner = new Scanner(f);
			while (scanner.hasNext()) {
				int i = scanner.nextInt();
				bst.add(i);
				ds.add(i);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("Data Loaded! BST size: " + bst.setSize() + ", DS size: " + ds.setSize());

		System.out.println("Average time for BST: " + getAverageTime(new Runnable() {

			@Override
			public void run() {
				int rnd = (int) (Math.random() * 50000);
				// System.out.println("number is " + rnd);
				bst.isElement(rnd);

			}

		}, 100));

		System.out.println("Average time for Linked: " + getAverageTime(new Runnable() {

			@Override
			public void run() {
				int rnd = (int) (Math.random() * 50000);
				// System.out.println("number is " + rnd);
				ds.isElement(rnd);

			}

		}, 100));

		System.out.println("BST SIZE: " + bst.treeHeight());
	}

	public static double getTime(Runnable r) {
		long time = System.nanoTime();
		r.run();
		return System.nanoTime() - time;
	}

	public static double getAverageTime(Runnable r, int runs) {
		double total = 0;
		for (int i = 0; i < runs; i++) {
			int time = (int) getTime(r);
			// System.out.println("\ttime is " + time);
			total += time;
		}
		return total / runs;
	}

}
