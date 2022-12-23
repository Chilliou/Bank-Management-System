import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;


public class Bank
{
	private ArrayList<Account> listClient;

	public Bank()
	{
		this.listClient = new ArrayList<Account>();

		try 
		{
			File file = new File("compte.txt");
			Scanner input = new Scanner(file);

			while (input.hasNextLine()) 
			{
				String line = input.nextLine();
				String[] info = line.split(",");

				int numAccount = Integer.parseInt(info[0]);
				String firstName = info[1];
				String lastName = info[2];
				double balance = Double. parseDouble(info[3]);

				this.listClient.add(new Account(numAccount, firstName, lastName, balance));
			}
			input.close();

		} catch (Exception ex) 
		{
			ex.printStackTrace();
		}

		
	}

	public void menu()
	{
		System.out.println("CUSTOMER ACCOUNT BANKING MANAGEMENT SYSTEM\n ");
		System.out.println("         WELCOME TO THE MAIN MENU         \n");
		System.out.println("1.Create new account");
		System.out.println("2.Update information of existing account");
		System.out.println("3.For transactions ");
		System.out.println("4.Check the details of existing account ");
		System.out.println("5.Removing existing account");
		System.out.println("6.View customer's list ");
		System.out.println("7.Exit \n");

		String input;
		int choice;
		do
		{
			System.out.println("Enter your choice : ");

			Scanner scanner = new Scanner(System.in);
			input = scanner.nextLine();
			choice = Integer.parseInt( input );

		}while(choice <=0 || choice >7);

		switch(choice)
		{
			case 1 -> this.newAccount();
			case 2 -> this.edit();
			case 3 -> this.transact();
			case 4 -> this.see();
			case 5 -> this.erase();
			case 6 -> this.viewList();
			case 7 -> System.exit( 0 );
			default -> {System.out.println("Error 404 "); 
						System.exit( -1 );}
		}
		
		
	}

	private void newAccount()
	{
		
	}

	private void viewList()
	{
		for(Account ac : this.listClient)
		{
			System.out.println(ac);
		}
	}

	private void edit()
	{
		
	}

	private void transact()
	{
		
	}

	private void erase()
	{
		
	}

	public void see()
	{
		
	}

}