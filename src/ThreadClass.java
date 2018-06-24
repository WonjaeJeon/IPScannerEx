import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ThreadClass extends Thread{
	int port, row;
	String ip;

	ThreadClass(int port, String ip, int i) {
		this.port = port;
		row = i;
		this.ip = ip;
	}

	public void run() {
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(ip, port), 200);
			socket.close();
			IPScannerEx.stats[row][4] = port;
		} catch (SocketException | SocketTimeoutException e) {
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		IPScannerEx.jTable.repaint();
	}

}
