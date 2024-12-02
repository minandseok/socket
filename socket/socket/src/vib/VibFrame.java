package vib;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JFrame;

public class VibFrame extends JFrame implements Runnable {
    Thread th;

    public VibFrame() {
        setTitle("진동 프레임 쓰레드");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200, 200);
        setLocation(300, 300);
        setVisible(true);

        getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 스레드가 종료되었거나 중단된 경우 새로운 스레드를 시작
                if (th == null || !th.isAlive()) {
                    th = new Thread(VibFrame.this);
                    th.start();
                } else {
                    th.interrupt();
                }
            }
        });

        th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {
        Random rd = new Random();
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                return;
            }
            int x = getX() + (rd.nextInt() % 5);
            int y = getY() + (rd.nextInt() % 5);
            setLocation(x, y);
        }
    }

    public static void main(String[] args) {
        new VibFrame();
    }
}
