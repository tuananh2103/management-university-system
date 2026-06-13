package university.management.cafe;

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
                    drinkSoftObj.main(regNumber);
                    System.out.println("********************************************");
                }
                else if(drinks == 2){
                    System.out.println("Please select your Coffee you want to drink");
                    drinkCoffeeObj.main(regNumber);
                    System.out.println("********************************************");
                }
                else if(drinks == 3){
                    drinkJuiceOrPlantDrinkObj.main(regNumber);
                    System.out.println("********************************************");
                }
                else if(drinks == 0){
                    System.out.println("********************************************");
                    break;
                }
                else
                    System.out.println("Please Choose Correct Option");
                    System.out.println("********************************************");
            }catch(Exception e){
                System.out.println("Invalid Inputs");
                input.nextLine();
                continue;
            }
        }
    }    
}
