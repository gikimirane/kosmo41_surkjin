package ch23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class F_CopyList {

	public static void main(String[] args) {

		List<String> src = Arrays.asList("Box","Apple","Toy","Robot");
		
		List<String> des = new ArrayList(src);
		Collections.shuffle(des);
		
		Collections.sort(des);
		System.out.println(des);
		
		Collections.copy(des, src);
		System.out.println(des);
		
		Collections.shuffle(des);
		System.out.println(des);
	}

}
