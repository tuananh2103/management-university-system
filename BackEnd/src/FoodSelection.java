import java.util.Scanner;

public class FoodSelection {
	static Scanner sc = new Scanner(System.in);
	
	public static void food(String regNumber) {
		int food;
		
		MyFastFood foodFastObj = new MyFastFood();
		
		MyDesiFood foodDesiObj = new MyDesiFood();
		
		while(true) {
			System.out.println("1.Fast Food\n2.for Desi Food \n0.Nothing\n");
			try {
				System.out.print("Enter your choice: ");
				food = sc.nextInt();
				System.out.println();
				if ( food == 1) {
					foodFastObj.main(regNumber);
					System.out.println("**********************************");
				}else if ( food == 2) {
					foodDesiObj.main(regNumber);
					System.out.println("**********************************");
				}else if ( food == 0) {
					System.out.println("**********************************");
					break;
				}else {
					System.out.println("Please Choose Correct Option!!!");
					System.out.println("**********************************");
				}
			}catch (Exception e) {
				System.out.println("Invalid Inputs");
				sc.nextLine();
				continue;
			}
		}
	}
}
