import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.swing.JOptionPane;

public class Pinging extends Thread {
	private String ip;
	int row;
	public Pinging(String ip, int i) {
		this.ip = ip;
		row=i;
	}

	public void run() {
		InputStream is = null;
		BufferedReader br = null;
		try {
			Runtime run = Runtime.getRuntime();
			Process p = run.exec("ping -a " + ip);
			IPScannerEx.stats[row][0] = ip;
			is = p.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.indexOf("[") >= 0) {
					IPScannerEx.stats[row][3] = line.substring(5, line.indexOf("["));
				}
				if (line.indexOf("ms") >= 0) {
					IPScannerEx.stats[row][1] = line.substring(line.indexOf("ms") - 1, line.indexOf("ms") + 2);
					IPScannerEx.stats[row][2] = line.substring(line.indexOf("TTL=") + 4, line.length());
					break;
				}
			}
			if(IPScannerEx.stats[row][1]==null) {
				IPScannerEx.stats[row][1]="[n/a]";
				IPScannerEx.stats[row][2]="[n/s]";
				IPScannerEx.stats[row][3]="[n/s]";
				IPScannerEx.stats[row][4]="[n/s]";
			}else if(IPScannerEx.stats[row][3]==null) {
				IPScannerEx.stats[row][3]="[n/a]";
			}
			if(!IPScannerEx.stats[row][1].equals("[n/a]")) {
				String portString = "";
				for (int port = 1; port <= 1024; port++) {
					try {
						Socket socket = new Socket();
						socket.connect(new InetSocketAddress(ip, port), 200);
						portString += port + " ";
						socket.close();
					} catch (SocketException | SocketTimeoutException e) {
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					IPScannerEx.jTable.repaint();
				}
				if (portString.equals("")) {
					IPScannerEx.stats[row][4] = "[n/a]";
				} else {
					IPScannerEx.stats[row][4] = portString;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		IPScannerEx.jTable.repaint();

	}
}
