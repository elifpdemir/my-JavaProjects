import java.io.*;


public class BankSystem {
	
	public static void main(String[] args) throws Exception {
		

		int[] acctNums = new int[countAccounts("Assignment4_AccountInfo.txt")];
		String[] acctNames= new String[countAccounts("Assignment4_AccountInfo.txt")];
		String[] acctSurnames = new String[countAccounts("Assignment4_AccountInfo.txt")];
		double[] acctBalances = new double[countAccounts("Assignment4_AccountInfo.txt")];
		int numOfAccounts;
		
		

        numOfAccounts = Assignment4.countAccounts("Assignment4_AccountInfo.txt");
        acctNums = new int[numOfAccounts];
        acctNames = new String[numOfAccounts];
        acctSurnames = new String[numOfAccounts];
        acctBalances = new double[numOfAccounts];

        System.out.println("The number of accounts is " + numOfAccounts);

        Assignment4.readAccountInfo(acctNums, acctNames, acctSurnames, acctBalances, "Assignment4_AccountInfo.txt");
        System.out.println("The information in the file is:");
        System.out.println("Number\tName\tSurname\tBalance");
        for (int i = 0; i < acctNums.length; i++) {
            System.out.println(acctNums[i] + "\t" + acctNames[i] + "\t" + acctSurnames[i] + "\t" + acctBalances[i]);
        }

        System.out.println("The deposit is successful: " + Assignment4.deposit(acctBalances, 0, 100));
        System.out.println("The new balance for the first account is " + acctBalances[0]);

        System.out.println("The withdrawal is successful: " + Assignment4.withdrawal(acctBalances, 1, 100));
        System.out.println("The new balance for the second account is " + acctBalances[1]);

        System.out.println("The return value of transferring 150 from the first account to the second is: " +
            Assignment4.transfer(acctNums, acctBalances, 12345, 67890, 150));
        System.out.println("The new balance for the first account is " + acctBalances[0]);
        System.out.println("The new balance for the second account is " + acctBalances[1]);

        Assignment4.writeAccountInfo(acctNums, acctNames, acctSurnames, acctBalances, "Assignment4_AccountInfoOut.txt");
		
		
		numOfAccounts = countAccounts("Assignment4_AccountInfo.txt");
		readAccountInfo(acctNums, acctNames, acctSurnames, acctBalances, "Assignment4_AccountInfo.txt");
		
		deposit(acctBalances, 0, 100);
		withdrawal(acctBalances, 1, 100);
		transfer(acctNums, acctBalances, 12345, 67890, 150);
		writeAccountInfo(acctNums, acctNames, acctSurnames, acctBalances, "Assignment4_AccountInfo.txt");
		
		
		
	
	
	}
	
	/*The method will take a String parameter. 
         This parameter represents the name of the file (or the file path).
          the number of lines in the file*/
    public static int countAccounts(String filename) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
        }
        return count;
    }
	
	//read file put into array
	
	//Before calling this method, set the size of the arrays
	
	public static void readAccountInfo(int[] acctNums, String[] names, String[] surnames, double[] balances, String filename) throws NumberFormatException, IOException {
		 BufferedReader reader = new BufferedReader(new FileReader(filename));
	        String line;
	        
	        int i = 0;
	        
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(" ");
	            acctNums[i] = Integer.parseInt(parts[0]);
	            names[i] = parts[1];
	            surnames[i] = parts[2];
	            balances[i] = Double.parseDouble(parts[3]);
	            i++;
	        }
	        reader.close();
		
	}
	
	public static boolean deposit(double[] balances, int index, double amount) {
		
        if (isDepositValid(amount)) {
            balances[index] += amount; // Increase account balance
            return true; // Successful
        } else {
            return false; // invalid
        }
	}
	
	public static boolean isDepositValid(double amount) {
        return amount > 0; 
    }

	
	public static boolean withdrawal(double[] balances, int index, double amount) {
		
        if (isWithdrawalValid(amount, balances[index])) {
            balances[index] -= amount; // decrease
            return true; // succesful
        } else {
            return false; //invalid
        }
    }

	
	public static boolean isWithdrawalValid(double amount, double balance) {
        return amount > 0 && balance >= amount; 
    }
	
	public static int transfer(int[] acctNums, double[] balances, int acctNumFrom, int acctNumTo, double amount) {
        int fromIndex = findAcct(acctNums, acctNumFrom);
        int toIndex = findAcct(acctNums, acctNumTo);

        if (fromIndex == -1) return 2; // acctNumFrom not found
        if (toIndex == -1) return 3; // acctNumTo not found

        if (!isWithdrawalValid(balances[fromIndex], amount)) return 1; // insufficient funds

        balances[fromIndex] -= amount;
        balances[toIndex] += amount;

        return 0; // success
    }
	
	
	public static int findAcct(int[] acctNums, int acctNum) { 

        for (int i = 0; i < acctNums.length; i++) {
            if (acctNums[i] == acctNum) {
                return i;
            }
        }
        return -1;
    }
	
	public static void writeAccountInfo(int[] acctNums, String[] names, String[] surnames, double[] balances, String filename) {
        try {
           
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            
          
            for (int i = 0; i < acctNums.length; i++) {
                String accountInfo = acctNums[i] + " " + names[i] + " " + surnames[i] + " " + balances[i];
                writer.write(accountInfo); 
                writer.newLine();
            }
            
          
            writer.close();
        } catch (IOException e) {
            
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
