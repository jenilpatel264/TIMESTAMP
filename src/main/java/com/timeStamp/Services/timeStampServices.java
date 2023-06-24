package com.timeStamp.Services;

import com.timeStamp.Model.ApiResponse;
import com.timeStamp.Model.timeStamp;

public interface timeStampServices {
	
	ApiResponse  addTimestamp(timeStamp timeStamp);
	ApiResponse  getPickTime();

}
