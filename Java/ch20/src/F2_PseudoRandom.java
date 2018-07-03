import java.util.Random;

public class F2_PseudoRandom {

	public static void main(String[] args) {

		Random rand = new Random();
		for(int i=0; i<7; i++)
				System.out.println(rand.nextInt(1000));

	}

}
