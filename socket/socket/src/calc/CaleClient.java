package calc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class CaleClient {

	public static void main(String[] args) {
		Socket socket = null;
		Scanner sc = new Scanner(System.in);
		BufferedReader in = null;
		BufferedWriter out = null;
		
		try {
			// 소켓: 서버 IP, 포트 번호
			socket = new Socket("localhost", 9999);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			while (true) {
				// 메시지 읽기
				String message = sc.nextLine();
				// 메시지 보내기
				out.write(message + "\n");
				out.flush();
				// bye를 입력 받았다면 종료
				if (message.equalsIgnoreCase("bye")) {
					break;
				}
			}
		} catch (IOException e) {
			System.out.print(e.getMessage());
		} finally {
			try {
				sc.close();
				socket.close();
			} catch (Exception e) {
				System.out.print(e.getMessage());	
			}
		}
	}

}
