import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/4/13.
 */
public class TestReg {
	
	/*public static void main(String[] args) {
		String str = "abcc123efg4hi56jk7890abc";
		String reg = "^.*(abc)$";
		
		boolean bln = str.matches(reg);
		System.out.println(bln);
	}*/
	
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("^[A-z]+[A-z0-9_-]*\\@[A-z0-9]+\\.[A-z]+$");
		String text = "test_123@126.com";
		Matcher m = pattern.matcher(text);
		System.out.println(m.matches());
		
	}
}
