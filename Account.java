import java.io.Serializable;

public class Account implements Serializable
{
	private int numAccount;
	
	private String firstName;
	private String lastName;

	private double balance;

	public Account(int numAccount, String firstName,String lastName, double balance)
	{
		this.numAccount = numAccount;
		this.firstName = firstName;
		this.lastName = lastName;
		this.balance = balance;
	}

	public int getnumAccount()
	{
		return this.numAccount;
	}

	public String getfirstName()
	{
		return this.firstName;
	}

	public String getlastName()
	{
		return this.lastName;
	}

	public double getBalance()
	{
		return this.balance;
	}

	public String toString()
	{
		return String.format("%8d | %9s %10s | Balance : %8.2f €",numAccount,firstName,lastName,balance); 
	}
}