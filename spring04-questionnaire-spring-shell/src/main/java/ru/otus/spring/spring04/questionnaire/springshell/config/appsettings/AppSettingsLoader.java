package ru.otus.spring.spring04.questionnaire.springshell.config.appsettings;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class AppSettingsLoader {

    private final AppSettings appSettings;

    @Autowired
    public AppSettingsLoader(AppSettings appSettings){
        this.appSettings = appSettings;
    }

}
