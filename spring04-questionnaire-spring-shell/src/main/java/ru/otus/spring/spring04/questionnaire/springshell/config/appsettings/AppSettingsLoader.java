package ru.otus.spring.spring04.questionnaire.springshell.config.appsettings;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class AppSettingsLoader {

    private final String qaFilePrefix;
    private final String qaFileSuffix;

    @Autowired
    public AppSettingsLoader(AppSettings settings){
        this.qaFilePrefix = settings.getQaFilePrefix();
        this.qaFileSuffix = settings.getQaFileSuffix();
    }

}
