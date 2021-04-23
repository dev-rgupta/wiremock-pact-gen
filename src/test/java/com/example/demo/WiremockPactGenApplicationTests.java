package com.example.demo;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.atlassian.ta.wiremockpactgenerator.WireMockPactGenerator;
import com.github.tomakehurst.wiremock.WireMockServer;

@SpringBootTest
class WiremockPactGenApplicationTests {

	private WireMockServer wireMockServer;
	private RestTemplate restTemplate;

	@BeforeEach
	void configureSystemUnderTest() {
		this.restTemplate = new RestTemplate();
		this.wireMockServer = new WireMockServer(options().bindAddress("localhost").port(9090));
		// 2. Add the WireMockPactGenerator listener
		wireMockServer.addMockServiceRequestListener(
		    WireMockPactGenerator
		        .builder("the-consumer", "the-provider")
		        .build()
		);
		this.wireMockServer.start();
		// configureFor("127.0.0.1", 9090);
	}

	@Test
	@DisplayName("Should ensure that WireMock server was started")
	void shouldEnsureThatServerWasStarted() {
		
		// 3. That's it!.. create your endpoint stubs and use them.
		wireMockServer.addStubMapping(get(urlEqualTo("/posts/1"))
		                .willReturn(aResponse().withStatus(200))
		                .build()
		);
		
	//	givenThat(get(urlEqualTo("/posts/1")).willReturn(aResponse().withStatus(200)));

		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:9090/posts/1", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@AfterEach
	void stopWireMockServer() {
		this.wireMockServer.stop();
	}

}
