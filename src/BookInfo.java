 /*Adonis Morales Assignment 3
  11/18/13
  Description: Reads a text file and stores its values into arrays.
 */

import java.util.Scanner;
import java.io.*;

public class BookInfo{

	public static String[] getBookNames() throws IOException {
		int count=0;

		final int SIZE = 7;

		String[] books = new String[SIZE];

		File myFile = new File("BookPrices.txt");
		Scanner inputFile = new Scanner(myFile);

		while(inputFile.hasNext() && count < books.length)
		{
			String str;

			str= inputFile.nextLine();

			String[] parts = str.split(",");
			books[count]=parts[0];
		 	count++;
		}
		inputFile.close();

		return books;
	}
	public static double[] getBookPrices() throws IOException{
		int count=0;
		final int SIZE = 7;
		double[] prices = new double[SIZE];

		File myFile = new File("BookPrices.txt");
		Scanner inputFile = new Scanner(myFile);

		while(inputFile.hasNext() && count < prices.length)
		{
			String str;

			str = inputFile.nextLine();
			String[] parts = str.split(",");

		 	prices[count]= Double.parseDouble(parts[1]) ;
		 	count++;
		}
		inputFile.close();

		return prices;
	}

}
