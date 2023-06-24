package com.timeStamp.Services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.WritableByteChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import org.springframework.stereotype.Service;


import com.timeStamp.Model.ApiResponse;
import com.timeStamp.Model.timeStamp;
import org.slf4j.LoggerFactory;  
import ch.qos.logback.classic.Logger;

@Service
public class timeStampServiceIMPL implements timeStampServices {
	
	Logger logger=(Logger) LoggerFactory.getLogger(timeStampServiceIMPL.class); 

	@Override
	public ApiResponse addTimestamp(timeStamp timeStamp) {

		ApiResponse apiResponse = null;
		if (timeStamp.getStart() <= timeStamp.getEnd()) {
			try {
				FileWriter fileWriter = new FileWriter("log.txt", true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

				bufferedWriter.write(timeStamp.getStart() + ":" + timeStamp.getEnd());
				bufferedWriter.newLine();
				apiResponse = new ApiResponse("SuccessFully added in the Log file");
				logger.info("added successfully in log file ["+timeStamp.getStart()+"],["+timeStamp.getEnd()+"]");
				bufferedWriter.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			apiResponse = new ApiResponse("Please send the proper input format");
			logger.info("Please send the proper input format");
		}
	
		return apiResponse;
	}

	@Override
	public ApiResponse getPickTime() {
		ApiResponse apiResponse=null;
		int substractor[]= {0,1,01,001,0001,00001,000001,0000001,00000001,0000000001,0000000001};
		Stack<timeStamp> st = new Stack<>();
		//Initialisation of all  variables for find out the the period and maximum simultaneous
		int max_len = 0;
		int max_start = 0;
		int max_end = 0;

		try {
			
			//reading file for timestamp input
			FileReader fileReader = new FileReader("log.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String data = bufferedReader.readLine();
			if(data==null)
			{
				logger.info("File is Empty");
				return new ApiResponse("File is Empty");
			}
			while (data != null) {
				//check whether : sign has in proper position 
		
				int number = data.indexOf(":");
			
				//checking the position , it might be different as per the requirement
				if(number!=10)
				{
					logger.info("The data is corrupted , Please check the file");
					return new ApiResponse("The data is corrupted , Please check the file");
				}
				//fetch the start timestamp
				int start = Integer.valueOf(data.substring(0, number));
				//fetch the ending timestamp
				int end = Integer.valueOf(data.substring(number + 1));
				
				//when stack is empty then enter
				if (st.empty()) {
					st.push(new timeStamp(start, end));
				} else {
					//check with previous starting value
					if (start == st.peek().getStart()) {
						st.add(new timeStamp(start, end));
					} else {
						//checking whether is maximum calls or not
						if (max_len <= st.size()) {
							max_len = st.size();
							max_start = st.peek().getStart();
							//checking from mamximu calls for alliviate the range 
							while (!st.empty() && start < st.peek().getEnd()) {
								st.pop();
							}
							if (st.empty()) {
								int result=start-substractor[number];
								if(result==max_start)
								{
									max_end=start;
								}else
								{
									max_end = result;
								}
								
							} else {
							
							
								max_end = st.peek().getEnd();
							}
						}
						//stack empty and start from new 
						while (!st.empty()) {
							st.pop();
						}
						//add current value 
						st.add(new timeStamp(start, end));
					}
				}

				data = bufferedReader.readLine();
			}
			//checking last simultaneous calls.
			if(max_len<=st.size())
			{
				
				Iterator<timeStamp> it=st.iterator();
				max_len=st.size();
				
				max_start=st.peek().getStart();
				int minValue=Integer.MAX_VALUE;
				while(it.hasNext())
				{
					int storeElement=it.next().getEnd();
					if(storeElement<minValue)
					{
						minValue=storeElement;
					}
					
				}
				max_end=minValue;
			}
			logger.info("The peak for this call log is "+max_len+" simultaneous calls, that occurred between "+max_start+" and "+max_end);
		 apiResponse=new ApiResponse("The peak for this call log is "+max_len+" simultaneous calls, that occurred between "+max_start+" and "+max_end);
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return apiResponse;
	}

}
