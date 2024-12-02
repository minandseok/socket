package multi.chatting;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// java multi/chatting/MultiChattingServer
public class MultiChattingServer extends JFrame {
    private JTextArea log = new JTextArea(10, 20);
    private Vector<ServiceThread> clients = new Vector<>();
    
    public MultiChattingServer() {
        super("Chatting Server");
        log.append("Start Chatting Server.\n");
        log.setEditable(false);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(new JScrollPane(log), BorderLayout.CENTER);
        setVisible(true);
        
        new ServerThread().start(); // 서버 시작
    }
    
    class ServerThread extends Thread {
        @Override
        public void run() {
            ServerSocket listener = null;
            try {
                listener = new ServerSocket(9998);
                while (true) {
                    Socket socket = listener.accept();
                    ServiceThread client = new ServiceThread(socket);
                    clients.add(client);
                    client.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    class ServiceThread extends Thread {
        private Socket socket;
        private BufferedReader in;
        private BufferedWriter out;
        private String name;

        public ServiceThread(Socket socket) {
            this.socket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                name = in.readLine();
                broadcastMessage(name + "님이 입장하였습니다.");

                while (true) {
                    String msg = in.readLine();
                    if (msg == null) break;
                    broadcastMessage(name + ": " + msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                clients.remove(this);
                broadcastMessage(name + "님이 퇴장하셨습니다.");
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(String msg) {
            try {
                out.write(msg + "\n");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void broadcastMessage(String msg) {
    	String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String timeMsg = "[" + time + "] " + msg;

        log.append(timeMsg + "\n");
        for (ServiceThread client : clients) {
            client.sendMessage(timeMsg);
        }
    }

    public static void main(String[] args) {
        new MultiChattingServer();
    }
}
