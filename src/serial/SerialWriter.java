package serial;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialWriter implements Runnable{
	OutputStream out;
	
	public SerialWriter(OutputStream out) {
		this.out = out;
	}
	
	public void run() {
		try {
				int c = 0;
				while ((c = System.in.read()) > -1 ) {
					if(c == 49) {
						this.out.write(1);
					}
				}	
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
