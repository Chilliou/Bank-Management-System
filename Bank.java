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
	private boolean firstTime = true;

	private Account loggedAccount= null; 

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


	//////////////////////////////////////////////////////////
	//														//
	//						MENU							//
	//														//
	//////////////////////////////////////////////////////////

	public void menuLoader()
	{
		System.out.println("HELLO TO YOUR FAVORITE BANK\n");
		System.out.println("1.Connect to your bank account");
		System.out.println("2.Connect to the admin");
		System.out.println("3.Exit \n");

		String input;
		int choice=this.choiceChooser(1,3);

		switch(choice)
		{
			case 1 -> this.connectAccount();
			case 2 -> this.menuAdmin();
			case 3 -> {this.save(); 
					   System.exit(0);}
			default -> {System.out.println("Error 404 "); 
						System.exit( -1 );}
		}
	}

	public void menuAccount()
	{

	}

	public void menuAdmin()
	{
		if(firstTime)
		{
			this.firstTime= false;
			this.clearConsole(false);
		}

		System.out.println("\nCUSTOMER ACCOUNT BANKING MANAGEMENT SYSTEM\n ");
		System.out.println("         WELCOME TO THE MAIN menuAdmin         \n");
		System.out.println("1.Create new account");
		System.out.println("2.Update information of existing account");
		System.out.println("3.For transactions ");
		System.out.println("4.Check the details of existing account ");
		System.out.println("5.Removing existing account");
		System.out.println("6.View customer's list ");
		System.out.println("7.Exit \n");

		String input;
		int choice=this.choiceChooser(1,7);

		switch(choice)
		{
			case 1 -> this.newAccount();
			case 2 -> this.editAccount();
			case 3 -> this.transact();
			case 4 -> this.seeAccount();
			case 5 -> this.eraseAccount();
			case 6 -> this.viewListAccount();
			case 7 -> {this.save(); 
					   System.exit(0);}
			default -> {System.out.println("Error 404 "); 
						System.exit( -1 );}
		}
		
		this.menuAdmin();
	}

	//////////////////////////////////////////////////////////
	//														//
	//						FUNC							//
	//														//
	//////////////////////////////////////////////////////////

	private void newAccount()
	{
		String[] name = this.getName();
		String firstName = name[0];
		String lastName = name[1];

		int numAccount = this.listClient.get(this.listClient.size()-1).getnumAccount()+1;

		this.listClient.add(new Account(numAccount,firstName,lastName,0.0));
		this.clearConsole(false);
		this.save();
	}

	private void editAccount()
	{
		Account accounteditAccounted  = this.getAccount();
		String[] name = this.getName();
		String firstName = name[0];
		String lastName = name[1];
		
		this.listClient.set(this.listClient.indexOf(accounteditAccounted),
							new Account(accounteditAccounted.getnumAccount(),firstName,lastName,accounteditAccounted.getBalance()));

		this.clearConsole(false);
		this.save();
	}

	private void transact()
	{
		Account accountSender;
		Account accountReceiver;
		do{
			accountSender = this.getAccount();
			accountReceiver = this.getAccount();
			if(accountReceiver.equals(accountSender))
			{
				System.out.println("The two counts cannot be equal");
			}
		}while(accountReceiver.equals(accountSender));
		
		double value = this.getValueTransact(accountSender);
	
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

		String transaction = "\n " +value + "€ | "+ accountSender.getfirstName()   +" "+ accountSender.getlastName() +" -> "+ 
												   accountReceiver.getfirstName() +" "+ accountReceiver.getlastName();
		System.out.println( transaction	);

		this.clearConsole(true);
		this.save();

	}

	private void seeAccount()
	{
		Account accountseeAccount = this.getAccount();

		System.out.println(accountseeAccount);
		this.clearConsole(true);

	}

	private void eraseAccount()
	{
		Account accounteraseAccount = this.getAccount();

		this.listClient.remove(accounteraseAccount);
		this.clearConsole(false);
		this.save();
	}

	private void viewListAccount()
	{
		this.clearConsole(false);
		System.out.println("\n  NumAcc |    Prenom     Nom    | Balance");
		System.out.println("-------------------------------------------------------");
		for(Account ac : this.listClient)
		{
			System.out.println(ac);
		}
	}

	

	

	//////////////////////////////////////////////////////////
	//														//
	//					GETTER SETTER						//
	//														//
	//////////////////////////////////////////////////////////

	

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

			if((account = this.getAccount(numAccount)) == null)
				System.out.println("The account number is not valid");
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


	//////////////////////////////////////////////////////////
	//														//
	//						UTILITY							//
	//														//
	//////////////////////////////////////////////////////////


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

	private void connectAccount()
	{
		Account acc = this.getAccount();
		if(this.checkPassword(acc))
		{
			System.out.println("Account connected");
			this.loggedAccount = acc;
			this.menuAccount();
		}

	}

	private void clearConsole(boolean timing) 
	{
		if(timing)
		{
			String input = scanner.nextLine();
		}

		try
		{
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();  
		}catch(Exception e)
		{
			System.out.println(	"Error 405");
		}

	}

	private int choiceChooser(int min,int max)
	{
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

		}while(!this.intervalCheck(min,max,choice));

		return choice;
	}

	private boolean intervalCheck(int min,int max,int i)
	{
		return (i>=min && i<=max);
	}

	private boolean checkPassword(Account acc)
	{
		boolean ret = false;
		String password = acc.getfirstName().substring(0,2) + acc.getlastName().substring(0,2);
		String input;

		for(int i=0;i<3 && !ret;i++)
		{
			System.out.print("Insert your password : ");
			input = scanner.nextLine();
			System.out.println("");

			ret = input.equals(password);
			if(!ret)
			{
				System.out.println(	"Wrong password !");
			}
		}

		if(!ret)
		{
			System.out.println("Too many failed attempts");
			this.save();
			System.exit(0);
		}
		

		return ret;
	}

	private double getValueTransact(Account accountSender)
	{
		double value = -1;
		String input;

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

			if(value >accountSender.getBalance() )
			{
				System.out.println("The account does not have enough on its balance ");
			}

		}while(value <=0.0 || value >accountSender.getBalance());

		return value;
	}
}