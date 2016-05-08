package wordEmbeddings02;

public class ExtractByThread {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for(int j=270;j<300;j++){
			//Thread thread = new UpdateThread(String.valueOf(i));
			//Thread thread = new InsertThread(String.valueOf(o));
			Thread thread = new ExtractTriThread(String.valueOf(j));
			thread.start();
		}
	
		
		/*Thread thread = new InsertThread(String.valueOf(2));
		thread.start();*/
	}
}
