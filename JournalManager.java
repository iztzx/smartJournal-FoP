/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package smartjournaling;

/**
 *
 * @author Jieshin
 */
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
        
  
      

    