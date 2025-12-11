package smartjournaling;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting program..."); // test line
        JournalPage jp = new JournalPage();
        jp.openJournalPage();
    }
}

package smartjournaling;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class JournalManager {

    /**
     * @param args the command line arguments
     */
//folder path and journal date format
private static final String JOURNAL_FILE = "./journals/";
private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//ensure folder always exists before journaling
public JournalManager(){
    File folder=new File(JOURNAL_FILE);
    if (!folder.exists()){
        folder.mkdir();}
}
//store and get list of date
public ArrayList<LocalDate> getJournalDates (LocalDate earliestDate){
    ArrayList<LocalDate> date= new ArrayList<>();
    LocalDate today=LocalDate.now();
    LocalDate pointer=earliestDate;
    while (!pointer.isAfter(today)){
    date.add(pointer);
    pointer=pointer.plusDays(1);
}
return date;
}
//check journal file for a date exist
public boolean journalExists(LocalDate date){
       File file=new File(JOURNAL_FILE+date.format(FORMAT)+".txt");
return file.exists();
   }

//write journal text into file(create or overwrite)
public void saveJournal(LocalDate date, String content){
      try 
      {FileWriter fw = new FileWriter (JOURNAL_FILE+date.format(FORMAT)+".txt");
      fw.write(content);
      fw.close();
      }catch (Exception e){
             System.out.println("Error saving journal"+e.getMessage());
         }}
      //read journal
 public String readJournal(LocalDate date){
     File file= new File (JOURNAL_FILE+date.format(FORMAT)+".txt");
     if(!file.exists())return null;
     
     StringBuilder sb=new StringBuilder();
     try
     (BufferedReader br = new BufferedReader (new FileReader(file))){
             String line;
             while((line=br.readLine())!=null){
                 sb.append(line).append("\n");
         }
             return sb.toString();
     }catch (Exception e){
         System.out.println("Error reading journal"+e.getMessage());
         return null;}
     }   
}

package smartjournaling;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
      
public class JournalPage {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private JournalManager manager;
    private Scanner sc;
    
public JournalPage(){
    manager = new JournalManager();
    sc = new Scanner(System.in);
}

public void openJournalPage(){
    LocalDate earliestdate = LocalDate.now().minusDays(7);
    
    while (true){
    System.out.println("Journal Date List:");
    ArrayList<LocalDate>dates=manager.getJournalDates(earliestdate);
    LocalDate today = LocalDate.now();
    
    for (int i=0;i<dates.size();i++){
        LocalDate d= dates.get(i);
        if (d.equals(today))
            System.out.println((i+1)+". "+d.format(FORMAT)+"(Today)");
        else{
            System.out.println((i+1)+". "+d.format(FORMAT));
    }
    } 
        System.out.println("Select a date to view the journal:");
        int choice=sc.nextInt();
        sc.nextLine();
        
        if (choice<1||choice>dates.size()){
            System.out.println("Invalid input!");
            continue;
        }
            LocalDate chosendate= dates.get(choice-1);
            handleDate(chosendate);
        }
    }

private void handleDate(LocalDate date){
    LocalDate today=LocalDate.now();
    boolean exists = manager.journalExists(date);
    System.out.println();
    
    if (!date.equals(today)){
        if(!exists){
        System.out.println("No journal found for this date!");
    return;}

    System.out.println("Journal for "+date.format(FORMAT));
    System.out.println(manager.readJournal(date));
    System.out.println("Enter to go back to dates");
    sc.nextLine();
    return;
    }
    
        if (!exists){
            System.out.println("No journal found for today! ");
            System.out.println("1. Create journal");
            System.out.println("2. Press enter to go back to dates");
            int choice = sc.nextInt();
            sc.nextLine();
        if (choice==1)
            createTodayJournal(date);
        else if (choice ==2)return;
        else 
        System.out.println("Invalid input");}
        
        System.out.println("The journal is already existed for today!");
        System.out.println("1. View journal");
        System.out.println("2. Edit journal");
        System.out.println("3. Press enter to go back to dates");
        int choice2= sc.nextInt();
        sc.nextLine();
        
       switch(choice2){
           case 1 : viewToday(today); break;
           case 2 : editToday(today);  break;
       }
}
            
       private void createTodayJournal(LocalDate date){
           System.out.println("Enter your journal for today:");
           String content= sc.nextLine();
           manager.saveJournal(date, content);
           System.out.println("Your journal is saved successfully!");
       }
        
       private void viewToday(LocalDate date){
           System.out.println("View your journal for "+(date.format(FORMAT)));
           manager.readJournal(date);
           System.out.println(manager.readJournal(date));
           System.out.println("Press enter to go back to dates");
           sc.nextLine();}
       
       private void editToday(LocalDate date){
           System.out.println("Edit your journal for "+(date.format(FORMAT)));
           String content=sc.nextLine();
           manager.saveJournal(date, content);
                 }
}
        
  
      

    
