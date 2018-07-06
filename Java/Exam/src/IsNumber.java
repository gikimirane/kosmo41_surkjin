
public class IsNumber {

	public static void main(String[] args) {

		String val = "12o34";
		char ch = ' ';
		boolean isNumber = true;
		
		for (int i=0; i<val.length(); i++){
			ch = val.charAt(i);
			
			if((ch >= '0' && ch <= '9')) {
			}else {
				isNumber = false;
				break;
			}
		}
		
		if(isNumber) 
			System.out.println(val + "는 숫자입니다.");
		else
			System.out.println(val + "는 숫자가 아닙니다.");
	}

}
