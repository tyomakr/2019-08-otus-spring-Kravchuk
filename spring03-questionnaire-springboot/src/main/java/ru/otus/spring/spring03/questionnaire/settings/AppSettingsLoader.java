package ru.otus.spring.spring03.questionnaire.settings;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class AppSettingsLoader {

    private final AppSettings settings;

    @Autowired
    public AppSettingsLoader(AppSettings settings){
        this.settings = settings;
    }

}
