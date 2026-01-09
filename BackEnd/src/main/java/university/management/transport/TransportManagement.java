package university.management.transport;

import java.util.Scanner;
import university.management.Main;

public class TransportManagement {
    static Scanner sc = new Scanner(System.in);
    static Main mainclass = new Main();
    static TMS transportObj = new TMS();
    
    public static void manage(String regNumber) {
        System.out.println("\n*******************************************************\n");
        System.out.println("\tTransport Management System\n");
        System.out.println("*******************************************************\n");
        
        System.out.print("1. Visit transport management system\n");
        System.out.print("2. Previous Menu\n");
        System.out.print("3. Exit\n");
        System.out.println();
        
        while(true) {
            try {
                System.out.println("Enter your choice: ");
                int c = sc.nextInt();
                boolean valid = false;
                
                switch(c) {
                    case 1: {
                        transportObj.manage(regNumber);
                        valid = true;
                        break;
                    }
                    case 2 : {
                        mainclass.management(regNumber);
                        valid = true;
                        break;
                    }
                    case 3 : {
                        System.exit(0);
                    }
                }
                if(valid) {
                    break;
                }
            }catch (Exception e) {
                System.out.println("Invalid input! Try again.\t");
                sc.nextLine();
            }
        }
    }
}
