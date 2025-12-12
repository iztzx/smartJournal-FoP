/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import java.util.Map;
public class SentimentAnalyzer {
    /** 
     * Analyze the journalText and return "POSITIVE", "NEGATIVE", or "UNKNOWN".
     */
    public String analyzeMood(String JournalText) {
        //creat api object to allow me to call POST API   
         API api = new API();
   
        // Load environment variables from .env file (custom loader),load Hugging Face token from .env file
        Map<String, String> env = EnvLoader.loadEnv(".env");
        
        // POST request: Perform sentiment analysis using HuggingFace model ---
            
        String postUrl = "https://router.huggingface.co/hf-inference/models/tabularisai/multilingual-sentiment-analysis";


        // Safely get bearer token
        String bearerToken = env.get("BEARER_TOKEN");
                    
        if (bearerToken == null || bearerToken.isEmpty()) {
            System.err.println("Error: BEARER_TOKEN is not set in the environment.");
            return"Unknown Error fo Bearer Token";
        }
        
        // check empty input
        if (JournalText == null || JournalText.trim().isEmpty()) {
            return "UNKNOWN INPUT";
            }

        // Format JSON body
        String jsonBody = "{\"inputs\": \"" + JournalText + "\"}";

                try {
                     
                    // Call POST
                    String postResponse = api.post(postUrl, bearerToken, jsonBody);
                    System.out.println("\nSentiment Analysis Response:\n" + postResponse);
                    String MoodExtract = Mood(postResponse);
                    return MoodExtract;

        }       catch (Exception e) {
                e.printStackTrace();
                return "UNKNOWN ERROR";
        }
      
        
    }
    
  private String Mood(String postResponse){
      //find index of positive and negative
      int VeryPositiveIndex=postResponse.toUpperCase().indexOf("\"VERY POSITIVE\"");
      int PositiveIndex=postResponse.toUpperCase().indexOf("\"POSITIVE\"");
      int VeryNegativeIndex=postResponse.toUpperCase().indexOf("\"VERY NEGATIVE\"");
      int NegativeIndex=postResponse.toUpperCase().indexOf("\"NEGATIVE\"");
      int NeutralIndex=postResponse.toUpperCase().indexOf("\"NEUTRAL\"");
      
      //Identify mood
      return ((VeryPositiveIndex<PositiveIndex)&&(VeryPositiveIndex<VeryNegativeIndex)&&(VeryPositiveIndex<NegativeIndex)&&(VeryPositiveIndex<NeutralIndex))?"VERY POSITIVE":
              ((PositiveIndex<VeryPositiveIndex)&&(PositiveIndex<VeryNegativeIndex)&&(PositiveIndex<NegativeIndex)&&(PositiveIndex<NeutralIndex))?"POSITIVE":
              ((VeryNegativeIndex<PositiveIndex)&&(VeryNegativeIndex<NegativeIndex)&&(VeryNegativeIndex<VeryPositiveIndex)&&(VeryNegativeIndex<NeutralIndex))?"VERY NEGATIVE":
              ((NegativeIndex<PositiveIndex)&&(NegativeIndex<VeryNegativeIndex)&&(NegativeIndex<VeryPositiveIndex)&&(NegativeIndex<NeutralIndex))?"NEGATIVE":"NEUTRAL";
      
}}
