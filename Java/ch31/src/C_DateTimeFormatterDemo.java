import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class C_DateTimeFormatterDemo {

	public static void main(String[] args) {

		ZonedDateTime date = ZonedDateTime.of(
				LocalDateTime.of(2019, 4, 5, 17, 20, 9), ZoneId.of("Asia/Seoul"));
		
		DateTimeFormatter fm1 = DateTimeFormatter.ofPattern("yy-M-d");
		DateTimeFormatter fm2 = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s");
		DateTimeFormatter fm3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss VV");
		DateTimeFormatter fm4 = DateTimeFormatter.ofPattern("yyyy-MMM-dd a hh:mm:ss");

		
		System.out.println(date.format(fm1));
		System.out.println(date.format(fm2));
		System.out.println(date.format(fm3));
		System.out.println(date.format(fm4));

	}

}
