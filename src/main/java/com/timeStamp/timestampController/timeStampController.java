package com.timeStamp.timestampController;

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

@RestController
public class timeStampController {
	
	@Autowired timeStampServiceIMPL timeStampServiceIMPL;
	
	@PostMapping("/")
	public ResponseEntity<ApiResponse> insertTimestamp(@RequestBody timeStamp stamp)
	{
		ApiResponse apiResponse=this.timeStampServiceIMPL.addTimestamp(stamp);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<ApiResponse> getPickTime()
	{
		ApiResponse apiResponse=this.timeStampServiceIMPL.getPickTime();
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	}
	
	

}
