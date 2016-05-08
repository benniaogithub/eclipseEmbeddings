package wordEmbeddings02;

import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.events.StartDocument;

import org.apache.log4j.Logger;

import com.mysql.jdbc.CallableStatement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.jmx.LoggerDynamicMBean;

public class ExtractTriThread extends Thread{
//	static final String url = "jdbc:mysql://127.0.0.1/wordembedding02";
//	static final String name = "com.mysql.jdbc.Driver";
//	static final String user = "root";
//	static final String password = "su";
	Connection conn = null;
	CallableStatement cstmt = null;
	private Logger logger = Logger.getLogger(ExtractTriThread.class);
//	String filePath = "I:\\dataoutput\\";
	String filePath = "I:\\数据\\corpus\\totalData\\";
	String outfilePath = "I:\\数据\\corpus\\extractedAdd\\";
	InputStreamReader read = null;
	BufferedReader bufferedReader = null;
	OutputStreamWriter  writer = null;
	BufferedWriter  bufferedWriter = null;
	private String index = null;
	//ArrayList<String> wordList = new ArrayList<String>();
	HashSet <String> wordList = new HashSet<String>();
	HashSet <String> relationList = new HashSet<String>();
	private String wordListPath = "res/wordNotExist3.txt";
	private String RelationListPath ="res/relations.txt";
	Pattern p= Pattern.compile("[a-z|\\_]+$");
	Matcher matcher = null;
	
	boolean referenceisWord = false;
	/*public static void main(String args[]){
		new testUpdate().start1();
	}*/
	public ExtractTriThread(String index) {
		this.index = index;
		filePath = filePath + index;

		// TODO Auto-generated constructor stub
	}
	public void run() {
		PropertyConfigurator.configure("config/log4j.properties");
		// 加载驱动
		importRelationList();
		importWordlist();
		//System.out.println(wordList.size());
		
		
		proFiles(filePath);
		//proFiles(filePath);
		

	}

	void importWordlist() {
		try {
			read = new InputStreamReader(new FileInputStream(wordListPath));
			bufferedReader = new BufferedReader(read);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		
		String lineTxt = null;
		String word = null;
		int count = 0;
		try {
			while ((lineTxt = bufferedReader.readLine()) != null) {
				word = lineTxt.trim();
				wordList.add(word);
			}
			bufferedReader.close();
			read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}
	
	void importRelationList() {
		try {
			read = new InputStreamReader(new FileInputStream(RelationListPath ));
			bufferedReader = new BufferedReader(read);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		
		String lineTxt = null;
		String relation = null;
		try {
			while ((lineTxt = bufferedReader.readLine()) != null) {
				relation = lineTxt.trim();
				relationList.add(relation);
			}
			bufferedReader.close();
			read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}
	
	void proFiles(String filePath) {
		File root = new File(filePath);
		File[] files = root.listFiles();
		
		String lineTxt = null;
		String word = null;
		String relation = null;
		String reference = null;
		//boolean wordisWord = false;
		
		for (File file : files) {
			if (file.isDirectory()) {
				proFiles(file.getAbsolutePath());
			} else {

					File extractedFile = new File(outfilePath+index);
					extractedFile.mkdirs();
					logger.debug(file.getName());
					try {
						writer = new OutputStreamWriter(new FileOutputStream(new File(outfilePath+index+"\\"+file.getName())));
						read = new InputStreamReader(new FileInputStream(file));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage());
					}// 考虑到编码格式
					bufferedReader = new BufferedReader(read);
					bufferedWriter = new BufferedWriter(writer);
					int count = 0;
					try {
						while ((lineTxt = bufferedReader.readLine()) != null) {

							String[] item = lineTxt.split("\t");
							if (item.length < 4) {
								continue;
							}
							relation = item[1].trim().split(":")[0];
							word = item[2].trim().toLowerCase();
							reference = item[3].trim().toLowerCase();
							//System.out.println(wordList.contains(word));
							//System.out.println(wordList.size());
							//matcher=p.matcher(word);
							//wordisWord = matcher.find();
							
							matcher=p.matcher(reference);
							referenceisWord = matcher.matches();	
							if(wordList.contains(word)&&referenceisWord&&relationList.contains(relation)){
								bufferedWriter.write(word+"----"+reference+"----"+relation+"\n");
							}
							
						}
						bufferedReader.close();
						read.close();
						bufferedWriter.close();
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						logger.error(e.toString());
					}
				// file.getAbsolutePath();	
			}
		}
	}

}