import java.time.Duration;
import java.time.LocalTime;

public class A7_LocalTimeDuration {

	public static void main(String[] args) {
		
		LocalTime s = LocalTime.of(14, 24, 35);
		System.out.println("이용시작 시각: " + s);
		
		LocalTime e = LocalTime.of(17, 31, 19);
		System.out.println("종료시작 시각: " + e);
		
		Duration between = Duration.between(s, e);
		System.out.println("총 이용 시간: " + between);
	}

}
