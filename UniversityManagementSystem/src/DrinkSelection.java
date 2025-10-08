import java.util.Scanner;

public class DrinkSelection {
	static Scanner input = new Scanner(System.in);
	
	public static void myDrink(String regNumber) {
		int drinks;
		MySoftDrinks drinkSoftObj = new MySoftDrinks();
		
		MyJuiceOrPlantDrink drinkJuiceOrPlantDrinkObj = new MyJuiceOrPlantDrink();
		
		MyCoffee drinkCoffeeObj = new MyCoffee();
		while(true) {
			System.out.println("Please Select a Drink you want to drink.\n1.Soft drinks\n.Coffee\n3.Juice or Plant drinks\n0.Nothing \n");
			try {
				System.out.println("Enter Your Choice: ");
				drinks = input.nextInt();
				System.out.println();
				if(drinks == 1) {
					drinkSoftObj.main(regNumber);//called method main from Class MySoftDrinks and pass Registration number 
					System.out.println("********************************************");
				}
				else if(drinks == 2){
					System.out.println("Please select your Coffee you want to drink");
					drinkCoffeeObj.main(regNumber);//called method main from Class MyJuiceOrPlantDrink and pass Registration number
					System.out.println("********************************************");
				}
				else if(drinks == 3){
					drinkJuiceOrPlantDrinkObj.main(regNumber);//called method main from Class MyCoffee and pass Registration number
					System.out.println("********************************************");
				}
				else if(drinks == 0){//if user enter 0 then no Drink he want.
					System.out.println("********************************************");
					break;
				}
				else
					System.out.println("Please Choose Correct Option");
					System.out.println("********************************************");
			}catch(Exception e){
				System.out.println("Invalid Inputs");
				input.nextLine();//Use to cancel Enter key remove from keyBoard buffer.
				continue;
			}
		}
	}	
}