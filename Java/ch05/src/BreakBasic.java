
public class BreakBasic {

	public static void main(String[] args) {

		int num = 1;
		boolean search = false;
		
		while (num < 30) {
			if (((num % 5) == 0) && (num % 7) == 0) {
				search = true;
				break;
			}
			
			//num ++;
			num = num + 2;
		}

		if(search)
			System.out.println("찾는 정수 : " + num);
		else
			System.out.println("5의 배수이자 7의 배수를 찾지 못했습니다");
	}

}
