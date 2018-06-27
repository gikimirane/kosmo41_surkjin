//배열 기반 반복문 활용의 예

public class StringArray {

	public static void main(String[] args) {

		String[] str = new String[7];
		str[0] = "Java"; 
		str[1] = "System";
		str[2] = "Compiler";
		str[3] = "Park";
		str[4] = "Tree";
		str[5] = "Dinner";
		str[6] = "Brunch Cafe";
		
		int cnum = 0;
		
		for(int i=0; i<str.length; i++)
			cnum += str[i].length();
		System.out.println("총 문자의 수: " + cnum);
	}

}
