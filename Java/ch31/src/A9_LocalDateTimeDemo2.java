import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class A9_LocalDateTimeDemo2 {

	public static void main(String[] args) {

		LocalDateTime today = LocalDateTime.of(2020, 4, 25, 11, 20);
		LocalDateTime fly1 = LocalDateTime.of(2020, 5, 14, 11, 15);
		LocalDateTime fly2 = LocalDateTime.of(2020, 5, 13, 15, 30);
		
		LocalDateTime myFly;
		
		if(fly1.isBefore(fly2))		myFly = fly1;
		else						myFly = fly2;
		
		Period day = Period.between(today.toLocalDate(), myFly.toLocalDate());
		Duration time = Duration.between(today.toLocalTime(), myFly.toLocalTime());
		
		System.out.println(day);
		System.out.println(time);
	}

}
