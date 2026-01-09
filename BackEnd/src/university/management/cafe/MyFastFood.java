package university.management.cafe;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class MyFastFood {
    static Scanner input = new Scanner(System.in);
    static int quantity;
    static int total_amount=0;
    static java.util.Date date=new java.util.Date();

    public static void main(String regNumber) {
        input = new Scanner(System.in);
        int menuOption;
        try{
            File file = new File("cafe/CafeBills/MyFastFood/"+regNumber+".txt");
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileWriter bw = new FileWriter(file,true);
            bw.write("\nDate: "+date+"\n");
            boolean ordering = true;
            while(ordering){
                menu();
                System.out.print("Enter your choice (0 to finish): ");
                try{
                    menuOption = input.nextInt();
                }
                catch(Exception e){
                    System.out.println("Invalid input.");
                    input.nextLine();
                    continue;
                }
                if(menuOption==0) break;
                System.out.print("Quantity: ");
                try{
                    quantity = input.nextInt();
                }catch(Exception e){
                    System.out.println("Invalid quantity.");
                    input.nextLine();
                    continue;
                }
                int price = 0;
                switch(menuOption){
                    case 1: price = 200; bw.write("Burger - "+quantity+" x "+price+"\n"); break;
                    case 2: price = 180; bw.write("Pizza - "+quantity+" x "+price+"\n"); break;
                    default: System.out.println("Invalid option."); continue;
                }
                total_amount += price * quantity;
            }
            bw.write("Total Amount: "+total_amount+"\n");
            bw.close();
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void menu(){
        System.out.println("1. Burger - 200\n2. Pizza - 180\n0. Finish");
    }

    // Return the total bill amount (instance method expected by SelectionMethod)
    public int FastfoodBill() {
        return total_amount;
    }

}
