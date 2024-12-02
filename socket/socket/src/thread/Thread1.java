package thread;

public class Thread1 extends Thread {
	static int n = 0;
	int step;
	String name;
	
	public Thread1(String name, int n) {
		super();
		this.name = name;
		this.step = n;
	}
	
	public void run() {
		while(true) {
			System.out.println(name + ": " + n);
			n += step;
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
