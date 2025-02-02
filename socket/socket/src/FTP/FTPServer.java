package FTP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer {
	public static void main(String[] args) throws Exception {
		try {
			ServerSocket server = new ServerSocket(9999);
			Socket socket = null;
			while (true) {
				System.out.println("클라이언트 접속 대기중");
				socket = server.accept();
				System.out.println(socket.getInetAddress() + "접속");
				try {
					while (true) {
						DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
						DataInputStream dis = new DataInputStream(socket.getInputStream());
						
						String choice = dis.readUTF();
						if (choice.equalsIgnoreCase("download")) {
							File dir = new File("c:/test/");
							File[] files = dir.listFiles();
							
							dos.writeInt(files.length);
							for (File file : files) {
								dos.writeUTF(file.getName());
							}
							String loadingFile = dis.readUTF();
							
							File file = new File("c:/test/" + loadingFile);
							FileInputStream fis = new FileInputStream(file);
							DataInputStream fsis = new DataInputStream(fis);
							
							byte[] fileContents = new byte[(int) file.length()];
							fsis.readFully(fileContents);
							
							dos.writeLong(fileContents.length);
							dos.write(fileContents);
							dos.flush();
						} else {
							String upFileName = dis.readUTF();
							
							byte[] fileContents = new byte[(int)dis.readLong()];
							dis.readFully(fileContents);
							
							File dest = new File("c:/test/" + upFileName);
							FileOutputStream fis = new FileOutputStream(dest);
							DataOutputStream fsis = new DataOutputStream(fis);
							fsis.write(fileContents);
							fsis.flush();
							fsis.close();
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
		} catch (Exception e) {
			System.out.println("연결 오류.");
		}
	}

}
