package com.banking.Payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class PaymentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentsApplication.class, args);
		var context=new AnnotationConfigApplicationContext(Configurations.class);
	}

}
