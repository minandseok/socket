package multi.chatting;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MultiChattingClient extends JFrame {
    private String SERVER_DOWN = "서버와 연결이 종료되었습니다.";
    private String name = null;
    private JTextField nameIf = new JTextField(7);
    private JTextField sendIf = new JTextField(7);
    private Socket socket = null;
    private BufferedReader in = null;
    private BufferedWriter out = null;
    private JTextArea log = new JTextArea(7, 20);
    
    public MultiChattingClient(String name) {
    	super("Chatting Client");
        this.name = name;

        setSize(400, 600); // 창 크기 조정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        // 이름 표시 영역
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.add(new JLabel("이름: "), BorderLayout.WEST);
        nameIf.setText(name);
        nameIf.setEditable(false);
        namePanel.add(nameIf, BorderLayout.CENTER);
        c.add(namePanel, BorderLayout.NORTH);

        // 채팅 내용 표시 영역
        log.setEditable(false);
        c.add(new JScrollPane(log), BorderLayout.CENTER);

        // 전송 입력 영역
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel("전송 입력"), BorderLayout.NORTH);
        inputPanel.add(sendIf, BorderLayout.CENTER);
        c.add(inputPanel, BorderLayout.SOUTH);

        setVisible(true);
        
        setUpConnection();
        
        sendIf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tf = (JTextField)e.getSource();
                try {
                    String msg = tf.getText().trim();
                    if (msg.length() == 0) {
                        return;
                    }
                    out.write(msg + "\n");
                    out.flush();
                    tf.setText("");
                } catch (Exception e2) {
                    log.append(SERVER_DOWN + "\n");
                    return;
                }
            }
        });
        
        new ClientThread().start();
        
        try {
            out.write(name + "\n");
            out.flush();
        } catch (IOException e) {
            log.append(SERVER_DOWN + "\n");
        }
    }
    
    public void setUpConnection() {
        try {
            socket = new Socket("localhost", 9998);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    class ClientThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    String msg = in.readLine();
                    log.append(msg + "\n");
                }
            } catch (IOException e) {
                log.append(SERVER_DOWN + "\n");
            }
        }
    }
    
    public static void main(String[] args) {
        JFrame nameFrame = new JFrame("이름 입력");
        JTextField nameField = new JTextField(10);
        
        nameFrame.setSize(250, 150);
        nameFrame.setLayout(new GridLayout(2, 1));
        nameFrame.setLocationRelativeTo(null);
        nameFrame.add(nameField);
        nameFrame.setResizable(false);
        nameFrame.setVisible(true);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // JLabel 추가
        JLabel label = new JLabel("이름 입력 ");
        panel.add(label, gbc);
        
        gbc.gridy = 1;
        panel.add(nameField, gbc);

        nameFrame.add(panel, BorderLayout.CENTER);
        nameFrame.setVisible(true);
        
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                nameFrame.dispose(); // 이름 입력 프레임 닫기
                new MultiChattingClient(name); // 채팅 UI 열기
            }
        });
    }
}
