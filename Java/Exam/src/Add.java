import java.util.StringTokenizer;

public class Add {

	public static void main(String[] args) {

		StringTokenizer v = new StringTokenizer("a:b:c:d:e:f", ":");
		StringTokenizer n = new StringTokenizer("1 3 5 7");
		
		String s = v.nextToken();
		int sum = Integer.parseInt(n.nextToken());
		
		while (v.hasMoreTokens() || n.hasMoreTokens())
		{
			if(v.hasMoreTokens()) s =  s + " + " + v.nextToken();
			if(n.hasMoreTokens()) sum += Integer.parseInt(n.nextToken());
		}
		System.out.println(s + " = " + sum);

	}

}
