package wordEmbeddings02;

public class updateByThread {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=0;i<2;i++){
			//Thread thread = new UpdateThread(String.valueOf(i));
			//Thread thread = new InsertThread(String.valueOf(o));
			Thread thread = new UpdateThread02(String.valueOf(i));
			thread.start();
		}
		/*Thread thread = new InsertThread(String.valueOf(2));
		thread.start();*/
	}
}
