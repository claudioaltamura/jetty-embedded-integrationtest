package de.claudioaltamura.jetty.embedded.integrationtest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HelloWorldServletIntegrationTest {

	private Server server;

	@Before
	public void setUp() throws Exception {
		server = new Server(8080);
		ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/test");
		ServletHolder asyncHolder = context.addServlet(HelloWorldServlet.class, "/helloworld");
		asyncHolder.setAsyncSupported(true);
		server.setHandler(context);
		server.start();
	}

	@After
	public void tearDown() throws Exception {
		server.stop();
		server.join();
	}

	@Test
	public void ok() throws ClientProtocolException, IOException {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = httpClientBuilder.build();
		HttpGet request = new HttpGet("http://localhost:8080/test/helloworld");

		CloseableHttpResponse response = httpClient.execute(request);

		assertEquals(HttpServletResponse.SC_OK, response.getStatusLine().getStatusCode());
	}

}
