
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.URL;
import java.util.Scanner;
import java.io.File;  
import java.io.IOException; 

public class Starter {

	private static Replacement_Policy FIFO;;
	private static Replacement_Policy Optimal;
	private static Replacement_Policy LRU;
	
	public static int Number_of_Pages;
	public static int Number_of_Frames;
	private static int Number_of_PageAccessRequests;
	private static int[] PageAccessRequests;
	
	private static String[] PrintOut1; 
	private static String[] PrintOut2;
	private static String[] PrintOut3; 
	
	private static String INPUT_FILE_NAME;
	private static String OUTPUT_FILE_NAME;
	
	//Bring in File
	//First Number: pages
	//Second Number: frames
	//Third Number: page access requests
	
	public Starter() {
	}
	
	 public static void main(String[] args) throws FileNotFoundException 
	 {
		 
			Number_of_Pages = 0;
			Number_of_Frames = 0;
			Number_of_PageAccessRequests = 0;
			
			PrintOut1 = new String[100];
			PrintOut2 = new String[100];
			PrintOut3 = new String[100];
			
			INPUT_FILE_NAME = new String();
			OUTPUT_FILE_NAME = new String();
			
			INPUT_FILE_NAME = "input4.txt";
			OUTPUT_FILE_NAME = "output4.txt";
			
			FIFO = new Replacement_Policy(1);
			Optimal = new Replacement_Policy(2);
			LRU = new Replacement_Policy(3);
			
			PageAccessRequests = new int[100];
			
			for(int i = 0; i < 100;i++)
			{
				PageAccessRequests[i] = 999;
				PrintOut1[i] = null;
				PrintOut2[i] = null;
				PrintOut3[i] = null;
			}
	
		 inPUT();  
		 FIFO.setNumber_Of_Frames(Number_of_Frames);
		 FIFO.set_Number_Requests(Number_of_PageAccessRequests);
		 FIFO.setPageAccessRequests(PageAccessRequests);
		 FIFO.run();
	
	        for (int i = 0; i < FIFO.getPrintTracker();i++)
	        {
	        	PrintOut1[i] = FIFO.printResult(i);
	        }
		 
		 Optimal.setNumber_Of_Frames(Number_of_Frames);
		 Optimal.set_Number_Requests(Number_of_PageAccessRequests);
		 Optimal.setPageAccessRequests(PageAccessRequests);
		 Optimal.run();
		 
	        for (int i = 0; i < Optimal.getPrintTracker();i++)
	        {
	        	PrintOut2[i] = Optimal.printResult(i);
	        }
		
		 LRU.setNumber_Of_Frames(Number_of_Frames);
		 LRU.set_Number_Requests(Number_of_PageAccessRequests);
		 LRU.setPageAccessRequests(PageAccessRequests);
		 LRU.run();
		 
	        for (int i = 0; i < LRU.getPrintTracker();i++)
	        {
	        	PrintOut3[i] = LRU.printResult(i);
	        }
		 
		 outPUT(FIFO, Optimal, LRU);
	 }
	 
	 public static void inPUT() throws FileNotFoundException
	 {


		 URL path = Starter.class.getResource(INPUT_FILE_NAME);

		   File file =
				      new File(path.getFile());
				    Scanner sc = new Scanner(file);
				  

				    	Number_of_Pages = sc.nextInt();
				    	Number_of_Frames = sc.nextInt();
				    	Number_of_PageAccessRequests = sc.nextInt();
				    	
				    
				    System.out.println("Pages: " + Number_of_Pages);
				    System.out.println("Frames: " + Number_of_Frames);
				    System.out.println("PageAccessRequests: " + Number_of_PageAccessRequests);
				    
				    int i = 0;
				    
				    while (i < Number_of_PageAccessRequests - 1)
				    {
				    	while (sc.hasNextInt())
				    	{
				    		PageAccessRequests[i] = sc.nextInt();
				    		i++;
				    	}
				    	
				    }

				    

	 }
	 
	 public static void outPUT(Replacement_Policy FIFO, Replacement_Policy Optimal, Replacement_Policy LRU)
	 {
		    try {
		        FileWriter myWriter = new FileWriter(OUTPUT_FILE_NAME);
		        for (int i = 0; i < FIFO.getPrintTracker();i++)
		        {
		        	myWriter.write(PrintOut1[i] + "\n");	
		        }
		        myWriter.write("\n");	
		        
		        for (int i = 0; i < Optimal.getPrintTracker();i++)
		        {
		        	myWriter.write(PrintOut2[i] + "\n");	
		        }
		        myWriter.write("\n");	
		        
		        for (int i = 0; i < LRU.getPrintTracker();i++)
		        {
		        	myWriter.write(PrintOut3[i] + "\n");	
		        }
		        myWriter.close();
		        System.out.println("File successfully written to.");
		      } catch (IOException e) {
		        System.out.println("An error occurred.");
		        e.printStackTrace();
		      }
		    }
	 
	
	 public static int getNumber_of_Frames()
	 {
		 return Number_of_Frames;
	 }
	 
}
