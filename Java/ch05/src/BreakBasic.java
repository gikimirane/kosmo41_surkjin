
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
			System.out.println("ã�� ���� : " + num);
		else
			System.out.println("5�� ������� 7�� ����� ã�� ���߽��ϴ�");
	}

}
