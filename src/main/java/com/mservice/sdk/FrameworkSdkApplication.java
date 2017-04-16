package com.mservice.sdk;

import com.mservice.sdk.config.MainConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.io.Console;

@SpringBootApplication
@EnableCaching
public class FrameworkSdkApplication{

	public static void main(String[] args) {
		SpringApplication.run(FrameworkSdkApplication.class, args);
	}
}
