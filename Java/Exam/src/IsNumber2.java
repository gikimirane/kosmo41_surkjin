
public class IsNumber2 {

	public static void main(String[] args) {

		String val = "12o34";
		String ch = "";
		boolean isNumber = true;
		
		for (int i=0; i<val.length(); i++){
			ch = val.substring(i, i+1);
			
			try {
				int num = Integer.parseInt(ch);
			}catch(Exception e) {
				isNumber = false;
				break;
			}
		
		if(isNumber) 
			System.out.println(val + "는 숫자입니다.");
		else
			System.out.println(val + "는 숫자가 아닙니다.");

		}
	}
}
