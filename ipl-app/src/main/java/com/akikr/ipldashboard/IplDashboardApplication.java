package com.akikr.ipldashboard;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootApplication
public class IplDashboardApplication
{
	public static void main(String[] args)
	{
		log.info("IplDashboardApplication started executing 'main' method with arguments:{}", Arrays.asList(args));
		SpringApplication.run(IplDashboardApplication.class, args);
		log.info("Completed executing 'main' method");

		Runtime.getRuntime().addShutdownHook(new Thread(() -> log.info("Shutting down IplDashboardApplication !!")));
	}

	@Bean
	public OpenAPI openApi(@Value("${app.server.host_address:localhost/app}") String serverHostAddress)
	{
		//@formatter:off
		return new OpenAPI().info(new Info()
				.title("IPL-DashboardApplication API Documentation")
				.version("1.0.0")
				.description("API documentation for my IPL-DashboardApplication"))
				.servers(getServers(serverHostAddress));
		//@formatter:on
	}

	private List<Server> getServers(String serverHostAddress)
	{
		return List.of(
				new Server().url("http://localhost:8081/app").description("Dev"),
				new Server().url("http://" + serverHostAddress).description("Prod-HTTP"),
				new Server().url("https://" + serverHostAddress).description("Prod-HTTPS"));
	}
}
