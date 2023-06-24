package com.timeStamp.timestampController;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.timeStamp.Model.ApiResponse;
import com.timeStamp.Model.timeStamp;
import com.timeStamp.Services.timeStampServiceIMPL;

import ch.qos.logback.classic.Logger;

@RestController
public class timeStampController {
	
	@Autowired timeStampServiceIMPL timeStampServiceIMPL;
	Logger logger=(Logger) LoggerFactory.getLogger(timeStampController.class); 
	
	@PostMapping("/")
	public ResponseEntity<ApiResponse> insertTimestamp(@RequestBody timeStamp stamp)
	{
		logger.info("starting for adding timestamp in the log file");
		ApiResponse apiResponse=this.timeStampServiceIMPL.addTimestamp(stamp);
		logger.info("ending the addition process in the log file");
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<ApiResponse> getPickTime()
	{
		logger.info("starting the calculation of the picktime with their period");
		ApiResponse apiResponse=this.timeStampServiceIMPL.getPickTime();
		logger.info("ending the getPicktime");
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	}
	
	

}
