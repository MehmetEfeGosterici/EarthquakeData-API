package com.takeHomeProject.EarthquakeAPI;

import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EarthquakeApiApplicationTests {

	@Value(value="${local.server.port}")
	private int port;

	@Test
	public void statusCodeTest() throws Exception {

		int status = getRequest("http://localhost:" + port + "/api/EarthquakeData?country=Turkey&days=1");
		int status2 = getRequest("http://localhost:" + port + "/api/EarthquakeData/dateRange?country=Turkey");
		int status3 = getRequest("http://localhost:" + port + "/api/EarthquakeData/filter?country=Turkey&maxMagnitude=5&limit=10&orderBy=time");

		assertThat(status).isEqualTo(200);
		assertThat(status2).isEqualTo(200);
		assertThat(status3).isEqualTo(200);
	}

	public int getRequest(String URL) throws IOException {
		return Request
				.Get(URL)
				.execute()
				.returnResponse()
				.getStatusLine()
				.getStatusCode();
	}


}
