package ru.otus.spring.spring03.questionnaire.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AvailableLocalesScannerService {

    private final MessageSource ms;
    private List<String> localesList = new ArrayList<>();
    private ConsoleService consoleService = new ConsoleService();

    public AvailableLocalesScannerService(MessageSource ms) {
        this.ms = ms;
    }

    List<String> getAvailableLocalesList() {        //сканирование всех файлов в папке i18n, для понимания, какое кол-во локалей присутствует
        String i18nResourcesRoot = "i18n";

        ClassLoader classLoader = AvailableLocalesScannerService.class.getClassLoader();
        File i18nFolder = new File(Objects.requireNonNull(classLoader.getResource(i18nResourcesRoot)).getFile());

        try {
            Files.walk(Paths.get(String.valueOf(i18nFolder))).forEach(filepath -> addFilesToList(filepath.toFile()));
        } catch (IOException e) {
            consoleService.printMsg(ms.getMessage("alss.err", null, LocaleContextHolder.getLocale()));
        }

        return localesList;
    }

    private void addFilesToList(File file) {
        if (file.getName().contains("messages_") && file.getName().endsWith(".properties") && !file.getName().startsWith(".")) {
            String localeCode = file.getName().replace("messages_", "").replace(".properties", "");
            localesList.add(localeCode);
        }
    }


}
