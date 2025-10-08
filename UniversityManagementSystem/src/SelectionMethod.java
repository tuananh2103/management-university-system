import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

public class SelectionMethod {
	static Scanner input = new Scanner(System.in);
	static int option;
	static Date date = new Date();
	static String name;
	
	public static void chooseMethod(String regNumber)throws Exception{
		int amount = 5000;
		// khoi tao du lieu food class
		FoodSelection foodSelectionObj = new FoodSelection();
		// khoi tao du lieu drink class
		DrinkSelection drinkSelectionObj = new DrinkSelection();
		
		while(true) {
			System.out.println("1.Food\n2.Drinks\n3.Bill Generate\n4.Previous Menu\n5. Exit\n");
			try {
				System.out.print("Enter your choice: ");
				option = input.nextInt();
				System.out.print("\n");
				if (option == 1) {
					foodSelectionObj.food(regNumber); // method tu food class duoc goi ra o day 
					System.out.println("***************************************************");
					break;
				}else if ( option == 2) {
					drinkSelectionObj.myDrink(regNumber);
					System.out.println("***************************************************");
				}else if ( option == 3) {
					File file = new File("student_data/" +regNumber + ".txt");
					try {
						Scanner sc = new Scanner(System.in);
						String [] line = sc.nextLine().split(":");
						name = line[1];
					}catch(Exception e){
						System.out.print("Error occured!");
					}
					MyFastFood fastfoodObj = new MyFastFood();
					int num = fastfoodObj.FastfoodBill();
					
					MyDesiFood desifoodObj = new MyDesiFood();
					int num2 = desifoodObj.DesifoodBill();
					
					MySoftDrinks soft = new MySoftDrinks();
					int num3 = soft.SoftDrinkbill();
					
					MyCoffee coffe = new MyCoffee();
					int num5 = coffe.CoffeeBill();
					
					MyJuiceOrPlantDrink JuiceDrink= new MyJuiceOrPlantDrink();
					int num4 = JuiceDrink.JuiceORPlantbill();
					
					int total = num +num2 +num3 +num4 +num5;
					
					if ( amount >=total && total !=0 ) {
						System.out.println("\n\n\n");
						System.out.println("\t\t\t-----------------------------------");
						System.out.println("\t\t\tDate: "+date);
						System.out.println("\t\t\tName: "+name);
						System.out.println("\t\t\tStudent ID: "+regNumber);
						System.out.println("\t\t\t---------Thanks For Coming---------");
						System.out.println("\t\t\t-----------Your Bill Is------------\n");
						File file2 = new File("cafe/CafeBills/BillFastFood.txt");
						Scanner myreader = new Scanner(System.in);
						int count = 0; 
						while( myreader.hasNextLine()) {
							myreader.nextLine();
							count+=1;
						}
						Scanner reader = new Scanner(System.in);
						String [] array = new String[count];
						int index = 0;
						
						while(reader.hasNextLine()) {
							array[index] = reader.nextLine();
							index++;
						}
						System.out.println("\t\t\tItems			Quantity	Prices\n");
						for ( int i = 0 ; i<array.length;i++) {
							System.out.print("\t\t\t"+array[i]+ "\n");
						}
						System.out.printf("\t\t\tTotal Bill                  %d  \n",(num+num2+num3+num4+num5));//Total Bill is formed here.
						System.out.println("\t\t\t---------------------------------------");
						System.out.println("\t\t\t---------------------------------------");
						System.out.println("\t\t\t---------------------------------------");
						System.out.println();
						try {
							File file1 = new File("cafe/CafeBills/FinalBills/"+ regNumber +".txt");
							boolean value = file1.createNewFile();
							FileWriter billObj = new FileWriter(file1,true);
							billObj.write("Date: "+date+"\n");
							billObj.write("-----------------------------------\n");
							billObj.write("Name : "+ name +"\n");
							billObj.write("Student ID : "+ regNumber +"\n");
							billObj.write("-----------------------------------\n");
							billObj.write("---------Thanks For Coming---------\n");
							billObj.write("-----------Your Bill Is------------\n");
							billObj.write("\t\t\tItems            Quantity   Prices\n");
							for (int i = 0 ;i<array.length ;i++ ) {
								billObj.write(""+array[i]+"\n");//Write the data
							}
							billObj.write("Total Bill                 "+total+"\n");
							billObj.write("-----------------------------------\n");
							billObj.write("-----------------------------------\n\n\n");
							billObj.close();
						}catch(Exception e) {
							System.out.println("invalid");
						}
					}else {
						if(total == 0) {
							System.out.println("Please buy something first");
						}else if ( total !=0) {
							System.out.println("Sir , you dont have enough Account Balance");
						}
					}
				}else if (option == 4) {
					File file3 = new File("cafe/CafeBills/BillFastfood.txt");

					PrintWriter writer = new PrintWriter(file3);
					writer.print("");
					writer.close();
					CafeManagement.manage(regNumber);
				}else if (option == 5 ) {
					System.out.println("********************************************");
					File file2 = new File("cafe/CafeBills/BillFastfood.txt");

					PrintWriter writer = new PrintWriter(file2);
					writer.print("");
					writer.close();
					System.exit(0);
				}else {
					System.out.println("Please select correct option ");
				}
			}catch(Exception e) {
				System.out.println("Invalid Inputs");
				input.nextLine();
				continue;
			}
		}
	}
}
