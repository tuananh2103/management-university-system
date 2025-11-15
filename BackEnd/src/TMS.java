import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.URI;

//Shahzaneer Ahmed --> SP21-BCS-087
// input fields are validated (exceptional handling)
// randomly generation Bus Registration ID
//CRUD
// file handling

public class TMS {

	public static void manage(String regNumber) {
		System.out.println();
		Scanner sc = new Scanner(System.in);
		
		String registrationNo = regNumber;
		String busID = busID(registrationNo);
		
		boolean isRegistered = busFileFound(regNumber);
		boolean feePaid = false;
		boolean feedback = true;
		String busReg = "";
		
		while(true) {
			System.out.println("Enter Corresponding Number to Select the option: ");
			System.out.println("0. Bus Routes\n1. Bus fees\n2. Bus registration\n3. Update info\n4. fee"+
			"Payment\n5. Bus Profile\n6. FeedBack\n7. Delete Bus registration\n8. Previous Menu\n9. Exit TMS\n");
			String selection = sc.next();
			
			if ( selection.equals("9")) {
				System.out.println("Thank you for using Transport Management System!");
				System.exit(0);
			}else if ( selection.equals("8")) {
				TransportManagement.manage(regNumber);
			}
			else if (selection.equals("0")||selection.equals("1")||selection.equals("2")||selection.equals("3")||
					selection.equals("4")||selection.equals("5")||selection.equals("6")||selection.equals("7")) {
				switch(selection) {
				case "0" ->{
					//hien thi tuyen duong cung cac tram cua tuyen duong 
					displayRoutesAndStops(registrationNo);
					try {
						Thread.sleep(4000);
					}catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				case "1" ->{
					//hien thi phi cua bus
					displayBusFees(registrationNo);
						try  {
							Thread.sleep(4000);
						}catch (InterruptedException e) {
						e.printStackTrace();
						}
				}
				case "2" -> {
					// them nguoi dang ky moi 
					if (busFileFound(registrationNo)) {
						System.out.println("Your Account Already exists in our database ! ");
						isRegistered = true;
					}
					else if (!busFileFound(registrationNo)) {
						busReg = busRegistration(registrationNo);
						
						File busFile = new File("transport/TransportFiles/"+registrationNo+".txt");
						try {
							busFile.createNewFile();
							isRegistered = true;
						}catch (IOException e) {
							System.out.println("Sorry we found some issuses creating your file!");
						}
						try {
							FileWriter busFileWriter = new FileWriter("transport/TransportFiles/"+registrationNo+".txt");
							busFileWriter.write(busReg);
							System.out.println("\n************** Registered Succesfully! **************\n\n");
							busFileWriter.close();
						}catch(IOException e) {
							System.out.println("Sorry we found some issuses inserting your details in the our database");
						}
					}
				}
				case "3" -> {
					// update du lieu duoc dang ky 
					
					if ( isRegistered) {
						busReg = busRegistration(registrationNo);
						feePaid = false;
						try {
							FileWriter busFileWriter = new FileWriter("transport/TransportFiles/"+registrationNo+".txt");
							busFileWriter.write(busReg);
							System.out.println("\n************** Data Updated Successfully **************\n\n");
							busFileWriter.close();
						}catch(IOException e) {
							System.out.println("Sorry we found some issuses inserting your details in the our database");
						}
					}else {
						   System.out.println("Kindly Register first !");
					}
				}
				case "4" -> {
					if (!feePaid) {
						paymentMethod(registrationNo, isRegistered);
						feePaid = true;
					}else if (feePaid){
						System.out.println("You have already paid the fee !");
					}
				}
				case "5" -> {
					// thong tin xe bus
					
					File busFilex = new File("transport/TransportFiles/"+registrationNo+".txt");
					
					if (!busFilex.exists())System.out.println("Your profile doesn't exist!");
					else if (feePaid && busFilex.exists()) {
						try {
							File busFile = new File("transport/TransportFiles/"+registrationNo+".txt");
							Scanner input = new Scanner(System.in);
							
							while(input.hasNextLine()) {
								String profile = input.nextLine();
								System.out.println(profile);
							}
							input.close();
							
						} catch(Exception e){
                            System.out.println("We found some issues in reading your data! try again!");
                        }
					}
				}
				case "6" ->{
					
					if (!feedback && isRegistered) {
						String busReview = busReviewMessage(registrationNo);
						feedback = true;
						try {
							FileWriter busFileWriter = new FileWriter("transport/TransportFiles/\"+registrationNo+\".txt");
							String busReviewFinal = String.format("\n\nYour FeedBack --->\n %s", busReview);
							busFileWriter.write(busReviewFinal);
							busFileWriter.close();
						}catch(IOException e) {
							System.out.println("Sorry we found some issues inserting your details in the our database");
						}
					}else if (!isRegistered)System.out.println("You cannot give feedback without registering to TMS");
					else if (busFileFound(registrationNo))System.out.println("You have already given the feedback!");
				}
				case "7" ->{
					File busFile= new File("transport/TransportFiles/\"+registrationNo+\".txt");
					if ( busFile.delete()) {
						System.out.println("The bus profile has been deleted sucessfully!");
						isRegistered = false;
						feePaid = false;
					}else if(!busFile.exists()) {
						System.out.println("No record found ! You haven't registered yet!");
					}else {
						System.out.println("Something went wrong");
					}
				}
			}
		}else System.out.println("Invalid Input!");
	}
}
	  static void displayBusFees(String registrationNo){
	        System.out.println("**************** Buses Fees ***********");
	        System.out.println("Route 1 : 20,000đ\nRoute 2 : 15,000đ\nRoute 3 : 14,000đ\nRoute 4 : 13,000đ\nRoute 5 : 10,000đ"
	        		+ "\nRoute 6 : 22,000đ\nRoute 7 : 24,000đ\nRoute 8 : 21,000đ\n\n");

	    }
	  static void displayRoutesAndStops(String registrationNo) {
		  System.out.println("--------------------------------Route # 1-----------------------------------------------");
		  System.out.println("  Stop --> Gia Lâm -> Đường Ngô Gia Khảm -> Đường Ngọc Lâm -> Đường Nguyễn Văn Cừ->"
		  		+ "\n			Qua Cầu Chương Dương -> Đường Trần Nhật Duật -> Đường Yên Phụ -> "
		  		+ "\n			Điểm trung chuyển Long Biên -> Đường Hàng Đậu -> Đường Hàng Cót -> "
		  		+ "\n			Đường Hàng Gà -> Đường Hàng Điếu -> Đường Đường Thành – Phủ Doãn -> "
		  		+ "\n			Đường Triệu Quốc Đạt -> Đường Hai Bà Trưng-> Đường Lê Duẩn -> "
		  		+ "\n			Đường Khâm Thiên -> Đường Ô Chợ Dừa -> Đường Quay đầu tại điểm mở dải phân cách của Ô Chợ Dừa -> "
		  		+ "\n			Đường Nguyễn Lương Bằng -> Đường Tây Sơn -> Đường Ngã tư Sở -> Đường Nguyễn Trãi -> "
		  		+ "\n			Đường Trần Phú (Hà Đông)-> Đường Quang Trung (Hà Đông)-> "
		  		+ "\n			Đường Ba La – Quốc lộ 6 -> điểm cuối BX Yên Nghĩa");
		  System.out.println("--------------------------------Route # 2-----------------------------------------------");
		  System.out.println("  Stop --> Dốc Bác Cổ -> ĐườngTrần Khánh -> Dư Đường Trần Hưng Đạo -> "
		  		+ "\n			Đường Lê Thánh Tông-> Đường Hai Bà Trưng -> Đường Quang Trung -> "
		  		+ "\n			Đường Tràng Thi -> Đường Điện Biên Phủ -> Đường Trần Phú -> "
		  		+ "\n			Đường Chu Văn An -> Đường Tôn Đức Thắng -> Đường Nguyễn Lương Bằng -> "
		  		+ "\n			Đường Tây Sơn – Ngã tư Sở – Nguyễn Trãi -> Đường Trần Phú -> "
		  		+ "\n			Đường  Quang Trung -> Đường Ba La -> Đường Quốc lộ 6 – BX Yên Nghĩa");
		  System.out.println("--------------------------------Route # 3-----------------------------------------------");
		  System.out.println("  Stop --> BX Giáp Bát -> Đường Giải Phóng -> Đường  Lê Duẩn -> "
		  		+ "\n			Đường Nguyễn Thượng Hiền -> Đường  Yết Kiêu -> Đường Trần Hưng Đạo -> "
		  		+ "\n			Đường Trần Khánh Dư -> Đường Trần Quang Khải -> Đường  Trần Nhật Duật -> "
		  		+ "\n			Đường Long Biên -> Đường Trần Nhật Duật –> Cầu Chương Dương –> "
		  		+ "\n 			Đường Nguyễn Văn Cừ Đường Nguyễn Sơn Đường Ngọc Lâm Đường Ngô Gia Khảm – bx Gia Lâm");
		  System.out.println("--------------------------------Route # 4-----------------------------------------------");
		  System.out.println("  Stop --> BX Nước Ngầm -> Đường Ngọc Hồi -> " 
		  		+ "\n			Đường Giải Phóng – Bến xe Giáp Bát -> Đường Giải Phóng -> "
		  		+ "\n			Đường Đại La -> Đường Minh Khai – Cầu Vĩnh Tuy -> "
		  		+ "\n			Đường Đàm Quang Trung -> Đường Chu Huy Mân -> "
		  		+ "\n			Đường Hội Xá -> Đường Vũ Xuân Thiều -> "
		  		+ "\n			Đường Phúc Lợi/Ngõ 193 Phúc lợi – Ngách 195/9 Phúc Lợi -> Đường Phúc Lợi");
		  System.out.println("--------------------------------Route # 5-----------------------------------------------");
		  System.out.println("  Stop --> Quận Long Biên -> Điểm trung chuyển -> "
		  		+ "\n			Đường Trần Nhật Duật -> Đường Nguyễn Hữu Huân -> Đường Lý Thái Tổ -> "
		  		+ "\n			Đường Ngô Quyền -> Đường Hai Bà Trưng -> Đường Lê Thánh Tông -> "
		  		+ "\n			Đường Trần Thánh Tông -> Đường Tăng Bạt Hổ – Yecxanh -> Đường Lò Đúc -> "
		  		+ "\n			274 Đường Trần Khát Chân -> Đường Kim Ngưu -> "
		  		+ "\n			Đường Nguyễn Tam Trinh -> Đường Lĩnh Nam –> Đường dẫn cầu Thanh Trì –> "
		  		+ "\n 			Pháp Vân –> Rẽ trái tại điểm giao cắt tại vành đai 3 và Ngọc Hồi Thanh Trì -> "
		  		+ "\n			Quay đầu xe tại công ty ABB -> BX Nước Ngầm");
		  System.out.println("--------------------------------Route # 6-----------------------------------------------");
		  System.out.println("  Stop --> BX Giáp Bát -> Đường Giải Phóng -> Đường Kim Đồng -> "
		  		+ "\n			Đường Giải Phóng -> Đường Ngọc Hồi -> Đường Quốc Lộ 1 ->Đường Liên Ninh -> "
		  		+ "\n			Đường Quán Gánh – Thị trấn Thường Tín -> Đường Tía ->Đường Đỗ Xá – Nghệ – Thị trấn Phú Xuyên -> "
		  		+ "\n			Đường Guột – Cầu Giẽ" );
		  System.out.println("--------------------------------Route # 7-----------------------------------------------");
		  System.out.println("  Stop --> bx Giáp Bát -> Đường Giải Phóng -> "
		  		+ "\n			Đường Kim Đồng – quay đầu tại điểm mở -> Đường Kim Đồng -> "
		  		+ "\n			Đường Giải Phóng -> Đường Ngọc Hồi –> Đường Thường Tín -> "
		  		+ "\n			Đường 427 -> Đường Hồng Vân (Vân La, Thường Tín – Bãi đỗ xe Thu Hoài)");
		  System.out.println("--------------------------------Route # 8-----------------------------------------------");
		  System.out.println("  Stop --> BX Giáp Bát -> Đường Giải Phóng -> "
		  		+ "\n			Đường Kim Đồng – quay đầu tại điểm mở -> Đường Kim Đồng -> "
		  		+ "\n			Đường Giải Phóng -> Đường Ngọc Hồi ->Đường Thường Tín -> "
		  		+ "\n			Đường Tía -> Đường Ngã ba Đỗ Xá -> Đường 429 (phía Đông) -> "
		  		+ "\n			Đường Phú Minh (Phú Xuyên – Công ty Việt Tuyến)");
	  }
	//	bus registration
	public static String busID(String registrationNo) {
		Random random = new Random();
		int number =(int) 1000 + random.nextInt();
		String busID = "" + number;
		return busID;
	}
	static String registrationTiming(String registrationNo) {
		// Registration date and timing
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy || h:m a"));
	}
	public static String busRoute(String registrationNo) {
		Scanner sc = new Scanner(System.in);
		String route;
		while(true) {
			
			System.out.println("Enter your route: ");
			route = sc.next();
			if( route.equals("1")||route.equals("2")||route.equals("3")||
					route.equals("4")|| route.equals("5")||
					route.equals("6")|| route.equals("7")|| route.equals("8")) {
				return route;
			}else {
				System.out.println("Enter valid route no!");
			}
		}
	}
	public static boolean find(String routeName, String []array) {
		for (int i = 0 ; i<array.length; i++) {
			if(routeName.equalsIgnoreCase(array[i])) {
				return true;
			}
		}
		return false;
	}
	
	public static String [] routeName(String registrationNo, String route) {
		String [] routeBus01 = {" Gia Lâm", "Đường Ngô Gia Khảm ", "Đường Ngọc Lâm", "Đường Nguyễn Văn Cừ ",
				"Qua Cầu Chương Dương" , "Đường Trần Nhật Duật","Đường Yên Phụ" ,
				"Điểm trung chuyển Long Biên","Đường Hàng Đậu ","Đường Hàng Cót" ,
				" Đường Hàng Gà"," Đường Hàng Điếu" ,"Đường Đường Thành – Phủ Doãn", 
				"Đường Triệu Quốc Đạt", "Đường Hai Bà Trưng", "Đường Lê Duẩn ", 
				"Đường Khâm Thiên ", "Đường Ô Chợ Dừa ", "Đường Quay đầu tại điểm mở dải phân cách của Ô Chợ Dừa "
				,"Đường Nguyễn Lương Bằng", "Đường Tây Sơn" , "Đường Ngã tư Sở" , "Đường Nguyễn Trãi" ,
				"Đường Trần Phú (Hà Đông) "," Đường Quang Trung (Hà Đông)",
				" Đường Ba La – Quốc lộ 6" ,"điểm cuối BX Yên Nghĩa"};
		String [] routeBus02 = {" Dốc Bác Cổ ->", "ĐườngTrần Khánh Dư ->"," Đường Trần Hưng Đạo -> ",
				"Đường Lê Thánh Tông ->", "Đường Hai Bà Trưng ->", "Đường Quang Trung ->", 
				"Đường Tràng Thi ->" ,"Đường Điện Biên Phủ ->", "Đường Trần Phú ->",
				"Đường Chu Văn An ->", "Đường Tôn Đức Thắng ->", "Đường Nguyễn Lương Bằng ->",
				"Đường Tây Sơn – Ngã tư Sở – Nguyễn Trãi ->"," Đường Trần Phú ->",
				"Đường  Quang Trung ->","Đường Ba La ->", "Đường Quốc lộ 6 – BX Yên Nghĩa."};
		String [] routeBus03 = {"BX Giáp Bát ->", "Đường Giải Phóng ->", "Đường  Lê Duẩn ->" ,
				"Đường  Nguyễn Thượng Hiền ->", "Đường  Yết Kiêu ->", "Đường Trần Hưng Đạo ->", 
				"Đường  Trần Khánh Dư ->","Đường Trần Quang Khải ->" ,"Đường  Trần Nhật Duật ->" ,
				"Đường Long Biên ->", "Đường Trần Nhật Duật –>","Cầu Chương Dương –>", 
				"Đường Nguyễn Văn Cừ Đường Nguyễn Sơn Đường Ngọc Lâm Đường Ngô Gia Khảm – bx Gia Lâm"};
		String []routeBus04 = {"BX Nước Ngầm ->" ,"Đường Ngọc Hồi ->" ,
				"Đường Giải Phóng – Bến xe Giáp Bát ->" ,"Đường Giải Phóng ->", 
				"Đường Đại La ->", "Đường Minh Khai – Cầu Vĩnh Tuy ->",
				"Đường Đàm Quang Trung ->", "Đường Chu Huy Mân ->", 
				"Đường Hội Xá ->", "Đường Vũ Xuân Thiều – Đường Phúc Lợi/ Ngõ 193 Phúc lợi – Ngách 195/9 Phúc Lợi – Ngõ 195 Phúc Lợi ->", "Đường Phúc Lợi"} ;
		String[]routeBus05 = {"Quận Long Biên ->", "Điểm trung chuyển ->", 
				"Đường Trần Nhật Duật ->", "Đường Nguyễn Hữu Huân ->", "Đường Lý Thái Tổ ->" ,
				"Đường Ngô Quyền ->" ,"Đường Hai Bà Trưng ->", "Đường Lê Thánh Tông ->", 
				"Đường Trần Thánh Tông ->", "Đường Tăng Bạt Hổ – Yecxanh ->", "Đường Lò Đúc ->", 
				"274 Đường Trần Khát Chân ->", "Đường Kim Ngưu ->", 
				"Đường Nguyễn Tam Trinh –>" ,"Đường Lĩnh Nam –>" ,"Đường dẫn cầu Thanh Trì –> ","Pháp Vân –> ",
				"Rẽ trái tại điểm giao cắt tại vành đai 3 và Ngọc Hồi Thanh Trì  ->"
				,"Quay đầu xe tại công ty ABB -> BX Nước Ngầm"};
		String[]routeBus06 = { "BX Giáp Bát ->", "Đường Giải Phóng ->","Đường Kim Đồng ->" ,
				"Đường Giải Phóng ->","Đường Ngọc Hồi ->" ,"Đường Quốc Lộ 1 ->", "Đường Liên Ninh ->",
				"Đường Quán Gánh – Thị trấn Thường Tín ->","Đường Tía ->","Đường Đỗ Xá – Nghệ – Thị trấn Phú Xuyên ->", 
				"Đường Guột – Cầu Giẽ"};
		String[] routeBus07 = {"bx Giáp Bát ->", "Đường Giải Phóng ->", 
				"Đường Kim Đồng – quay đầu tại điểm mở ->" ,"Đường  Kim Đồng -> ",
				"Đườn  Giải Phóng ->", "Đường Ngọc Hồi –>", "Đường Thường Tín ->" ,
				"Đường 427 ->", "Đường Hồng Vân (Vân La, Thường Tín – Bãi đỗ xe Thu Hoài)"};
		String[] routeBus08 = {"BX Giáp Bát ->","Đường Giải Phóng ->" ,
				"Đường Kim Đồng – quay đầu tại điểm mở ->", "Đường Kim Đồng ->",
				"Đường Giải Phóng ->" ,"Đường Ngọc Hồi ->","Đường Thường Tín ->",
				"Đường Tía ->" ,"Đường Ngã ba Đỗ Xá ->","Đường 429 (phía Đông) ->",
				"Đường Phú Minh (Phú Xuyên – Công ty Việt Tuyến)"};
		switch(route) {
			case "1" -> {
				return routeBus01;
			}
			case "2" -> {
				return routeBus02;
			}
			case "3" -> {
				return routeBus03;
			}
			case "4" -> {
				return routeBus04;
			}
			case "5" -> {
				return routeBus05;
			}
			case "6" -> {
				return routeBus06;
			}
			case "7" -> {
				return routeBus07;
			}
			case "8" -> {
				return routeBus08;
			}
		}
		return new String[]{};
	}
	static String busStop(String registrationNo, String route){
        Scanner input = new Scanner(System.in);
        String routeName;
        while(true) {
            System.out.println("Enter the stop name :");
            routeName = input.nextLine();
            if (find(routeName,routesNameStr(registrationNo,route))){
                break;
            }
            else System.out.println("Invalid route Name!");

        }
        return routeName;
    }
	public static String busFee(String registrationNo, String route) {
		 String fees = "";
	        switch (route) {
	            case "1" -> fees = "20.000đ";
	            case "2" -> fees = "15.000đ";
	            case "3" -> fees = "14.000đ";
	            case "4" -> fees = "13.000đ";
	            case "5" -> fees = "10.000đ";
	            case "6" -> fees = "22.000đ";
	            case "7" -> fees = "24.000đ";
	            case "8" -> fees = "21.000đ";
	        }
	        return fees;
	}
	public static String busDropOff(String registrationNo) {
		while(true) {
			Scanner input = new Scanner(System.in);
			
			System.out.println("Enter your preferred timming for drop-off\n1. 08:00AM\n2. 14:00PM\n3. 19:00PM");
			String selectionDropOff = input.nextLine();
			String dropOffTime = "";
			if(selectionDropOff.equals("1")|| selectionDropOff.equals("2")|| selectionDropOff.equals("3")) {
				switch(selectionDropOff) {
				case "1" -> dropOffTime ="08:00PM";
				case "2" -> dropOffTime ="02:00PM";
				case "3" -> dropOffTime ="07:00PM";
				}
				return dropOffTime;
			}else {
				System.out.println("Kindly Enter valid input! ");
			}
		}
	}
	public static String busPickUp(String registration ) {
		Scanner input = new Scanner(System.in);
		while(true) {
			System.out.println("Enter your preferred timing for pick-up\n1.  07:00AM\n2. 13:00PM\n3. 18:00PM");
			String selectionPickUp = input.nextLine();
			String PickUpTime = "";
			if (selectionPickUp.equals("1")|| selectionPickUp.equals("2")|| selectionPickUp.equals("1")) {
				switch(selectionPickUp) {

				case "1" -> PickUpTime ="07:00AM";
				case "2" -> PickUpTime ="13:00PM";
				case "3" -> PickUpTime ="18:00AM";
				
				}
			}
		}
	}
	public static String busReviewMessage(String registrationNo) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your Review for Transport Management");
		String review = sc.nextLine();
		System.out.println("Thank you for your feedback about our Bus Management System");
		return review;
	}
	static void busReview(String registrationNo, boolean isRegistered) {
		
	}
	static String busRegistration(String registrationNo) {
		Scanner input = new Scanner(System.in);
		String busID = busID(registrationNo);
		String DateAndTime = registrationTiming(registrationNo);
		String route = busRoute(registrationNo);
		String busStop = busStop(registrationNo,route);
			
		String busFee= busFee(registrationNo,route);
		String pickUp = busPickUp(registrationNo);
		String dropOff  = busDropOff(registrationNo);
		
		String details = String.format("""
				Registration No                  : %s
                Bus ID                           : %s
                Route                            : %s
                Stop Name                        : %s
                Fee                              : %s
                PickUp Timing                    : %s
                DropOff Timing                   : %s
                Registered at                    : %s
				""",registrationNo,busID,route,busStop,busFee,pickUp,dropOff,DateAndTime);
		return details;
	}
	static void paymentMethod(String registrationNo, boolean isRegistered) {
		if(isRegistered) {
			Desktop desk = Desktop.getDesktop();
			
			Scanner sc = new Scanner(System.in);
			
			while( true) {
				System.out.println("Select your payment method: :\n1. Money\n2. Visa card\n3. JazzCash");
				
				try {
					int option = sc.nextInt();
					if(option >= 1 && option <=3) {
						switch(option) {
							case 1 ->{
									 System.out.println("Thank you for using JazzCash for paying fee");
								}
							case 2 -> {
								try {
									desk.browse(new URI("https://www.visa.fr/"));
									System.out.println("Thank you for using visa for paying fee");
								}catch (IOException | URISyntaxException e) {
                                    System.out.println("Sorry we found an issue try again!");
							}
						}
							 case 3 -> {
	                                try {
	                                	desk.browse(new URI("https://www.jazzcash.com.pk"));
	                                    System.out.println("Thank you for using jazzcash for paying fee");

	                                } catch (IOException | URISyntaxException e) {
	                                    System.out.println("Sorry we found an issue try again!");

	                                }
							 }
					}
						break;
				}else System.out.println("Enter appropriate option !");
				
				}catch(Exception e) {
					 System.out.println("Invalid input !");
	                    sc.nextLine(); //to clear the  buffer
				}
			}
		}else System.out.println("You have to register first to proceed Payment!");
	}
	static boolean busFileFound(String registrationNo) {
		File busFile = new File("transport/TransportFiles/"+registrationNo+".txt");
		if(busFile.exists())return true;
		else return false;
	}
	private static String[] routesNameStr(String registrationNo, String route) {
		// TODO Auto-generated method stub
		return null;
	}
}	


