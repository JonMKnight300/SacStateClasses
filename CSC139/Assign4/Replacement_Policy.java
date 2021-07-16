

public class Replacement_Policy {

	private int type;
	private int Number_Of_Frames;
	private int[] PageAccessRequests;
	private static int Number_of_PageAccessRequests;
	private static String[] PrintOut; 
	private int[] frame_Memory;
	private int[] timeStamp;
	private int[] nextUseTime;
	private int[] likeUseArray;
	
	private int currentFrame;
	private int breakNumber;
	private int previousFrame;
	private int tempFrameNum;
	private int pageFault;
	private int greatestUseTime;
	private int likeUseTime;
	private int leastTimestamp;
	private int printTracker;
	
	private boolean isFound;
	private boolean frameChange;
	private boolean nextUseIsFound;
	
	public Replacement_Policy(int the_type){
		type = the_type;
		// 1, FIFO
		// 2, Optimal
		// 3, LRU
		
		currentFrame = 0;
		breakNumber = 0;
		previousFrame = 0;
		tempFrameNum = 0;
		pageFault = 0;
		leastTimestamp = 0;
		
		greatestUseTime = 0;
		likeUseTime = 0;
		printTracker = 0;
		
		isFound = false;
		frameChange = false;
		nextUseIsFound = false;
		
		PageAccessRequests = new int[100];
		PrintOut = new String[100];
		frame_Memory = new int[100];
		nextUseTime = new int[100];
		likeUseArray = new int[100];
		timeStamp = new int[100];

	
		
		for(int i = 0; i < 100;i++)
		{
			PageAccessRequests[i] = 999;
			PrintOut[i] = null;
			frame_Memory[i] = 999;
			nextUseTime[i] = 999;
			likeUseArray[i] = 999;
			timeStamp[i] = 999;
		}
	}
	
	public void setNumber_Of_Frames(int frame)
	{
		Number_Of_Frames = frame;
		System.out.println("Frames in Replacement: " + Number_Of_Frames);
	}
	
	public void set_Number_Requests(int request)
	{
		Number_of_PageAccessRequests = request;
		 System.out.println("Requests in Replacement: " + Number_of_PageAccessRequests);
	}
	
	public void setPageAccessRequests(int[] pageRequests)
	{
		for(int i = 0;i < Number_of_PageAccessRequests;i++)
		{
			PageAccessRequests[i] = pageRequests[i];
		}
		
		for(int i = 0;i < Number_of_PageAccessRequests;i++)
		{
	    	System.out.println("Array: " + PageAccessRequests[i]);
	    }
	}
	
