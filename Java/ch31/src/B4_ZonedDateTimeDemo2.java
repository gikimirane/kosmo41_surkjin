import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class B4_ZonedDateTimeDemo2 {

	public static void main(String[] args) {

		ZonedDateTime dep = ZonedDateTime.of(
				LocalDateTime.of(2018, 7, 9, 14, 30), ZoneId.of("Asia/Seoul"));
		System.out.println("Departure: " + dep);
		
		ZonedDateTime arr = ZonedDateTime.of(
				LocalDateTime.of(2018, 7, 9, 17, 25), ZoneId.of("Europe/Paris"));
		System.out.println("Arrival: " + arr);
		
		System.out.println("Flight Time: " + Duration.between(dep, arr));

	}
}
