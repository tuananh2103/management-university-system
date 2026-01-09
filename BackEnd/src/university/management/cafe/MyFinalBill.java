package university.management.cafe;

import java.io.File;
import java.io.FileWriter;

public class MyFinalBill {
    public static void appendFinalBill(String regNumber, int amount) {
        try{
            File file = new File("cafe/CafeBills/FinalBills/"+regNumber+".txt");
            file.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(file,true);
            fw.write("Final Bill Amount: "+amount+"\n");
            fw.close();
        }
        catch(Exception e){
            System.out.println("Error writing final bill: "+e.getMessage());
        }
    }
}
