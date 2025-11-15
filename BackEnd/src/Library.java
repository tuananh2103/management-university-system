//Importing all required packages
import java.util.Scanner;
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.*;
import java.io.*;

public class Library {
	 //Khởi tạo và khai báo các biến và mảng cần thiết
	static Scanner input = new Scanner(System.in);
	static String[] borrowedbooks;
	static int[] borrowedBooksIndexes;
	static int b = 20;
	static String []books = new String[b];
	static int totalBooks = 0;
	static int userChoice;
	static int dateformater;
	static String borrowedDate;
	static String returnedDate;
	
	public static void main(String regNumber) {
		Arrays.fill(books, "");
		boolean flag = true;
		while(flag) {
			//Hiển thị menu chính
			 System.out.println("******************************************");
			 System.out.println("Welcome to Library Management System!");
			 System.out.println("Enter 1 to search books");
			 System.out.println("Enter 2 to read membership criteria ");
			 System.out.println("Enter 3 to borrow books ");
			 System.out.println("Enter 4 to return books ");
			 System.out.println("Enter 5 to previous menu");
			 System.out.println("Enter 6 to exit ");
			 System.out.println("******************************************");
			 try {
				 System.out.println("Enter you choice: ");
				 userChoice = input.nextInt();
				 if ( userChoice == 3 ) {
					 TermsAndConditions.displayTerms();
					 System.out.println("Do you accept the terms Press 1 to accept and 2 to reject: ");
					 int option = input.nextInt();
					 
					 if (option == 1 ) {
						 borrowBook(regNumber);
					 }else if (option == 2) {
						 System.out.println("You must have to accept terms and condition, otherwise you can't buy any book!.");
					 }else {
						 System.out.println("Invalid Input! \n");
						 input.nextLine();
					 }
				 }else if (userChoice == 4) {
					 System.out.print("");
					 returnBook(regNumber);
				 }else if ( userChoice == 2) {
					 System.out.print("");
					 TermsAndConditions.displayTerms();
				 }else if ( userChoice == 1) {
					 searchBook();
				 }else if ( userChoice == 5) {
					 LibraryManagement.manage(regNumber);
				 }else if ( userChoice == 6) {
					 System.exit(0);
				 }else {
					 System.out.println("Invalid Input!");
				 }
			 }catch (Exception e) {
				 System.out.println("Invalid Input Try Again! \n");
				 input.nextLine();
			 }
		}
	}
	//Module mượn sách
	public static void borrowBook(String regNumber) {
		System.out.println("******************************************");
		System.out.println("Available books are: ");
		try {
			File file = new File("Library/AvailableBooks.txt");
			Scanner sc = new Scanner(System.in);
			int j = 1;
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line!= "") {
					books[j-1] = line;
					System.out.println(j + ". "+line);
					totalBooks++;	
				}
				j++;
			}
			sc.close();
			System.out.println("******************************************");
		}catch (Exception e) {
			 System.out.println("Error occurred!");
		}
		if ( totalBooks == 0 ) {
			System.out.println("!!!!!! No Record Found !!!!!!\n");
		}else {
			boolean flag = false;
			while(true) {
				try {
					// số lượng sách đã mua từ người dùng
					Scanner input = new Scanner(System.in);
					System.out.print("How many books you want to borrow: ");
					int numberOfPurchasedBooks = input.nextInt();
					borrowedbooks = new String[numberOfPurchasedBooks];
					borrowedBooksIndexes = new int[numberOfPurchasedBooks +1];

					//Xác thực đầu vào
					if( numberOfPurchasedBooks <= totalBooks && numberOfPurchasedBooks > 0) {
						for ( int i = 0 ; i< numberOfPurchasedBooks; i++ ) {
							System.out.println("******************************************");
							System.out.printf("Record Book %d \n",i+1);
							int previousBook = 0;
							System.out.println("Which book you want to borrow, Enter its number: ");
							int bookNumber = input.nextInt();
							try {
								if ( bookNumber >0 && bookNumber <= b) {
									//Hiển thị sách có sẵn
									File file = new File("Library/AvailableBooks.txt");
									Scanner sc = new Scanner(file);
									int index = 1;
									while( sc.hasNextLine()) {
										String line = sc.nextLine();
										if ( index == bookNumber) {
											borrowedbooks[i] = line;
											if ( bookNumber == previousBook) {
												System.out.println("This book is booked. Try Another!");
											}
											previousBook = bookNumber;
											break;
										}
										index++;
									}
									sc.close();
								}else {
									System.out.printf("Invalid Book Number");
									i -=1;
								}
							}catch(Exception e) {
								System.out.println("Invalid Input! Try Again");
								i -= 1;
							}
						}
						System.out.println("Successfully Borrowed " + numberOfPurchasedBooks +" books");
						flag = true;
					}else {
						System.out.print("Invalid Input! Only Books" +totalBooks + " are available.\n");
					}
				}catch(Exception e) {
					System.out.print("Invalid input! Try Again\n");
				}
				if(flag) {
					break;
				}
			}
			//Tính ngày mượn
			try {
				SimpleDateFormat dateAndTimeFormatter = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				borrowedDate = dateAndTimeFormatter.format(date);
				
				File myFile = new File("Library/BorrowedBooks/"+regNumber+".txt");
				boolean value = myFile.createNewFile();
				FileWriter fileWriter = new FileWriter("Library/BorrowedBooks/"+regNumber+".txt",true);
				fileWriter.write("Borrowed at: " + dateAndTimeFormatter.format(date) + "\n");
				
				for(String book : borrowedbooks) {
					fileWriter.write(book + "\n");
				}
				fileWriter.close();
			}catch (Exception e) {
				System.out.print(e+"\n");
			}
			for ( int n = 0; n < books.length;n++) {
				for ( int m = 0 ; m < borrowedbooks.length;m++ ) {
					if ( borrowedbooks[m].compareToIgnoreCase(books[n])== 0) {
						books[n] = "";
						break;
					} 
				}
			}
			// Ghi tệp có sẵn dưới dạng trống
			try {
				File myFile = new File("Library/AvailableBooks.txt");
				FileWriter fileWriter = new FileWriter(myFile);
				fileWriter.write("");
				fileWriter.close();
				
			}catch(Exception e) {
				  System.out.print(e+"\n");
			}
			//Tạo file mới cùng tên để lưu trữ sách có sẵn sau khi mượn
			try {
				File myFile = new File("Library/AvailableBooks.txt");
				boolean value = myFile.createNewFile();
				FileWriter fileWriter = new FileWriter(myFile,true);
				for ( String bk : books) {
					if (bk !="") {
						fileWriter.write(bk+"\n");
					}
				}
				fileWriter.close();
			}catch(Exception e) {
				 System.out.print(e+"\n");
			}
			Arrays.fill(borrowedbooks, "");
			Arrays.fill(books,"");
			totalBooks = 0;
		}
	}

	// module trả sách
	public static void returnBook(String regNumber) {
		//Kiểm tra ngày trả sách
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		returnedDate = formatter2.format(date);
		
		int choice = 0;
		try {
			//Hiển thị sách mượn
			File fileObj = new File("Library/BorrowedBooks/" +regNumber+".txt");
			if (fileObj.exists()) {
				try {
					System.out.print("\nYou have borrowed following books: \n");
					File file = new File("Library/BorrowedBooks/" +regNumber+".txt");
					Scanner data = new Scanner(file);
					int i = 0 ;
					while(data.hasNextLine()) {
						String line = data.nextLine();
						System.out.print(line +"\n");
						books[i] = line;
						i++;
					}
					data.close();
				}catch(Exception e) {
					
				}
				// chọn Menu
				 System.out.print("\n************************************\n");
		         System.out.print("\n1. Return books\n");
		         System.out.print("2. Previous menu\n");
		         
		         boolean flag = true;
		         while(true) {
		        	 try {
		        		 System.out.print("Enter your choice: ");
		        		 choice = input.nextInt();
		        		 if ( choice == 1 ) {
		        			 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		        			 Date date1 = sdf.parse(borrowedDate);
		        			 Date date2 = sdf.parse(returnedDate);
		        			 
		        			 System.out.println("Borrowed date: " +sdf.format(date1));
		        			 System.out.println("Returned date: " +sdf.format(date2));
		        			 
		        			 int result = date1.compareTo(date2);
		        			 
		        			 if ( result == 0) {
		        				 System.out.println("No fine is declared to " +regNumber);   				 
		        			 }else if (result > 30) {
		        				 int fine = 50 ; 
		        				 System.out.println("You have to pay 50 rupees fine per day!");
		        			 }else {
		        				 System.out.println("Correct your calendar");
		        			 }
		        			 		flag = true;
		        			 		FileWriter writer = new FileWriter("Library/AvailableBooks.txt");
		        			 		for ( int a = 0; a < books.length;a++) {
		        			 			writer.write(books[a] +"\n");
		        			 		}
		        			 		writer.close();
		        			 		
		        			 		File myObj = new File("Library/BorrowedBooks/" +regNumber+".txt");
		        			 		if (myObj.delete()) {
		        			 			System.out.println("Books has been returned Successfully!");
		        			 		}else {
		        			 			System.out.println("Some error occured in deleting your date \n");
		        			 		}
		        		 }else if (choice == 2) {
     			 			flag = true;
     			 			System.out.println("Return to previous menu...");
     			 			
     			 		}else {
     			 			System.out.println("Invalid choice... Try Again! \n");
     			 		}
		        	 }catch(Exception e) {
		        		 System.out.println("Invalid choice... Try Again!"+"\n");
		        		 input.nextLine();
		        	 }
		        	 if(flag) {
		        		 break;
		        	 }
		         }
			}else {
				System.out.println("You have not borrowed any book!\n");
			}
		}catch(Exception e) {
			   System.out.println("Error Occurred!");
		}
	}
	//Module tra cứu sách
	public static String searchBook() {
		boolean process = false;
		Scanner input = new Scanner(System.in);
		 //Lấy tên sách từ người dùng
		System.out.println("Enter book's name you want to search: ");
		String searchBookName = input.nextLine().toLowerCase();
		String line = "";
		
		try {
			//Chỉ định tệp để tìm kiếm sách
			File file = new File("Library/AvailableBooks.txt");
			Scanner console = new Scanner(System.in);
			//Tìm kiếm tên sách từ các tập tin có sẵn
			int i =  0; 
			while(console.hasNextLine()) {
				line = console.nextLine().toLowerCase();
				if(line.contains(searchBookName)) {
					System.out.println((i+1) + ". " + line);
					process = true;
				}
				i++;
			}
		}catch(Exception e) {
			System.out.println("Any Error while reading the Available file");
			input.nextLine();
		}
		//Không có trường hợp đầu vào phù hợp
		if (!process) {
			System.out.printf("No book available with name \"%s\"Try Another! \n",searchBookName);
		}
		return line;
	}
}
