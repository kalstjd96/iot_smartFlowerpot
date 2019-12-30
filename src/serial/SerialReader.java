package serial;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;;

public class SerialReader implements Runnable{
	InputStream in;
	public static String Data = "";
	
	public SerialReader(InputStream in) {
		this.in = in;
	}
	
	public void run() {
		SerialBean bean = new SerialBean();
		byte[] buffer = new byte[1024];
		int len = -1;
		int temp = 0;
		//Timer m_timer = new Timer();
		
		long now_time = (System.currentTimeMillis()/1000); 
		
		//SimpleDateFormat real_date = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		//String nowTime = real_date.format(new Date(real_time));
		long future_time;
		
		Runnable able = new Runnable() {
			@Override
			public void run() {
				long time = System.currentTimeMillis(); 
				SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String str = dayTime.format(new Date(time));
				bean.setTime(str);
				if(new SerialDB().insertDB(bean)== true) {
					//m_timer.cancel();
					bean.setTemperature(null);
					bean.setHumidity(null);
				}
				System.out.println("DB 데이터 전송 성공");
				
			}
		};
		
		String msg = "";
		try {
			while ((len = this.in.read(buffer)) > -1 ) {
				future_time = (System.currentTimeMillis()/1000); 
				
				String iaa = new String(buffer, 0, len);
//				System.out.println(iaa);
				iaa=iaa.trim();
				msg+=iaa;
				msg=msg.trim();
				
				
				if(iaa.equals("/")) {
					int idxMid = msg.indexOf(",");
					int idxEnd = msg.indexOf("/");
					String sub_msg1 = msg.substring(0,idxMid);
					String sub_msg2 = msg.substring(idxMid+1,idxEnd);
					
					int temp_idx = sub_msg1.indexOf(".");
					int hum_idx = sub_msg1.indexOf(".");
					
					String temp_value = sub_msg1.substring(0,temp_idx);
					String hum_value = sub_msg2.substring(0,hum_idx);
					
					System.out.println(temp_value);
					System.out.println(hum_value);
					
					bean.setTemperature(temp_value);
					bean.setHumidity(hum_value);
					
					long time = System.currentTimeMillis();
					SimpleDateFormat dayTime = new SimpleDateFormat("yyyy년MM월dd일-hh:mm:ss");
					String str = dayTime.format(new Date(time));
					bean.setTime(str);
					new SerialDB().insertDB(bean);
					System.out.println("DB 데이터 전송 성공");
					
					msg="";	
				}
				
				
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}