package university.management;

import java.util.regex.*;
import java.util.*;
import java.io.File;
import java.io.FileWriter;

import university.management.transport.TransportManagement;
import university.management.library.LibraryManagement;
import university.management.cafe.CafeManagement;
import university.management.courses.Courses;
import university.management.admin.Admin;

public class Main {
    static Scanner input = new Scanner(System.in);

    // Đối tượng hệ thống quản lý
    static TransportManagement transport = new TransportManagement();
    static LibraryManagement library = new LibraryManagement();
    static CafeManagement cafe = new CafeManagement();

    // Biến biểu mẫu đăng ký
    static String name;
    static String password;
    static String dateOfBirth;
    static String regNumber;
    static String studentCnic;
    static String gmail;
    static String degree;
    static String gender;
    static String contactNumber;
    static String fatherCnic;
    static String fatherName;

    // Biến form đăng nhập
    static String stdName;

    // Phương thức đăng ký..
    public static void registration() {
        System.out.println("\n*******************************************************\n");
        System.out.println("\tRegistration Form\n");
        System.out.println("*******************************************************\n");
        input.nextLine();

        // Xác thực tên sinh viên
        while (true) {
            System.out.println("Enter your name: ");
            name = input.nextLine().toUpperCase();

            if (name.length() >= 3 && name.matches("^[a-zA-Z ]*$")) {
                break;
            } else {
                System.out.println("\tTry Again! Invalid data");
            }
        }
        // Xác thực mật khẩu
        while (true) {
            System.out.println("Enter your password(at least 8 characters)");
            password = input.nextLine();

            if (password.length() >= 8) {
                break;
            } else {
                System.out.println("\tTry Again! Invalid data");
            }
        }
        // Xác thực bằng cấp
        while (true) {
            System.out.println("Enter your Degree(License) :");
            degree = input.nextLine().toUpperCase();
            if (degree.toUpperCase().compareTo("License") == 0) {
                break;
            } else {
                System.out.println("\tTry again! Enter valid degree name!");
            }
        }

        // Xác thực giới tính
        while (true) {
            System.out.println("Enter your Gender(Male or Female): ");
            gender = input.nextLine().toUpperCase();

            if (gender.compareTo("MALE") == 0 || gender.compareTo("FEMALE") == 0) {
                break;
            } else {
                System.out.println("\nTry Again!Enter valid data");
            }
        }

        // Xác thực ngày sinh
        while (true) {
            System.out.println("Enter Your Date Of Birth(DD-MM-YYYY): ");
            dateOfBirth = input.nextLine();

            if (Pattern.matches("^(0[1-9]|[12][0-9]|3[01])[-/.](0[1-9]|1[012])[-/.](19|20)\\\\d\\\\d$", dateOfBirth)) {
                break;
            } else {
                System.out.println("\nTry Again! Enter date in valid");
            }
        }
        // Xác thực CNIC của sinh viên
        while (true) {
            System.out.println("Enter your CNIC(Computerized National Identity Card): ");
            studentCnic = input.nextLine();
            if (Pattern.matches("\\d{5}-\\d{7}-\\d{1}", studentCnic)) {
                break;
            } else {
                System.out.println("\tTry Again! Enter valid CNIC!");
            }
        }
        // Gmail
        while (true) {
            System.out.println("Enter your gmail : ");
            gmail = input.nextLine();
            if (Pattern.matches("^[\\w.+\\-]+@gmail\\.com$", gmail)) {
                break;
            } else {
                System.out.println("\tTry Again!Enter valid email ID.");
            }
        }

        // liên hệ xác thực số
        while (true) {
            System.out.println("Enter your contact Number(03xx-xxxxxxx): ");
            contactNumber = input.nextLine();
            if (Pattern.matches("03\\d{2}-\\d{7}", contactNumber) && contactNumber.length() == 12) {
                break;
            } else {
                System.out.println("\tTry Again! Enter valid contact Number!");
            }
        }

        // Tên Cha.
        while (true) {
            System.out.print("Enter Your Father's Name : ");
            fatherName = input.nextLine().toUpperCase();
            if (fatherName.length() >= 3 && fatherName.matches("^[a-zA-Z ]*$")) {
                break;
            } else {
                System.out.println("\tTry Again! Enter valid data.");
            }
        }

        // Xác nhận CNIC cha
        while (true) {
            System.out.print("Enter Your Father CNIC number : ");
            fatherCnic = input.nextLine();
            if (Pattern.matches("\\d{5}-\\d{7}-\\d{1}", fatherCnic)) {
                break;
            } else {
                System.out.println("\tTry Again! Enter valid CNIC number.");
            }
        }

        // Tạo số đăng ký ngẫu nhiên
        String[] system = {"FA", "SP"};
        int r = ((int) (Math.random() * 150));
        String regNumber = system[(int) (Math.random() * 2)] + ((int) (Math.random() * 4) + 18) + "-BCS-" + (r < 10 ? ("00") : (r < 100) ? ("0") : ("")) + r;
        while (true) {
            try {
                File file = new File("roll_numbers/ids.txt");
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (line.compareTo(regNumber) == 0) {
                        continue;
                    }
                }
                sc.close();
                FileWriter fileWriter = new FileWriter("roll_numbers/ids.txt");
                fileWriter.write(regNumber + "\n");
                fileWriter.close();
                break;
            } catch (Exception e) {
                System.out.print(e);
            }
        }
        // Lưu dữ liệu vào file
        try {

            File file = new File("roll_numbers/ids.txt");
            boolean value = file.createNewFile();
            if (value) {
                try {
                    FileWriter fileWriter = new FileWriter("student_data/" + regNumber + ".txt");
                    fileWriter.write("Name: " + name);
                    fileWriter.write("\nPassword: " + password);
                    fileWriter.write("\nRegNumber: " + regNumber);
                    fileWriter.write("\nDegree: " + degree);
                    fileWriter.write("\nGenger: " + gender);
                    fileWriter.write("\nDateOfBirth: " + dateOfBirth);
                    fileWriter.write("\nGmail: " + gmail);
                    fileWriter.write("\nStudentCnic: " + studentCnic);
                    fileWriter.write("\nContactNumber: " + contactNumber);
                    fileWriter.write("\nFatherName: " + fatherName);
                    fileWriter.write("\nFatherCnic: " + fatherCnic);
                    fileWriter.close();
                    System.out.println("\n*******************************************************\n");
                    System.out.println("Hi! " + name + ", Your Registration number is " + regNumber);
                    System.out.println("\tRegistered Successfully!\n");
                    System.out.println("*******************************************************\n");

                } catch (Exception e) {
                    System.out.println("Errored occured!");
                }
            } else {
                System.out.println(regNumber + "already exists!");
            }
        } catch (Exception e) {
            System.out.println("Errored occured!");
        }

