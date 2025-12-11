/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smartjournaling;

/**
 *
 * @author Jieshin
 */
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
        

       