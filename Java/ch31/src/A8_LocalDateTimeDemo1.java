import java.time.LocalDateTime;

public class A8_LocalDateTimeDemo1 {

	public static void main(String[] args) {

		LocalDateTime dt = LocalDateTime.now();
		System.out.println("지금 시각: " + dt);
		
		//22시간 35분 뒤
		LocalDateTime mt = dt.plusHours(22);
		mt = mt.plusMinutes(35);
				
		System.out.println("미팅 시각: " + mt);
		
	}

}
