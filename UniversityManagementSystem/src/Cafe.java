import java.util.Scanner;

public class Cafe {
	static Scanner sc = new Scanner(System.in);
	public static void manage(String regNumber) throws Exception{
		int amount = 10000;
		// tao ra obj de goi phuong thuc tu class display menu
		Displaymenu menu = new Displaymenu();
		menu.displayMenu();
		
		System.out.println("\n");
		//tao ra obj de goi phuong thuc tu class selection method
		SelectionMethod select = new SelectionMethod();
		select.chooseMethod(regNumber);
		System.out.println();
		
	}
}
