package wordEmbeddings02;

import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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

public class UpdateThread02 extends Thread{
	static final String url = "jdbc:mysql://127.0.0.1/wordembedding02";
	static final String name = "com.mysql.jdbc.Driver";
	static final String user = "root";
	static final String password = "su";
	Connection conn = null;
	CallableStatement cstmt = null;
	private Logger logger = Logger.getLogger(UpdateThread02.class);
	String filePath = "I:\\extracted\\";
	InputStreamReader read = null;
	BufferedReader bufferedReader = null;
	private String index = null;
	//ArrayList<String> wordList = new ArrayList<String>();

	/*public static void main(String args[]){
		new testUpdate().start1();
	}*/
	public UpdateThread02(String index) {
		this.index = index;
		filePath = filePath + index;

		// TODO Auto-generated constructor stub
	}
	public void run() {
		PropertyConfigurator.configure("config/log4j.properties");
		// 加载驱动
		
		//System.out.println(wordList.size());
		try {
			Class.forName(name);// 指定连接类型
			conn = DriverManager.getConnection(url, user, password);// 获取连接
			cstmt = (CallableStatement) conn.prepareCall("{CALL pro_update(?,?,?)}");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		proFiles(filePath);
		//proFiles(filePath);
		try {
			cstmt.close();
			conn.close();
		} catch (Exception e) {
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
					logger.debug(file.getName());
					try {
						read = new InputStreamReader(new FileInputStream(file));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage());
					}// 考虑到编码格式
					bufferedReader = new BufferedReader(read);
					int count = 0;
					try {
						while ((lineTxt = bufferedReader.readLine()) != null) {

							String[] item = lineTxt.split("----");
							relation = item[2].trim();
							word = item[0].trim().toLowerCase();
							reference = item[1].trim().toLowerCase();
							//System.out.println(wordList.contains(word));
							//System.out.println(wordList.size());
							//matcher=p.matcher(word);
							//wordisWord = matcher.find();
							try {
								cstmt.setString(1, word);
								cstmt.setString(2, reference);
								cstmt.setString(3,"relation" );
								cstmt.execute();
								System.out.println(count++);
							} catch (Exception e) {
								//System.out.println(wordList.contains(word));
								logger.error(e.getMessage());	
							}
							
							
						}
						bufferedReader.close();
						read.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						logger.error(e.toString());
					}
				// file.getAbsolutePath();	
			}
		}
	}

}