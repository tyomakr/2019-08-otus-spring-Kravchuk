package ru.otus.spring.spring04.questionnaire.springshell.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChangeLocaleService {

    private final MessageSource ms;
    private ConsoleService cs = new ConsoleService();

    public void changeLocale() {
        List<String> localesList = scanMessagesFiles();
        cs.printMsg(ms.getMessage("cpp.small.sep", null, LocaleContextHolder.getLocale()));
        cs.printMsg(ms.getMessage("qs.select.lang", null, LocaleContextHolder.getLocale()));

        for (int i = 1; i <= localesList.size(); i++) {
            cs.printMsg(i + " " + localesList.get(i - 1));
        }

        cs.printMsg(ms.getMessage("qs.select.enter.num", null, LocaleContextHolder.getLocale()));
        int number = Integer.parseInt(cs.readMsg());

        if (number <= localesList.size() && number > 0) {
            String sl = localesList.get(number - 1);
            String lang = sl.substring(0,2);
            int endInd = sl.length();
            String country = sl.substring(3, endInd);
            Locale selectedLocale = new Locale(lang, country);
            LocaleContextHolder.setLocale(selectedLocale);
        }

    }

    private List<String> scanMessagesFiles() {
        List<String> localesList = new ArrayList<>();

        String i18nResourcesRoot = "i18n";
        ClassLoader classLoader = this.getClass().getClassLoader();

        File i18nFolder = new File(Objects.requireNonNull(classLoader.getResource(i18nResourcesRoot)).getFile());

        try {
            Files.walk(Paths.get(String.valueOf(i18nFolder))).forEach(filepath -> addFilesToList(filepath.toFile(), localesList));
        } catch (IOException e) {
            cs.printMsg(ms.getMessage("cls.scan.err", null, LocaleContextHolder.getLocale()));
        }
        return localesList;
    }

    private void addFilesToList(File file, List<String> localesList) {
        if (file.getName().contains("messages_") && file.getName().endsWith(".properties") && !file.getName().startsWith(".")) {
            String localeCode = file.getName().replace("messages_", "").replace(".properties", "");
            localesList.add(localeCode);
        }
    }

}
