package wordEmbeddings;

public class InsertByThread180_239 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=180;i<239;i++){
			Thread thread = new InsertThread(String.valueOf(i));
			//Thread thread = new InsertThread(String.valueOf(o));
			thread.start();
		}
		/*Thread thread = new InsertThread(String.valueOf(2));
		thread.start();*/
	}

}
