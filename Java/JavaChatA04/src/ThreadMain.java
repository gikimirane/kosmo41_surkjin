import java.util.Scanner;

public class ThreadMain {
	
	public ThreadMain() {
		
	}

	public void init() {
		System.out.println("숫자를 입력해 주세요.");
		Scanner s = new Scanner(System.in);
		String s_num = s.nextLine();
		int n_num = Integer.parseInt(s_num);
		
		try {
			Thread tsub = new ThreadSub(n_num);
			tsub.start();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("이름을 입력해 주세요.");
		String s_name = s.nextLine();
		System.out.println("입력이름: " + s_name);
	}
	
	public static void main(String[] args) {
		//객체생성
		ThreadMain tm = new ThreadMain();
		tm.init();	
	}
	
	//내부클래스
	class ThreadSub extends Thread {

		int nNum;
		
		public ThreadSub(int num) {
			this.nNum = num;
		}
		
		@Override
		public void run() {
			int i = 0;
			while( i < nNum) {
				try {
					Thread.sleep(1000);
					i++;
					System.out.println("Thread: " + i);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

}
