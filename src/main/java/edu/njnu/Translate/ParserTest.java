package edu.njnu.Translate;


import edu.njnu.Translate.SLR1Table.TableDriver;
import edu.njnu.Translate.element.Symbol;
import edu.njnu.Translate.element.TypeEnum;
import edu.njnu.Translate.element.Word;
import edu.njnu.Translate.wordscanner.Scanner;

import java.io.*;

public class ParserTest {
	public static void main(String[] args) {
		Scanner wordScanner = new Scanner();
		String s = "1.23+3+3.3*(1+3)*4";
		wordScanner.appendBuffer(s);

		try {
			TableDriver driver = new TableDriver(new PrintWriter(new File("ResultCode.txt")));
			boolean getNext = false;

			Word w = (Word)(wordScanner.getNext());
			while (!wordScanner.isEnd() || (wordScanner.isEnd() == true && getNext == false)) {
				if (getNext) {
					w = (Word)(wordScanner.getNext());
				}
				getNext = driver.next(w);
			}
			Word end = new Word(TypeEnum.End, "#");
			while (!driver.next(end));

			System.out.println("Result Code Saved to ResultCode.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
