package odev;
import java.io.*;


/*
 * @author elif polatdemir
 * @ date 28.11.2024
 */

public class Assignment4 {
	
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
	
	//Metot, bir String parametre alacak. Bu parametre,
	//dosyanın ismini (veya dosya yolunu) temsil eder.
	//dosyadaki satır sayısı
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
	
	//dosya oku diziye koy
	//boşluk karakterine göre okur
	//Bu yöntemi çağırmadan önce dizilerin boyutunu 
	//ayarlamak için countAccounts() yönteminden döndürülen değeri kullanabilirsiniz
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
            balances[index] += amount; // Hesap bakiyesini arttır
            return true; // Başarılı
        } else {
            return false; // Geçersiz yatırım
        }
	}
	
	public static boolean isDepositValid(double amount) {
        return amount > 0; // Yatırım miktarı sıfırdan büyükse geçerlidir
    }

	
	public static boolean withdrawal(double[] balances, int index, double amount) {
		
        if (isWithdrawalValid(amount, balances[index])) {
            balances[index] -= amount; // Hesap bakiyesini azalt
            return true; // Başarılı
        } else {
            return false; // Geçersiz çekim
        }
    }

	
	public static boolean isWithdrawalValid(double amount, double balance) {
        return amount > 0 && balance >= amount; // Yatırım miktarı sıfırdan büyük olmalı ve bakiye yeterli olmalı
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
            // Dosyaya yazmak için BufferedWriter nesnesi oluşturuyoruz
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            
            // Array'ler üzerinde dönerek her bir hesap bilgisini dosyaya yazıyoruz
            for (int i = 0; i < acctNums.length; i++) {
                String accountInfo = acctNums[i] + " " + names[i] + " " + surnames[i] + " " + balances[i];
                writer.write(accountInfo); // Hesap bilgisini dosyaya yaz
                writer.newLine(); // Her hesap bilgisinden sonra yeni bir satıra geç
            }
            
            // Dosyayı kapatıyoruz
            writer.close();
        } catch (IOException e) {
            // Dosya yazma sırasında oluşan hataları yakalıyoruz
            System.out.println("Dosyaya yazarken bir hata oluştu.");
            e.printStackTrace();
        }
    }
}
