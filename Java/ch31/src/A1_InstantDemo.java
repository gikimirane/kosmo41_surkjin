import java.time.Duration;
import java.time.Instant;

public class A1_InstantDemo {

	public static void main(String[] args) {

		Instant s = Instant.now();
		System.out.println("시작: " + s.getEpochSecond());
		System.out.println("Time flies like an arrow.");
		
		Instant e = Instant.now();
		System.out.println("종료: " + e.getEpochSecond());

		Duration between = Duration.between(s, e);
		System.out.println("밀리 초 단위 차: " + between.toMillis());
		System.out.println("밀리 초 단위 차: " + Duration.between(s, e).toMillis());

	}

}
