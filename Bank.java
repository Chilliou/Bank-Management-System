import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;



public class Bank
{
	private ArrayList<Account> listClient;

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
		int choice;
		do
		{
			System.out.print("Enter your choice : ");

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
			case 7 -> this.exit();
			default -> {System.out.println("Error 404 "); 
						System.exit( -1 );}
		}
		
		
	}

	private void newAccount()
	{
		
	}

	private void edit()
	{
		
	}

	private void transact()
	{
		
	}

	private void see()
	{
		
	}

	private void erase()
	{
		
	}

	private void viewList()
	{
		for(Account ac : this.listClient)
		{
			System.out.println(ac);
		}
	}

	private void exit()
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

		System.exit(0);
	}

}