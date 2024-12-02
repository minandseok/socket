package thread;

public class Thread2 extends Thread {
	public static void main(String[] args) {
		Thread1 th1 = new Thread1("T1", 2);
		th1.start();
		Thread1 th2 = new Thread1("T2", 5);
		th2.start();
	}
}
