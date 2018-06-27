class BankAccount
{
	int bal = 0;
	
	public int dps(int amt)
	{
		bal += amt;
		return amt;
	}
	
	public int wdt(int amt)
	{
		bal -= amt;
		return amt;
	}
	
	public int chkMyBal()
	{
		System.out.println("잔액: " + bal);
		return bal;
	}
}
public class BankAccnt {

	public static void main(String[] args) {

		BankAccount yoon = new BankAccount();
	//	BankAccount park = new BankAccount();
		BankAccount park = yoon;
		
		yoon.dps(5000);
		park.dps(3000);
		
		yoon.wdt(2000);
		park.wdt(2000);

		yoon.chkMyBal();
		park.chkMyBal();

	}

}
