package university.management.admin;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.regex.Pattern;
import university.management.Main;

public class Admin {
	static Scanner input = new Scanner(System.in);
	static int ids[];
	static String[] registeredCourses;
	static String[] courseDetails;
	static String[] availableCourse;
	
	public static void admin() {
		System.out.print("************************************************\n");
		System.out.print("\t\tAdmin Panel\n");
		System.out.print("************************************************\n\n");
		
		// Vòng lặp menu
		
		while(true) {
			System.out.print("\n1. Student Details \n");
			System.out.print("2. Course Details \n");
			System.out.print("3. Cafe Details \n");
			System.out.print("4. Library Details \n");
			System.out.print("5. Transport Details \n");
			System.out.print("6. Previous Menu \n");
			System.out.print("7. Exit \n\n");
			System.out.print("Enter your choice : ");
			
			try {
				int choice = input.nextInt();
				switch(choice) {
					case 1 : {
						studentDetails();
						break;
					}
					case 2 : {
						courseDetails();
						break;
					}
					case 3 : {
						cafeDetails();
						break;
					}
					case 4 : {
						libraryDetails();
						break;
					}
					case 5 : {
						transportDetails();
						break;
					}
					case 6 : {
						Main.Choices();
						break;
					}
					case 7 : {
						System.exit(0);
					}
					default : {
						System.out.print("Invalid Data! Try Again...\n");
						input.nextLine();
					}
				}
			}catch(Exception e) {
				System.out.print("Invalid Data! Try again...\n");
				input.nextLine();
			}
		}
	}
	public static void transportDetails() {
		System.out.print("*************************************************\n");
		System.out.print("\t\t Transport Details \n");
		System.out.print("*************************************************\n\n");
		input.nextLine();
		
		while(true) {
			System.out.print("Enter the Student ID(XXXX-XXX-XXX) : ");
			String regNumber = input.nextLine().toUpperCase();
			if(Pattern.matches("SP\\d{2}-\\w{3}-\\d{3}|FA\\d{2}-\\w{3}-\\d{3}",regNumber)){
				try {
					File file = new File("transport/TransportFiles"+regNumber+".txt");
					if(file.exists()) {
						Scanner sc = new Scanner(file);
						System.out.print("\n");
						int i = 1;
						while(sc.hasNextLine()) {
							String line = sc.nextLine();
							System.out.print(line+"\n");
						}
						System.out.print("*****************************************************\n");
						sc.close();
						break;
					}else {
						System.out.println("No record Found\n");
						break;
					}
				}catch(Exception e) {
					System.out.print(e);
					input.nextLine();
				}
			}else{
				System.out.print("Invalid ID! Try Again....\n");
			}
		}
		
	}
	public static void libraryDetails() {
		System.out.print("*************************************************\n");
		System.out.print("\t\t Library Details \n");
		System.out.print("*************************************************\n\n");
		input.nextLine();


		while(true){
			System.out.print("Enter the Student ID(XXXX-XXX-XXX) : ");
			String regNumber = input.nextLine().toUpperCase();
			if(Pattern.matches("SP\\d{2}-\\w{3}-\\d{3}|FA\\d{2}-\\w{3}-\\d{3}",regNumber)){
				try{
					File file = new File("library/BorrowedBooks/"+regNumber+".txt");
					if(file.exists()){
						Scanner sc = new Scanner(file);
						System.out.print("\n");
						System.out.print("Following books has been borrowed : \n");
						int i=1;
						while(sc.hasNextLine()){
							String line = sc.nextLine();
							System.out.print(i+". "+line+"\n");
						}
						System.out.print("*****************************************************\n");
						sc.close();
						break;
					}
					else{
						System.out.print("No Record Found\n");
						break;
					}
				}
				catch(Exception e){
					System.out.print(e);
				}
			}
			else{
				System.out.print("Invalid ID! Try Again....\n");
			}
		}
	}
	private static void cafeDetails() {
		System.out.print("*************************************************\n");
		System.out.print("\t\t Cafe Details \n");
		System.out.print("*************************************************\n\n");
		input.nextLine();
		String regNumber = "";
		
		while(true) {
			System.out.print("Enter the Student ID(XXXX-XXX-XXX): ");
			regNumber = input.nextLine().toUpperCase(); 
			if(Pattern.matches("SP\\d{2}-\\w{3}-\\d{3}|FA\\d{2}-\\w{3}-\\d{3}", regNumber)) {
				try {
					File file = new File("cafe/CafeBills/FinalBills/"+regNumber+".txt");
					if(file.exists()) {
						Scanner sc = new Scanner(file);
						System.out.println();
						int i = 1;
						while(sc.hasNextLine()) {
							String line = sc.nextLine();
							System.out.print(line +"\n");
						}
						System.out.print("*****************************************************\n");
						sc.close();
						break;
					}else {
						System.out.print("No Record Found\n");
						break;
					}
				}catch(Exception e) {
					System.out.print(e);
					input.nextLine();
				}
			}else {
				System.out.print("Invalid ID! Try Again...");
			}
		}
		// Hien thi menu 
		while(true) {
			System.out.print("\n1. DesiFood Bill Details \n");
			System.out.print("2. FastFood Bill Details \n");
			System.out.print("3. Coffee Bill Details \n");
			System.out.print("4. SoftDrinks Bill Details \n");
			System.out.print("5. Juices Bill Drink \n");
			System.out.print("6. Exit \n\n");
			System.out.print("Enter your choice : ");
			
			boolean flag = false;
			try {
				int choice = input.nextInt();
				switch(choice) {
					case 1 : {
						System.out.print("**********  Desi Food Bill *********\n");
						try {
							File file = new File("cafe/CafeBills/DesiFood/"+regNumber+".txt");
							if(file.exists()) {
								Scanner sc = new Scanner(file);
								System.out.println();
								int i = 1;
								while(sc.hasNextLine()) {
									String line = sc.nextLine();
									System.out.print(line+"\n");
								}
								System.out.print("*****************************************************\n");
								sc.close();
								break;
							}else {
								System.out.print("No Record Found\n");
								break;
							}
						}catch(Exception e) {
							System.out.print(e);
							input.nextLine();
						}
						break;
					}
					case 2 : {
						System.out.print("**********  Fast Food Bill *********\n");
						try {
							File file = new File("cafe/CafeBills/FastFood/"+regNumber+".txt");
							if ( file.exists()) {
								Scanner sc = new Scanner(file);
								System.out.println();
								int i = 1 ; 
								while(sc.hasNextLine()) {
									String line = sc.nextLine();
									System.out.print(line + "\n");
								}
								System.out.print("*****************************************************\n");
								sc.close();
								break;
							}else {
								System.out.print("No Record Found\n");
								break;
							}
						}catch(Exception e) {
							System.out.print(e);
							input.nextLine();
						}
						break;
					}
					case 3 : {
						System.out.print("**********  Coffee Bill *********\n");
						try {
							File file = new File("cafe/CafeBills/MyCoffee"+regNumber+".txt");
							if ( file.exists()) {
								Scanner sc = new Scanner(file);
								System.out.println();
								int i = 1 ;
								while(sc.hasNextLine()) {
									String line = sc.nextLine();
									System.out.print(line + "\n");
								}
								System.out.print("*****************************************************\n");
								sc.close();
								break;
							}else{
								System.out.print("No Record Found\n");
								break;
							}
						}catch(Exception e) {
							System.out.print(e);
							input.nextLine();
						}
						break;
					}
					case 4: {
						System.out.print("**********  SoftDrinks Bill *********\n");
						try{
							File file = new File("cafe/CafeBills/MySoftDrinks/"+regNumber+".txt");
							if(file.exists()){
								Scanner sc = new Scanner(file);
								System.out.print("\n");
								int i=1;
								while(sc.hasNextLine()){
									String line = sc.nextLine();
									System.out.print(line+"\n");
								}
								System.out.print("*****************************************************\n");
								sc.close();
								break;
							}
							else{
								System.out.print("No Record Found\n");
								break;
							}
						}
						catch(Exception e){
							System.out.print(e);
							input.nextLine();
						}
						break;
					}
					case 5: {
						System.out.print("**********   Juices Bill *********\n");
						try{
							File file = new File("cafe/CafeBills/MyJuiceOrPlantDrink/"+regNumber+".txt");
							if(file.exists()){
								Scanner sc = new Scanner(file);
								System.out.print("\n");
								int i=1;
								while(sc.hasNextLine()){
									String line = sc.nextLine();
									System.out.print(line+"\n");
								}
								System.out.print("*****************************************************\n");
								sc.close();
								break;
							}
							else{
								System.out.print("No Record Found\n");
								break;
							}
						}
						catch(Exception e){
							System.out.print(e);
							input.nextLine();
						}
						break;
					}
					case 6: {
						System.out.print("\n");
						flag = true;
						break;
					}
					default: {
						System.out.print("Invalid Data! Try again...\n");
						input.nextLine();
					}
				}

				if(flag){
					break;
				}
			}
			catch(Exception e){
				System.out.print("Invalid Data! Try again...\n");
				input.nextLine();
			}

		}
	}
	private static void courseDetails() {
		System.out.print("*************************************************\n");
		System.out.print("\t\t Course Details \n");
		System.out.print("*************************************************\n\n");
		input.nextLine();
		
		while(true) {
			System.out.print("Enter the Student ID(XXXX-XXX-XXX): ");
			String regNumber = input.nextLine().toUpperCase(); 
			if(Pattern.matches("SP\\d{2}-\\w{3}-\\d{3}|FA\\d{2}-\\w{3}-\\d{3}", regNumber)) {
				try {
					File file = new File("students_data/courses/"+regNumber+".txt");
					if(file.exists()) {
						manageCourse(regNumber);
					}else {
						File ff = new File("students_data/courses/" +regNumber +".txt");
						if(ff.exists()) {
							System.out.print("Student must have to register a course firstly!\n");
							break;
						}else {
							System.out.print("Student is not registered!\n");
		            		break;
						}
					}
				}catch(Exception e) {
					System.out.print(e);
					input.nextLine();
				}
			}else {
				System.out.print("Invalid ID! Try Again....\n");
			}
		}
	}

