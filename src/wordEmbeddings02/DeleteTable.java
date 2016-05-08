package wordEmbeddings02;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.stream.events.StartDocument;

import org.apache.log4j.Logger;

import com.mysql.jdbc.CallableStatement;

public class DeleteTable {
	
	static final String url = "jdbc:mysql://127.0.0.1/wordembedding02";
	static final String name = "com.mysql.jdbc.Driver";
	static final String user = "root";
	static final String password = "su";
	Connection conn = null;
	CallableStatement cstmt = null;
	private Logger logger = Logger.getLogger(InsertThread.class);

	InputStreamReader read = null;
	BufferedReader bufferedReader = null;
	private String index = null;
	ArrayList<String> wordList = new ArrayList<String>();
	private String wordListPath = "res/wordlist_final3.txt";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// ArrayList<String> wordList = new ArrayList<String>();
		new DeleteTable().start();
		
	}
	void start(){

		
		
		try {
			Class.forName(name);// 指定连接类型
			conn = DriverManager.getConnection(url, user, password);// 获取连接
			cstmt = (CallableStatement) conn.prepareCall("{call pro_deleteTable(?)}");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
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
				try {
					cstmt.setString(1, "t_"+word);
					cstmt.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//wordList.add(word);
				//System.out.println(word);

			}
			bufferedReader.close();
			read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		try {
			cstmt.close();
			conn.close();
		} catch (Exception e1) {
			logger.error(e1.getMessage());
		}

	

	}
	}
}
