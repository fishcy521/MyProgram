import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/4/12.
 */
public class FileCreate {
	
	public static void main(String[] args) {
		Calendar beginCal = Calendar.getInstance();
		System.out.println(beginCal.get(Calendar.DAY_OF_WEEK));
		Calendar endCal = Calendar.getInstance();
		endCal.set(2017,4,20);
		
		int beginDay = beginCal.get(Calendar.DAY_OF_YEAR);
		int endDay = endCal.get(Calendar.DAY_OF_YEAR);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		while (true) {
			
			if (beginCal.after(endCal)) {
				break;
			}
			
			String strFileName = sdf.format(beginCal.getTime());
			beginCal.add(Calendar.DAY_OF_YEAR,1);
			
			if (beginCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
					||beginCal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				continue;
			}
			
			System.out.println(strFileName);
			
			
		}
	}
}
