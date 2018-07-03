import java.util.Arrays;

public class H3_ArrayEquals {

	public static void main(String[] args) {

		int[] ar1 = {1,2,3,4,5};
		int[] ar2 = Arrays.copyOf(ar1, ar1.length);
		
		int[][] ar3 = {{1,2},{3,4}};
		int[][] ar4 = {{1,2},{3,4}};
		
		System.out.println(Arrays.equals(ar1, ar2));
		System.out.println(Arrays.equals(ar3, ar4));
	}

}
