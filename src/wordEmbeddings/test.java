package wordEmbeddings;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.mysql.jdbc.CallableStatement;

public class test {
	
	static InputStreamReader read = null;
	static BufferedReader bufferedReader = null;
	static String wordListPath = "res/wordlist_final3.txt";
	//static ArrayList<String> wordList = new ArrayList<String>();
	static HashSet <String> wordList = new HashSet<String>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// ArrayList<String> wordList = new ArrayList<String>();
		
		try {
			read = new InputStreamReader(new FileInputStream(wordListPath));
			bufferedReader = new BufferedReader(read);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			String lineTxt = null;
			String word = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				word = lineTxt.trim();
				// wordList.add(word);
				wordList.add(word);
				//System.out.println(word);

			}
			bufferedReader.close();
			read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
System.out.println(wordList.size());
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
