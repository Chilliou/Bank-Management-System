import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Bank
{
	private ArrayList<Account> listClient;
	private static Scanner scanner = new Scanner( System.in );

	public Bank()
	{
		this.listClient = new ArrayList<Account>();

		try
        {
            FileInputStream fis = new FileInputStream("clientData");
            ObjectInputStream ois = new ObjectInputStream(fis);
 
            this.listClient = (ArrayList) ois.readObject();
 
            ois.close();
            fis.close();
        } 
        catch (IOException ioe) 
        {
            ioe.printStackTrace();
            return;
        } 
        catch (ClassNotFoundException c) 
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }

		
	}

	public void menu()
	{
		System.out.println("\nCUSTOMER ACCOUNT BANKING MANAGEMENT SYSTEM\n ");
		System.out.println("         WELCOME TO THE MAIN MENU         \n");
		System.out.println("1.Create new account");
		System.out.println("2.Update information of existing account");
		System.out.println("3.For transactions ");
		System.out.println("4.Check the details of existing account ");
		System.out.println("5.Removing existing account");
		System.out.println("6.View customer's list ");
		System.out.println("7.Exit \n");

		String input;
		int choice=-1;
		do
		{
			System.out.print("Enter your choice : ");
			try
			{
				input = scanner.nextLine();
				choice = Integer.parseInt( input );
			} catch(NumberFormatException e){
				System.out.println("Insert a number, please.\n");
			}

		}while(choice <=0 || choice >7);

		switch(choice)
		{
			case 1 -> this.newAccount();
			case 2 -> this.edit();
			case 3 -> this.transact();
			case 4 -> this.see();
			case 5 -> this.erase();
			case 6 -> this.viewList();
			case 7 -> {this.save(); 
					   System.exit(0);}
			default -> {System.out.println("Error 404 "); 
						System.exit( -1 );}
		}
		
		
	}

	private void newAccount()
	{
		String[] name = this.getName();
		String firstName = name[0];
		String lastName = name[1];

		int numAccount = this.listClient.get(this.listClient.size()-1).getnumAccount()+1;

		this.listClient.add(new Account(numAccount,firstName,lastName,0.0));
		this.save();

	}

	private void edit()
	{
		String[] name = this.getName();
		String firstName = name[0];
		String lastName = name[1];
		Account accountEdited  = this.getAccount();
		
		this.listClient.set(this.listClient.indexOf(accountEdited),
							new Account(accountEdited.getnumAccount(),firstName,lastName,accountEdited.getBalance()));

		this.save();
	}

	private void transact()
	{
		Account accountSender = this.getAccount();
		Account accountReceiver = this.getAccount();

		String input;
		double value = -1;

		do
		{
			System.out.print("\nEnter the value of the transaction : ");
			try
			{
				input = scanner.nextLine();
				value = Double.parseDouble( input );
			} catch(NumberFormatException e){
				System.out.println("Insert a double, please.\n");
			}

		}while(value <=0.0 || value >accountSender.getBalance());

		this.listClient.set(this.listClient.indexOf(accountSender),
							new Account(accountSender.getnumAccount(),
										accountSender.getfirstName(),
										accountSender.getlastName(),
										accountSender.getBalance()-value));

		this.listClient.set(this.listClient.indexOf(accountReceiver),
							new Account(accountReceiver.getnumAccount(),
										accountReceiver.getfirstName(),
										accountReceiver.getlastName(),
										accountReceiver.getBalance()+value));



		this.save();
	}

	private void see()
	{
		Account accountSee = this.getAccount();

		System.out.println(accountSee);
	}

	private void erase()
	{
		Account accountErase = this.getAccount();

		this.listClient.remove(accountErase);
		this.save();
	}

	private void viewList()
	{
		for(Account ac : this.listClient)
		{
			System.out.println(ac);
		}
	}

	public void save()
	{
		try
		{
			FileOutputStream fos = new FileOutputStream("clientData");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.listClient);
			oos.close();
			fos.close();
		} 
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		}

	}

	private Account getAccount()
	{
		int numAccount = -1;
		String input;
		Account account;

		do
		{
			System.out.print("\nEnter your num Account : ");
			try
			{
				input = scanner.nextLine();
				numAccount = Integer.parseInt( input );
			} catch(NumberFormatException e){
				System.out.println("Insert a number, please.\n");
			}

			account = this.getAccount(numAccount);
		}while(account == null);

		return account;
	}

	private Account getAccount(int numAccount)
	{
		for(Account ac : this.listClient)
		{
			if(ac.getnumAccount() == numAccount)
			{
				return ac;
			}
		}
		return null;
	}

	private String[] getName()
	{
		String firstName;
		String lastName;
		do
		{
			System.out.print("\nEnter your first name : ");
			firstName = scanner.nextLine();

			System.out.print("Enter your last name : ");
			lastName = scanner.nextLine();

			System.out.println("");
		}while( !Pattern.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$", firstName) ||
				!Pattern.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$", lastName) );

		String[] name = {firstName,lastName};
		return name;
	}

}