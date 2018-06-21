import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Pinging extends Thread {

	Object[] msg;
	String ip;

	public Pinging(String ip) {
		this.ip = ip;
		msg = new Object[4];
	}

	public void run() {
		InputStream is = null;
		BufferedReader br = null;

		try {
			Runtime run = Runtime.getRuntime();
			Process p = run.exec("ping -a " + ip);
			msg[0] = ip;
			is = p.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			String line = null;

			while ((line = br.readLine()) != null) {

				//System.out.println(line);

				if (line.indexOf("[") >= 0) {
					msg[3] = line.substring(5, line.indexOf("["));
				}

				if (line.indexOf("ms") >= 0) {
					msg[1] = line.substring(line.indexOf("ms") - 1, line.indexOf("ms") + 2);
					msg[2] = line.substring(line.indexOf("TTL=") + 4, line.length());
					break;
				}

			}
			if (line != null) {
			}

		} catch (Exception e) {
		} finally {

			try {
				if (br != null)
					br.close();
			} catch (Exception ex2) {

			}

			try {
				if (is != null)
					is.close();
			} catch (Exception ex2) {

			}

		}
	}

	public Object[] getMsg() {

		try {
			join();
		} catch (InterruptedException interrexcept) {
			return null;
		}

		return msg;
	}

}
