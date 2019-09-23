package ru.otus.spring.spring03.questionnaire.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LocalesScannerService {

    private final MessageSource ms;
    private List<String> localesList = new ArrayList<>();
    private ConsoleService cs = new ConsoleService();


    Locale selectLocale() {

        Locale selectedLocale = new Locale("ru", "RU");

        List<String> localesList = scanMessagesLocales();
        cs.printMsg(ms.getMessage("qs.sep", null, selectedLocale));
        cs.printMsg(ms.getMessage("qs.select.lang", null, selectedLocale));

        for (int i = 1; i <= localesList.size(); i++) {
            cs.printMsg(i + " " + localesList.get(i - 1));
        }

        boolean isNotSelectedLocale = true;
        while (isNotSelectedLocale) {

            cs.printMsg(ms.getMessage("qs.select.enter.num", null, selectedLocale));

            try {
                int number = Integer.parseInt(cs.readMsg());

                if (number <= localesList.size() && number > 0) {
                    String sl = localesList.get(number - 1);
                    String lang = sl.substring(0,2);
                    int endInd = sl.length();
                    String country = sl.substring(3, endInd);
                    selectedLocale = new Locale(lang, country);
                    isNotSelectedLocale = false;
                }
            } catch (NumberFormatException e) {
                cs.printMsg(ms.getMessage("qs.err.num", null, selectedLocale));
            }
        }
        return selectedLocale;
    }


    private List<String> scanMessagesLocales() {        //сканирование всех файлов в папке i18n, для понимания, какое кол-во локалей присутствует
        String i18nResourcesRoot = "i18n";
        ClassLoader classLoader = LocalesScannerService.class.getClassLoader();
        File i18nFolder = new File(Objects.requireNonNull(classLoader.getResource(i18nResourcesRoot)).getFile());

        try {
            Files.walk(Paths.get(decodeGetParameter(String.valueOf(i18nFolder)))).forEach(filepath -> addFilesToList(filepath.toFile()));
        } catch (IOException e) {
            cs.printMsg(ms.getMessage("lss.err", null, LocaleContextHolder.getLocale()));
        }

        return localesList;
    }


    private void addFilesToList(File file) {
        if (file.getName().contains("messages_") && file.getName().endsWith(".properties") && !file.getName().startsWith(".")) {
            String localeCode = file.getName().replace("messages_", "").replace(".properties", "");
            localesList.add(localeCode);
        }
    }

    private static String decodeGetParameter(String parameter) throws UnsupportedEncodingException {
        return new String(URLDecoder.decode(parameter, "UTF-8").getBytes("UTF-8"), "UTF-8");
    }
}