	//Quản lý khóa học
	public static void manageCourse(String regNumber) {
		boolean valid = false;
		while(true) {
			System.out.print("1. View Registered Courses\n");
			System.out.print("2. Add new Course\n");
			System.out.print("3. Delete course\n");
			System.out.print("4. Previous Menu\n");
			System.out.print("5. Exit\n");

			System.out.print("Enter choice : ");
			try {
				int choice  = input.nextInt();
				switch(choice) {
					case 1 : {
						valid = true;
						viewCourse(regNumber);
						break;
					}
					case 2 : {
						valid = true;
						addCourse(regNumber);
						break;
					}
					case 3 : {
						valid = true;
						deleteCourse(regNumber);
						break;
					}
					case 4 : {
						valid = true;
						admin();
						break;
					}
					case 5 : {
						valid = true;
						System.exit(0);
						break;			
					}
					default : {
						System.out.print("Invalid input! Try Again!!!\n");
					}
				}
			}catch(Exception e) {
				System.out.print("Invalid input! Try again!!\n");
				input.nextLine();
			}
		}
		
	}
	// Delete Course
	private static void deleteCourse(String regNumber) {
		viewCourse(regNumber);
		
		int remainingCourses = 0;
		int canDelete = registeredCourses.length-4;
		while(true) {
			System.out.print("You can drop only "+(canDelete)+" courses because student must have pick 4 courses!\n");
			try {
				if(canDelete == 0) {
					System.out.print("You can't drop any course\n");
					break;
				}
				System.out.print("\nHow many courses student wants to drop: ");
				int amount = input.nextInt();
				remainingCourses = registeredCourses.length-amount;
				
				if ( amount > canDelete) {
					System.out.print("You can drop only "+(canDelete)+" courses because student must have pick 4 courses!\n");
                    continue;
				}else if (amount < 0) {
					 System.out.print("Invalid input! Try again!\n");
	                    continue;
				}else if(amount==0 || canDelete==0){
                	System.out.print("No course can be dropped!\n");
                	break;
                }
				// Lưu trữ id khóa học trong mảng..
				int x = 0; 
				while(true) {
					try {
						if ( x== amount) {
							break;
						}
						   System.out.print("\n*************course"+(x+1)+"*******************\n");
	                       System.out.print("Enter choice :");
	                       int choice = input.nextInt();
	                       
	                       if (choice < registeredCourses.length) {
	                    	   if ( registeredCourses[choice]=="") {
	                    		   System.out.print("Same course can't be delete twice or more!\n");
	                    		   continue;
	                    	   }else {
	                    		   registeredCourses[choice] = "";
	                    		   x++;
	                    	   }
	                       }else {
	                    	   System.out.print("Invalid ID, Try Again!\n");
	                    	   continue;	
	                       }
					}catch(Exception e) {
						System.out.print("Invalid input! Try again...");
                        input.nextLine();
					}
				}
				try {
					   FileWriter fileWriter = new FileWriter("students_data/courses/"+ regNumber +".txt");
	                    fileWriter.write("");
	                    fileWriter.close();
	                }
	                catch(Exception e){
	                    System.out.print(e);
	                }
			}catch(Exception e) {
				  System.out.print("Invalid input! Try again\n");
	                input.nextLine();
	                continue;
			}
			  try{
		            FileWriter ff = new FileWriter("students_data/courses/"+ regNumber +".txt",true);
		            for(int i=0;i<registeredCourses.length;i++){
		            	if(registeredCourses[i]!=""){
		            		ff.write(registeredCourses[i]+"\n");
		            	}
		            }
		            ff.close();
		        }
		        catch(Exception e){
		            System.out.print(e);
		        }

		        System.out.print("Courses deleted Successfully!\n");
		        break;

		}
		
	}
	// Add new Course
	private static void addCourse(String regNumber) {
		int semester = 0;
		String sub = "";
		try {
			sub = regNumber.substring(0,4);
			if(sub.compareTo("SP18")==0) {
				semester = 8;
			}else if ( sub.compareTo("FA18")== 0) {
			    semester = 7;
			}
			else if ( sub.compareTo("SP19")== 0) {
			    semester = 6;
			}
			else if ( sub.compareTo("FA19")== 0) {
			    semester = 5;
			}
			else if ( sub.compareTo("SP20")== 0) {
			    semester = 4;
			}
			else if ( sub.compareTo("FA20")== 0) {
			    semester = 3;
			}
			else if ( sub.compareTo("FA21")== 0) {
			    semester = 2;
			}
			else if ( sub.compareTo("FA21")== 0) {
			    semester = 1;
			}
			// tìm tổng_khóa trong tệp
			int total_courses = 0;
			try {
				File file = new File("cours/"+semester+".txt");
				Scanner sc = new Scanner(file);
				while(sc.hasNextLine()) {
					String line = sc.nextLine();
					total_courses++;
				}
				sc.close();
			}catch(Exception e) {
				System.out.print(e);
			}
			courseDetails = new String[total_courses];
			// Xâu chuỗi dữ liệu trong mảng.
			try {
				File file = new File("cours/"+semester+".txt");
				Scanner sc = new Scanner(file);
				int i = 0;
				while(sc.hasNextLine()) {			
					String line = sc.nextLine();
					courseDetails[i] = line;
					i++;
				}
				sc.close();
			}catch(Exception e) {
				System.out.print(e);
			}
			try {
				File file = new File("students_data/courses"+regNumber+".txt");
				Scanner sc = new Scanner(file);
				while(sc.hasNextLine()) {
					String line = sc.nextLine();
					for ( int i = 0 ; i < courseDetails.length;i++) {
						if ( courseDetails[i].compareTo(line)==0) {
							courseDetails[i] = "";
						}
					}
				}
				sc.close();
			}catch(Exception e) {
				System.out.print(e);
			}
			// Didplaying available courses
			int [] space = {15,30,15,30};
			int size = 0; 
			try {
				int i = 0, j =0;
				 System.out.print("\n\n");
	             System.out.printf("%-5s%-15s%-30s%-15s%-30s%n","ID","Course_Code","Course_Title","Credit_Hours","Instructor");
	             System.out.println("------------------------------------------------------------------------------------------");
	             for(String line : courseDetails){
	            	 if(line!="") {
	            		 System.out.printf("%-5d",i);
	            		 String[]data = line.split(",",4);
	            		 size++;
	            		 for( String item: data) {
	            			 System.out.printf(("%-"+space[j]+"s"),item);
	            			 j++;
	            		 }
	            		 i++;
	            		 j=0;
	            		 System.out.println();
	            	 }
	             }
			}catch(Exception e) {
				System.out.print(e);
			}
			availableCourse = new String[size];
			int index = 0; 
			for (int y = 0; y< courseDetails.length;y++) {
				if (courseDetails[y]!="") {
					availableCourse[index]= courseDetails[y];
					index++;
				}
			}
			while(true) {
				try {
					System.out.print("\nHow many courses student want to add more: ");
					int amount = input.nextInt();
					if ( amount > availableCourse.length|| amount <0) {
						System.out.print("Invalid Input\n");
						continue;
					}else if(amount ==0) {
						break;
					}

					// Lưu trữ id khóa học trong mảng..
					int x = 0;
					String [] toRegister = new String[amount];
					boolean alreadyAdded = false;
					while(true) {
						try {
							alreadyAdded = false;
							System.out.print("\n*************course"+(x+1)+"*******************\n");
	                        System.out.print("Enter choice :");
	                        int choice = input.nextInt();
	                        for ( int z = 0 ; z < x ; z ++ ) {
	                        	if (toRegister[z].compareTo(availableCourse[choice])==0) {
	                        		alreadyAdded = true;
	                        		break;
	                        	}
	                        }
	                        if(alreadyAdded) {
	                        	System.out.print("Already Registered!\\n");
	                        	continue;
	                        }else if (choice < availableCourse.length && choice>=0) {
	                        	toRegister[x] = availableCourse[choice];
	                        	x++;
	                        	if( x == amount) {
	                        		break;
	                        	}
	                        }else {
	                        	System.out.print("Invalid ID, Try Again!\n");
	                        	continue;
	                        }
						}catch(Exception e) {
							System.out.print(e);
						}
					}
					try {
						FileWriter file = new FileWriter("students_data/courses/"+ regNumber +".txt",true);
						int j = 0 ;
						for (String e : toRegister) {
							file.write(e+"\n");
						}
						file.close();
						System.out.println("Courses Added Successfully!");
						break;
					}catch(Exception e) {
						 System.out.print(e);
					}
				}catch(Exception e) {
					 System.out.print("Invalid input! Try again");
	                 input.nextLine();
				}
			}
		}catch (Exception e) {
			System.out.print("error occurred!\n");
		}	
	}
	// Xem khóa học
	public static void viewCourse(String regNumber) {
		int length = 0 ; 
		
		try {
			File file = new File("students_data/courses/"+regNumber+".txt");
			Scanner sc = new Scanner(System.in);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				length++;
			}
			sc.close();
		}catch(Exception e) {
			System.out.print(e);
		}
		registeredCourses = new String[length];
		
