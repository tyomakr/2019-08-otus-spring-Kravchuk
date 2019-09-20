package ru.otus.spring.spring04.questionnaire.springshell;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;

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
}
