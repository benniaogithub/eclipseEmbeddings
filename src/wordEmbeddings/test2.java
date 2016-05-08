package wordEmbeddings;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String string="aba";
	/*	Pattern p= Pattern.compile("[a-z|\\_]+$");
		Matcher matcher=p.matcher(string);*/
		System.out.println(string.matches("[a-z|\\_]+$"));
		
	}

}
