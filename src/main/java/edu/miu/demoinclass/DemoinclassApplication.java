package edu.miu.demoinclass;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.util.UrlPathHelper;

@SpringBootApplication
public class DemoinclassApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoinclassApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
