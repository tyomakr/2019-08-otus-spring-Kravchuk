package ru.otus.spring.spring04.questionnaire.springshell;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@SpringBootApplication
public class SpringShellApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringShellApp.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			Locale locale = new Locale("ru", "RU");
			LocaleContextHolder.setLocale(locale);
		};
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/i18n/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
