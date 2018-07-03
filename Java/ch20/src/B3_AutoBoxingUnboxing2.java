
public class B3_AutoBoxingUnboxing2 {

	public static void main(String[] args) {

		Integer num = 10;
		
		num++;	//오토 박싱과 오토 언박싱이 동시 진행
		System.out.println(num);
		
		num += 3;
		System.out.println(num);

		int r = num + 5;		//오토 언박싱
		Integer rObj = num -5;	//오토 언박싱
		
		System.out.println(r);
		System.out.println(rObj);

	}

}
