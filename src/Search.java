import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Search {
	
	public static void main (String args[]) throws IOException
	{
		 
		 //System.out.println("Enter the complete path for the product json file\n");
		 
	     String dataFilePath = "../input/data.json";
	    // System.out.println("Enter the complete path for the query json file");
	     String queryFilePath = "../input/short_query.json";
	     PreProcess preprocess = new PreProcess();
	     
	     HashMap<String, ArrayList<Integer>> queryIndex = new HashMap<String, ArrayList<Integer>>();
	     HashMap<String, ArrayList<Integer>> artistIndex = new HashMap<String, ArrayList<Integer>>();
	     ArrayList<Query> queryList = preprocess.process(dataFilePath, queryFilePath, queryIndex, artistIndex);
	     
	    
	    
	     int j = 40;
	     System.out.println("Sample 40 records for testing");
	     while(j>0)
	     {
	    	 Query tmp = queryList.get(j);
	    	 System.out.println("Artist:" + tmp.getArtist()+ "\t Query:" +tmp.getQuery() +"\t Product:"  + tmp.getProductName());
	    	 j--;
	     }
	     
	     
	     
	     System.out.println("*****************MENU ************************");	
	     System.out.println("Enter -- query <mention the query> --");	
	     System.out.println("Enter -- artist <mention the artist name here> --");	
	     System.out.println("Type exit for exiting the program \n\n\n");
	     
	     
	     Scanner s = new Scanner(System.in);
	     System.out.print(">>> ");
	     String input = s.nextLine();
	     while (!input.equalsIgnoreCase("exit"))
	     {
	    	 if(input.contains("artist"))
	    	 {
	    		 // the query is for the artist
	    		 String query = input.split("artist")[1].trim();
	    		 ArrayList<Integer> indexes = artistIndex.get(query);
	    		 if(indexes == null)
	    		 {
	    			 System.out.println("No Queries resulted in this artist. Sorry!"); 
	    		 }
	    		 else
	    		{
	    			 System.out.println("Search Queries are ");
		    		 for(int i : indexes)
		    		 {
		    			 System.out.println(queryList.get(i).getQuery()+"\n");	 
		    		 }
	    		}
	    	 }
	    	 else if (input.contains("query"))
	    	 {
	    		 String query = input.split("query")[1].trim();
	    		 ArrayList<Integer> indexes = queryIndex.get(query);
	    		 if(indexes == null)
	    		 {
	    			 System.out.println("No Products found for this Query!!\n"); 
	    		 }
	    		 else
	    		{
		    		 for(int i : indexes)
		    		 {
		    			 System.out.println("Product Name: " + queryList.get(i).getProductName()+
		    					 			" \t Product Id"+queryList.get(i).getProductId()+
		    					 			" \tGenre: " + queryList.get(i).getGenre());	 
		    		 }
	    		}
	    	 }
	    	 System.out.print(">>> ");
	    	 input =  s.nextLine();
	     }
	     
	     s.close();
		 
	     
	}

}