	public void run() 
	{
		System.out.println("\n");
		if (type == 1)
		{
			PrintOut[printTracker] = "FIFO";
			printTracker++;
			System.out.println("FIFO");
			System.out.println("\n");
		}
		else if (type == 2)
		{
			PrintOut[printTracker] = "Optimal";
			printTracker++;
			System.out.println("Optimal");
			System.out.println("\n");
		}
		else if (type == 3)
		{
			PrintOut[printTracker] = "LRU";
			printTracker++;
			System.out.println("LRU");
			System.out.println("\n");
		}
		
	
		for(int i = 0; i < Number_of_PageAccessRequests;i++)
		{
			
			
			
			if (currentFrame == Number_Of_Frames)
			{
			//	System.out.println("Current Frame: " + currentFrame + " Number of Frames: " + Number_Of_Frames);
				int l = 0;
				
				while (l < currentFrame)
				{
					if (frame_Memory[l] == PageAccessRequests[i])
					{
						isFound = true;
						
					}
					
					//System.out.println("In Cur: Frame Memory: " + frame_Memory[l] + " and PageAccessRequest " + PageAccessRequests[i]);
					
					if ((frame_Memory[l] != PageAccessRequests[i]) && isFound == false)
					{
					frameChange = true;
					//System.out.println("frameChange is true ");
					}
					else 
						{
						frameChange = false;
					//	System.out.println("frameChange is false ");
						}
					
					l++;
				}
				
				if (frameChange == true)
				{
				//	System.out.println("frameChange break at PageAccessRequest " + PageAccessRequests[i] + " - i is " + i);
					breakNumber = i;
					break;
				}

			}
			
			int j = 0;
			
			
			while (frame_Memory[j] != 999)
			{
				if (frame_Memory[j] == PageAccessRequests[i])
				{
					isFound = true;
					System.out.println("Page " + PageAccessRequests[i] + " already in frame " + j);
					PrintOut[printTracker] = "Page " + PageAccessRequests[i] + " already in frame " + j;
					printTracker++;
					if (type == 2)
					{
						nextUseTime(i, j);
						int test = 0;
						while (frame_Memory[test] != 999)
						{
							if (test != j && nextUseTime[test] != 666)
							{
								nextUseTime[test]--;
							}
							test++;
						}
					//	System.out.println("Next Use AFTER SET: " + nextUseTime[j]);
					}
					
					if (type == 3)
					{
					timeStamp[j] = i;
					}
				//	System.out.println("Timestamp is " + timeStamp[j]);
				}
				j++;
			}	
			if (isFound == false)
			{

				frame_Memory[currentFrame] = PageAccessRequests[i];
				System.out.println("Page " + PageAccessRequests[i] + " loaded in frame " + j);
				PrintOut[printTracker] = "Page " + PageAccessRequests[i] + " already in frame " + j;
				printTracker++;
				if (type == 2)
				{
					nextUseTime(i, j);
					int test = 0;
					while (frame_Memory[test] != 999)
					{
						if (test != j && nextUseTime[test] != 666)
						{
							nextUseTime[test]--;
						}
						test++;
					}
				//	System.out.println("Next Use AFTER SET: " + nextUseTime[j]);
				}

				if (type == 3)
				{
				timeStamp[currentFrame] = i;
				}
			//	System.out.println("Timestamp is " + timeStamp[currentFrame]);
				pageFault++;
				currentFrame++;

			}
			
		
			

			
			isFound = false;
		}
		
		for(int i = 0;i < Number_Of_Frames;i++)
		{
	    	System.out.println("Frame Array: " + frame_Memory[i]);
	    	//System.out.println("Timestamp for each: " + timeStamp[i]);
	    	if (type == 2)
	    	System.out.println("Next Use for each: " + nextUseTime[i]);
	    }
		
		
		
		if (type == 1)
		{
			FIFO();
		}
		else if (type == 2)
		{
			Optimal();
		}
		else if (type == 3)
		{
			LRU();
			
		}
		
	}
	
	public void nextUseTime(int i, int j)
	{
		int lookAhead = i+1;
		int lookAhead2 = 0;
		while (PageAccessRequests[lookAhead] != 999 && nextUseIsFound == false)
		{
		if (PageAccessRequests[i] != PageAccessRequests[lookAhead])
		{
			
			lookAhead2++;
			
		}
		else if(PageAccessRequests[i] == PageAccessRequests[lookAhead])
		{
			lookAhead2++;
			nextUseIsFound = true;
		}
		lookAhead++;
		
		}
		
		if (nextUseIsFound == true)
		{
			nextUseTime[j] = lookAhead2;
		}
		else 
		{
			nextUseTime[j] = 666;
		}
		
		//System.out.println("nextUseTime is " + nextUseTime[j] + " for Page " + PageAccessRequests[i]);
		
		nextUseIsFound = false;
	}
	
