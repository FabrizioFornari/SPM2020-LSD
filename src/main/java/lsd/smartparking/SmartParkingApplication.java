package lsd.smartparking;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan("lsd.smartparking*")
@ComponentScan("lsd.smartparking*")
@EnableMongoRepositories(basePackages="lsd.smartparking*")
public class SmartParkingApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(SmartParkingApplication.class, args);
	}

}