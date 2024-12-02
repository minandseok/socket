package thread.run;

public class ThreadRun2 {

	public static void main(String[] args) {
		Thread th1 = new Thread(new ThreadRun1("Run1", 2));
		th1.start();
		
		Thread th2 = new Thread(new ThreadRun1("Run2", 5));
		th2.start();

	}

}