	public void FIFO()
	{
		currentFrame = 0;
		
		while(breakNumber < Number_of_PageAccessRequests)
		{
	
			for(int i = 0; i < Number_Of_Frames;i++)
			{
				if (frame_Memory[i] == PageAccessRequests[breakNumber])
				{
					tempFrameNum = i;
					isFound = true;
				}
				
			}
			

			if (isFound == false)
			{
				previousFrame = frame_Memory[currentFrame];
				frame_Memory[currentFrame] = PageAccessRequests[breakNumber];
				System.out.println("Page " + previousFrame + " unloaded from Frame " + currentFrame +  ", Page " +  PageAccessRequests[breakNumber] + " loaded into Frame " + currentFrame);
				PrintOut[printTracker] = "Page " + previousFrame + " unloaded from Frame " + currentFrame +  ", Page " +  PageAccessRequests[breakNumber] + " loaded into Frame " + currentFrame;
				printTracker++;
				pageFault++;
				currentFrame++;
				
				if (currentFrame > Number_Of_Frames-1)
				{
					currentFrame = 0;
				}
			}

			else if (isFound == true)
			{
					System.out.println("Page " + PageAccessRequests[breakNumber] + " already in frame " + tempFrameNum);
					PrintOut[printTracker] = "Page " + PageAccessRequests[breakNumber] + " already in frame " + tempFrameNum;
					printTracker++;
			}
			
			isFound = false;
			breakNumber++;
			
		}
		
		System.out.println(pageFault + " page faults");
		PrintOut[printTracker] = pageFault + " page faults";
		printTracker++;
	}
	
	public void Optimal()
	{
		
		while(breakNumber < Number_of_PageAccessRequests)
		{
			

	
			for(int i = 0; i < Number_Of_Frames;i++)
			{
				if (frame_Memory[i] == PageAccessRequests[breakNumber])
				{
					tempFrameNum = i;
					isFound = true;
				}
				
			}
			

			if (isFound == false)
			{
				int j = 0;
		
				greatestUseTime = Number_Of_Frames-1;
				
				while (j < Number_Of_Frames)
				{
					
					if ((nextUseTime[j] > nextUseTime[greatestUseTime]) && nextUseTime[j] != 999)
					{
						greatestUseTime = j; 
						
					}
					else if (nextUseTime[j] == nextUseTime[greatestUseTime] && j != greatestUseTime)
					{
						
						likeUseArray[likeUseTime] = j;
						likeUseArray[likeUseTime+1] = greatestUseTime;
						likeUseTime++;
						likeUseTime++;
						
				//		System.out.println("nextUseTime[j]: " + nextUseTime[j]);
				//		System.out.println("nextUseTime[greatestUseTime]: " + nextUseTime[greatestUseTime]);
				//		System.out.println("frame_Memory[j]: " + frame_Memory[j]);
				//		System.out.println("frame_Memory[greatestUseTime]: " + frame_Memory[greatestUseTime]);
					}
					j++;
				}
				
				int l = 0;
				int innerUseTimeCheck = 0;
				if (likeUseTime > 0)
				{
					while (l < Number_Of_Frames)
					{
						for(int k = 0; k < Number_Of_Frames; k++)
						{
						if ((likeUseArray[l] < likeUseArray[k]))
						{
							innerUseTimeCheck = likeUseArray[l];
						}
						}
						
						if (innerUseTimeCheck < greatestUseTime)
						{
							greatestUseTime = innerUseTimeCheck;
						//	System.out.println("greatestUseTime: " + greatestUseTime);
						//	System.out.println("innerUseTimeCheck: " + innerUseTimeCheck);
						}
						
						l++;
					}
					
					for(int k = 0; k < Number_Of_Frames; k++)
					{
					//	System.out.println("InLikeUseArray: " + likeUseArray[k]);
					}
					
				}
					/*
				if (frame_Memory[likeUseArray[1]] == 666 && frame_Memory[likeUseArray[2]] == 666)
				{
					greatestUseTime = likeUseArray[0];
				}
			
				for(int k = 0; k < Number_Of_Frames; k++)
				{
					
					System.out.println("likeUseTime inside 666 loop is " + likeUseTime);
					if((frame_Memory[k] == 666) && likeUseTime == 0)
					{
						
						greatestUseTime = k;
					}
				}*/
				
				previousFrame = frame_Memory[greatestUseTime];
				frame_Memory[greatestUseTime] = PageAccessRequests[breakNumber];
				System.out.println("Page " + previousFrame + " unloaded from Frame " + greatestUseTime +  ", Page " +  PageAccessRequests[breakNumber] + " loaded into Frame " + greatestUseTime);
				PrintOut[printTracker] = "Page " + previousFrame + " unloaded from Frame " + greatestUseTime +  ", Page " +  PageAccessRequests[breakNumber] + " loaded into Frame " + greatestUseTime;
				printTracker++;
				pageFault++;
				likeUseTime = 0;
				nextUseTime(breakNumber, greatestUseTime);
				int test = 0;
				while (frame_Memory[test] != 999)
				{
					if (test != greatestUseTime && nextUseTime[test] != 666)
					{
						nextUseTime[test]--;
					}
					test++;
				}
				
				
				
			}

			else if (isFound == true)
			{
				System.out.println("Page " + PageAccessRequests[breakNumber] + " already in frame " + tempFrameNum);
				PrintOut[printTracker] = "Page " + PageAccessRequests[breakNumber] + " already in frame " + tempFrameNum;
				printTracker++;
				timeStamp[tempFrameNum] = breakNumber;
				
				nextUseTime(breakNumber, tempFrameNum);
				int test = 0;
				while (frame_Memory[test] != 999)
				{
					if (test != tempFrameNum && nextUseTime[test] != 666)
					{
						nextUseTime[test]--;
					}
					test++;
				}

			}
			
			isFound = false;
			breakNumber++;
			
			
		}
		
		System.out.println(pageFault + " page faults");
		PrintOut[printTracker] = pageFault + " page faults";
		printTracker++;
	}
	
