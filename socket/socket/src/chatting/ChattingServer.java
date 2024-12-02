package chatting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ChattingServer {
	static String calc(String exp) {
		StringTokenizer st = new StringTokenizer(exp);
		
		if(st.countTokens() != 3) return "Error";
		
		String result = null;
		int opr1 = Integer.parseInt(st.nextToken());
		String op = st.nextToken();
		int opr2 = Integer.parseInt(st.nextToken());
		switch (op) {
			case "+":
				result = Integer.toString(opr1 + opr2);
				break;
			case "-":
				result = Integer.toString(opr1 - opr2);
				break;
			case "*":
				result = Integer.toString(opr1 * opr2);
				break;
			case "/":
				result = Integer.toString(opr1 / opr2);
				break;
			case "^":
				result = Integer.toString((int)Math.pow(opr1, opr2));
				break;
			default:
				result = "잘못된 연산이 입력되었습니다.";
		}
		return result;
	}
	
	public static void main(String[] args) {
		BufferedReader in = null;
		BufferedWriter out = null;
		ServerSocket listener = null;
		Socket so = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			listener = new ServerSocket(9998); // listening requst.
			System.out.println("연결중...");
			so = listener.accept(); // 소켓 생성
			System.out.println("연결 완료.");
			
			in = new BufferedReader(new InputStreamReader(so.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
			
			while (true) {
				String rMsg = in.readLine();
				if (rMsg.equalsIgnoreCase("bye")) {
					break;
				}
				String msg = calc(rMsg);
				
//				System.out.println("상대: " + rMsg);
//				
//				System.out.print("나: ");
//				String msg = sc.nextLine();
				out.write(msg + "\n");
				out.flush();
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				sc.close();
				so.close();
				listener.close();	
			} catch (Exception e2) {
				System.out.println("연결이 끊어졌습니다. (문제 발생)");
			}
		}
	}

}
