package wordEmbeddings;

public class InsertByThread0_59 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=9;i<12;i++){
			Thread thread = new InsertThread(String.valueOf(i));
			//Thread thread = new InsertThread(String.valueOf(o));
			thread.start();
		}
		/*Thread thread = new InsertThread(String.valueOf(2));
		thread.start();*/
	}

}
