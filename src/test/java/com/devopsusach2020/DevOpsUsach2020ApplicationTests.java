package com.devopsusach2020;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.devopsusach2020.rest.RestData;

@SpringBootTest
class DevOpsUsach2020ApplicationTests {

	RestData restData = new RestData();

	@Test
	void contextLoads() {
	}


	@Test
	void verifyIfValidaApiCovidHttpIsStatus200_thenOk() {
		assertTrue(restData.validaApiCovid());
	}
}
