package ru.otus.spring.spring04.questionnaire.springshell.config;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.spring04.questionnaire.springshell.config.appsettings.AppSettingsLoader;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties =  {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"})
@DisplayName("Класс AppSettingsLoaderTest")
class AppSettingsLoaderTest {

    @Autowired
    AppSettingsLoader appSettingsLoader;

    @Test
    @DisplayName("корректно получает префикс и суффикс файла")
    void shouldGetQaFilePrefixAndSuffix() {
        Assert.assertEquals(appSettingsLoader.getAppSettings().getQaFilePrefix(), "qa_");
        Assert.assertEquals(appSettingsLoader.getAppSettings().getQaFileSuffix(), ".csv");
    }

}