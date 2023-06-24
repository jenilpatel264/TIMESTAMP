package com.timeStamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.timeStamp.Model.ApiResponse;
import com.timeStamp.Model.timeStamp;
import com.timeStamp.Services.timeStampServiceIMPL;

@SpringBootTest
class TimestampApplicationTests {

	@Autowired
	timeStampServiceIMPL timeStampServiceIMPL;

	@Test
	void contextLoads() {

	}

	// checking with the proper input
	@Test
	public void addTimeStamp() {
		timeStamp stamp = new timeStamp(1385718411, 1385718412);
		ApiResponse apiResponse = this.timeStampServiceIMPL.addTimestamp(stamp);

		assertEquals(apiResponse.getMessage(), "SuccessFully added in the Log file");
	}

	// checking with starting point more then ending
	@Test
	public void LessThanStartTimestamp() {
		timeStamp stamp = new timeStamp(1385718411, 1385718410);
		ApiResponse apiResponse = this.timeStampServiceIMPL.addTimestamp(stamp);

		assertEquals(apiResponse.getMessage(), "Please send the proper input format");
	}

	// checking with the issue with the data in file
	// it is custom one but it may be change according to the timestamp length ,
	// here I refer as 10 length for timestamp
	@Test
	public void checkWithInproperInput() {
		timeStamp stamp = new timeStamp(138571840, 1385718410);
		ApiResponse apiResponse = this.timeStampServiceIMPL.addTimestamp(stamp);

		ApiResponse pickTimeResponse = this.timeStampServiceIMPL.getPickTime();
		System.out.println(pickTimeResponse.getMessage());

		assertEquals(pickTimeResponse.getMessage(), "The data is corrupted , Please check the file");
	}

	// checking with the proper data
	@Test
	public void checkWithproperInput() {
		try {
			PrintWriter writer = new PrintWriter("log.txt");
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timeStamp stamp = new timeStamp(1385718408, 1385718452);
		ApiResponse apiResponse = this.timeStampServiceIMPL.addTimestamp(stamp);
		timeStamp stamp1 = new timeStamp(1385718408, 1385718464);
		ApiResponse apiResponse1 = this.timeStampServiceIMPL.addTimestamp(stamp1);

		ApiResponse pickTimeResponse = this.timeStampServiceIMPL.getPickTime();
		System.out.println(pickTimeResponse.getMessage());
		assertTrue(pickTimeResponse.getMessage().contains("The peak for this call log is 2 simultaneous calls, that occurred between 1385718408 and 1385718452"));
	}

}
