package serial;
public class Main {

	public static void main(String[] args) {
		try {
			(new Serial()).connect("COM4");
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
