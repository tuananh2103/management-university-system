package university.management.cafe;

import java.util.Scanner;

public class Cafe {
    static Scanner sc = new Scanner(System.in);
    public static void manage(String regNumber) throws Exception{
        int amount = 10000;
        Displaymenu menu = new Displaymenu();
        menu.displayMenu();
        System.out.println("\n");
        SelectionMethod select = new SelectionMethod();
        select.chooseMethod(regNumber);
        System.out.println();
    }
}
