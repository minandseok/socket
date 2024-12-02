package thread.run;

public class ThreadRun1 implements Runnable {
	int n = 0;
	int step;
	String name;
	
	public ThreadRun1(String name, int n) {
		super();
		this.name = name;
		this.step = n;
	}

	@Override
	public void run() {
		
		while(true) {
			System.out.println(name + ": " + n);
			System.out.println("--------------");
			n += step;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
