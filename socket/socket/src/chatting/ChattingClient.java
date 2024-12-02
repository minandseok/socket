package chatting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChattingClient {

	public static void main(String[] args) {
		BufferedReader in = null;
		BufferedWriter out = null;
		Socket so = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			so = new Socket("localhost", 9998);
			in = new BufferedReader(new InputStreamReader(so.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
			
			while (true) {
				System.out.print("나: ");
				String msg = sc.nextLine();
				if (msg.equalsIgnoreCase("bye")) {
					out.write(msg + "\n");
					out.flush();
					break;
				}
				out.write(msg + "\n");
				out.flush();
				
				String rMsg = in.readLine();
//				System.out.println("상대: " + rMsg);
				System.out.println("계산결과: " + rMsg);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				sc.close();
				so.close();	
			} catch (Exception e2) {
				System.out.println("연결이 끊어졌습니다. (문제 발생)");
			}
		}
	}

}
