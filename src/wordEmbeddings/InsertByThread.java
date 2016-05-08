package wordEmbeddings;

public class InsertByThread {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=0;i<340;i++){
			Thread thread = new InsertThread(String.valueOf(i));
			//Thread thread = new InsertThread(String.valueOf(o));
			thread.start();
		}
		/*Thread thread = new InsertThread(String.valueOf(2));
		thread.start();*/
	}

}