	public void LRU()
	{
		System.out.println("LRU activated");
		currentFrame = 0;
		
		while(breakNumber < Number_of_PageAccessRequests)
		{
	
			for(int i = 0; i < Number_Of_Frames;i++)
			{
				if (frame_Memory[i] == PageAccessRequests[breakNumber])
				{
					tempFrameNum = i;
					isFound = true;
				}
				
			}
			

			if (isFound == false)
			{
				
				int j = 0;
				
				leastTimestamp = 0;
				
			//	System.out.println("What is timestamp[j]? " + timeStamp[j] + " What is timestamp[j+1]? " + timeStamp[j+1]); 
				
				while (j < Number_Of_Frames)
				{
				//	System.out.println("What is timestamp[j]? " + timeStamp[j] + " What is timestamp[j+1]? " + timeStamp[j+1]); 
					if ((timeStamp[j] < timeStamp[leastTimestamp]) && timeStamp[j] != 999)
					{
	
					//	if ((timeStamp[j] != 999) && (timeStamp[j] < timeStamp[leastTimestamp]))
						leastTimestamp = j;
					//	System.out.println("LeastTimestamp " + leastTimestamp); 
					}

					j++;
						
				}
				
		//		System.out.println("What is Least TimeStamp " + leastTimestamp);
			
				
				
				previousFrame = frame_Memory[leastTimestamp];
				frame_Memory[leastTimestamp] = PageAccessRequests[breakNumber];
				timeStamp[leastTimestamp] = breakNumber;
			//	System.out.println("TimeStamp is " + timeStamp[leastTimestamp] + " with Page being" + frame_Memory[leastTimestamp]);
				System.out.println("Page " + previousFrame + " unloaded from Frame " + leastTimestamp +  ", Page " +  PageAccessRequests[breakNumber] + " loaded into Frame " + leastTimestamp);
				PrintOut[printTracker] = "Page " + previousFrame + " unloaded from Frame " + leastTimestamp +  ", Page " +  PageAccessRequests[breakNumber] + " loaded into Frame " + leastTimestamp;
				printTracker++;
				pageFault++;
				
				
			}

			else if (isFound == true)
			{
				System.out.println("Page " + PageAccessRequests[breakNumber] + " already in frame " + tempFrameNum);
				PrintOut[printTracker] = "Page " + PageAccessRequests[breakNumber] + " already in frame " + tempFrameNum;
				printTracker++;
				timeStamp[tempFrameNum] = breakNumber;
			//	System.out.println("Timestamp is " + timeStamp[tempFrameNum]);

			}
			
			isFound = false;
			breakNumber++;
			
			
		}
		
		System.out.println(pageFault + " page faults");
		PrintOut[printTracker] = pageFault + " page faults";
		printTracker++;
		
	}
	
	public String printResult (int i)
	{
		return PrintOut[i];
	}
	
	public int getPrintTracker()
	{
		return printTracker;
	}

}
