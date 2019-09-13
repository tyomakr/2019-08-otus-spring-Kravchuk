package ru.otus.spring.spring03.questionnaire.settings;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
@Data
public class AppSettings {

    private String qaFilePrefix;
    private String qaFileSuffix;

}
