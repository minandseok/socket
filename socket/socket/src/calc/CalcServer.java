package calc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class CalcServer {

	public static void main(String[] args) {
		Socket socket = null;
		ServerSocket listener = null;
		BufferedReader in = null;
		BufferedWriter out = null;
		
		try {
			// Listening Request
			listener = new ServerSocket(9999);
			System.out.println("Connecting...");
			// Save Request in socket
			socket = listener.accept();
			System.out.println("-----연결 완료-----");
			// 입출력
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			while (true) {
				String inputMessage = in.readLine();
				// bye를 입력 받았다면 연결 종료
				if (inputMessage.equalsIgnoreCase("bye")) {
					System.out.println("상대가 연결을 종료했습니다.");
					break;
				}
				System.out.println("상대: " + inputMessage);	
			}
		} catch (IOException e) {
			System.out.print(e.getMessage());
		} finally {
			try {
				socket.close();
				listener.close();
			} catch (IOException e) {
				System.out.print(e.getMessage());	
			}
		}
	}

}
