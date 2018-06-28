import java.time.LocalDate;

public class DateOfExec {

	static String date;
	static {
		LocalDate nDate = LocalDate.now();
		date = nDate.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(date);
	}

}
