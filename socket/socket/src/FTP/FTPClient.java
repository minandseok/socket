package FTP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Scanner;

public class FTPClient {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		Socket socket = new Socket("localhost", 9999);
		
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		
		while (true) {
			System.out.println("download or upload: ");
			String choice = sc.nextLine();
			if (choice.equalsIgnoreCase("download")) {
				dos.writeUTF("download");
				
				int length = dis.readInt();
			
				for (int i = 0; i < length; i++) {
					String getName = dis.readUTF();
					System.out.println(getName);
				}
				
				System.out.println("다운 받을 파일 명을 입력하세요: ");
				String downFile = sc.nextLine();
				
				dos.writeUTF(downFile);
				
				byte[] fileContents = new byte[(int)dis.readLong()];
				dis.readFully(fileContents);
				
				File dest = new File("c:/test2/" + downFile);
				FileOutputStream fos = new FileOutputStream(dest);
				DataOutputStream dfos = new DataOutputStream(fos);
				dfos.write(fileContents);
				dfos.flush();
				dfos.close();
				
				System.out.println(dest.getName() + "수신 완료.");
			} else {
				dos.writeUTF("upload");
				
				File dir = new File("c:/test2/");
				File[] files = dir.listFiles();
				
				for (File file : files) {
					System.out.println(file.getName());
				}
				System.out.println("upload할 파일을 입력하세요.");
				String upFile = sc.nextLine();
				dos.writeUTF(upFile);
				
				File file = new File("c:/test2/" + upFile);
				FileInputStream fis = new FileInputStream(file);
				DataInputStream fsis = new DataInputStream(fis);
				
				byte[] fileContents = new byte[(int) file.length()];
				fsis.readFully(fileContents);
				
				dos.writeLong(fileContents.length);
				dos.write(fileContents);
				dos.flush();
			}
			
			System.out.println("종료하시겠습니까? y or n: ");
			String menu = sc.nextLine();
			if (menu.equals("y") || menu.equals("yes")) {
				System.exit(0);
			}
			
		}
	}

}