        // Lưu trữ số dư trong mô-đun cafe sinh viên khi đăng ký lần đầu
        try {
            File file = new File("cafe/balance" + regNumber + ".txt");
            file.createNewFile();
            FileWriter fileW = new FileWriter("cafe/balance" + regNumber + ".txt");
            fileW.write(4000);
            fileW.close();
        } catch (Exception e) {
            System.out.println("Errored occured!");
        }
    }
    // login
    public static void login() {
        System.out.println("\n*******************************************************\n");
        System.out.println("\tLOGIN FORM\n");
        System.out.println("*******************************************************");
        input.nextLine();
        // Xác thực mật khẩu
        boolean userExisted = false;
        while (true) {
            // Kiểm tra xác thực RegNumber..
            System.out.println("Enter RegNumber (XXXX-XXX-XXX): ");
            regNumber = input.nextLine().toUpperCase();
            if (Pattern.matches("SP\\d{2}-\\w{3}-\\d{3}-FA\\d{2}-\\w{3}-\\d{3}", regNumber)) {
                break;
            } else {
                System.out.println("Invalid ID! Try Again...\n");
            }
        }
        System.out.println("Enter your Password: ");
        password = input.nextLine();
        if (regNumber.compareTo("ADMIN") == 0 && regNumber.compareTo("admin") == 0) {
            Admin.admin();

        } else {
            File file = new File("students_data" + regNumber + ".txt");

            if (file.exists()) {
                try {
                    Scanner sc = new Scanner(System.in);
                    while (sc.hasNextLine()) {
                        String line = sc.nextLine();
                        String key = line.substring(0, line.indexOf(":"));
                        String value = line.substring(line.indexOf(":") + 1, line.length());
                        if (key.compareTo("password") == 0 && value.compareTo(password) == 0) {
                            userExisted = true;
                            break;
                        }
                        if (key.compareTo("name") == 0) {
                            stdName = value;
                        }
                    }
                    sc.close();
                } catch (Exception e) {
                    System.out.print(e);
                }
                if (userExisted) {
                    System.out.println("\n*******************************************************\n");
                    System.out.println("\tWelcome " + stdName);
                    System.out.println("\n*********************************************************");
                    management(regNumber);

                } else {
                    System.out.println("\nInvalid Credientials! Try Again!");
                }
            } else {
                System.out.println("\nThat user didn't exists! ");

            }
        }
    }
    public static void main(String[] args) {
        Choices();
    }

    public static void management(String regNumber) {
        System.out.println("\n1. Manage Courses");
        System.out.println("2. Transport Management");
        System.out.println("3. Library Management");
        System.out.println("4. Cafe Management");
        System.out.println("5. Previous Menu");
        System.out.println();

        while (true) {
            try {
                System.out.print("Enter your choice: ");
                int choice = input.nextInt();
                boolean valid = false;

                switch (choice) {
                    case 1: {
                        Courses.manage(regNumber);
                        valid = true;
                        break;
                    }
                    case 2: {
                        transport.manage(regNumber);
                        valid = true;
                        break;
                    }
                    case 3: {
                        library.manage(regNumber);
                        valid = true;
                        break;
                    }
                    case 4: {
                        cafe.manage(regNumber);
                        valid = true;
                        break;
                    }
                    case 5: {
                        Choices();
                        valid = true;
                        break;
                    }
                }
                if (valid) {
                    break;
                } else {
                    System.out.println("Invalid Input! Try Again!!");
                    input.nextLine();
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Try Again..");
                input.nextLine();
            }
        }

    }
    public static void Choices() {
        System.out.println("*******************************************************\n");
        System.out.println("\tUniversity Management System\n");
        System.out.println("*******************************************************");
        boolean flag = true;
        while (flag) {
            try {
                System.out.println("\n1. Registration");
                System.out.println("2. Login ");
                System.out.println("3. Exit");
                System.out.print("Enter choice : ");
                int c = input.nextInt();
                switch (c) {
                    case 1: {
                        registration();
                        flag = false;
                        break;
                    }
                    case 2: {
                        login();
                        flag = false;
                        break;
                    }
                    case 3: {
                        System.out.println("*****Thanks*****");
                        System.exit(0);
                        break;
                    }
                    default: {
                        System.out.println("Invalid iput... Try again!!");
                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input.. Try again");
                input.nextLine();
            }
        }

    }

}