		System.out.print(regNumber +" registered in following courses : \n");
		int [] space = {15,30,15,30};
		try {
			File file = new File("students_data/courses/"+regNumber+".txt");
			Scanner sc = new Scanner(file);
			int i =0 , j =0; 
			System.out.print("\n\n");
			System.out.printf("%-5s%-15s%-30s%-15s%-30s%n","ID","Course_Code","Course_Title","Credit_Hours","Instructor");
			System.out.println("------------------------------------------------------------------------------------------");
			while(sc.hasNextLine()) {
				System.out.printf("%-5d",i);
				String line = sc.nextLine();
				registeredCourses[i] = line;
				String [] data = line.split(",",4);
				for ( String item: data) {
					System.out.printf(("%-"+space[j]+"s"),item);
					j++;
				}
				i++;
				j= 0;
				System.out.println();
			} 
			sc.close();
		}catch(Exception e) {
			System.out.print(e);
		}
		System.out.print("\n\n");
	}
	// Student details
	private static void studentDetails() {
		System.out.print("*************************************************\n");
		System.out.print("\t\t Student Details \n");
		System.out.print("*************************************************\n\n");
		input.nextLine();
		
		while(true) {
			System.out.print("Enter the Student ID(XXXX-XXX-XXX) : ");
			String regNumber = input.nextLine().toUpperCase();
			if(Pattern.matches("SP\\d{2}-\\w{3}-\\d{3}|FA\\d{2}-\\w{3}-\\d{3}",regNumber)){
				try {
					File file = new File("students_data/"+regNumber+".txt");
					if(file.exists()) {
						Scanner sc = new Scanner(file);
						System.out.println();
						while(sc.hasNextLine()) {
							String line = input.nextLine();
							String [] data = line.split(":");
							System.out.printf("%-20s%-10s%s%n",data[0].toUpperCase(),":",data[1]);
						}
						System.out.print("\n******************************************************************\n");
						sc.close();
						break;
					}else{
						System.out.print("No Record Found\n");
						break;
					}
				}catch(Exception e) {
					System.out.print(e);
					input.nextLine();
				}
			}else{
				System.out.print("Invalid ID! Try Again....\n");
			}
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}
