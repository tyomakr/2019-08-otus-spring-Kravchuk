package ru.otus.spring.spring04.questionnaire.springshell.dao;

import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import ru.otus.spring.spring04.questionnaire.springshell.domain.Question;
import ru.otus.spring.spring04.questionnaire.springshell.config.appsettings.AppSettingsLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
@Component
public class QuestionDaoImpl implements QuestionDao {

    private final AppSettingsLoader appSettingsLoader;

    public QuestionDaoImpl(AppSettingsLoader appSettingsLoader) {
        this.appSettingsLoader = appSettingsLoader;
    }

    @Override
    public List<Question> getQuestionList() {
        Locale locale = LocaleContextHolder.getLocale();
        List<Question> qList = new ArrayList<>();

        String questionsFile = appSettingsLoader.getQaFilePrefix() + locale.getLanguage() + appSettingsLoader.getQaFileSuffix();

        try (InputStream inputStream = getClass().getResourceAsStream("/" + questionsFile)) {
            if (inputStream != null) {
                Reader reader = new InputStreamReader(inputStream);
                CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);

                for (CSVRecord csvRecord : parser) {
                    String question = csvRecord.get(1);
                    String correctAnswer = csvRecord.get(0);

                    qList.add(new Question(question, correctAnswer));
                }
                parser.close();
                reader.close();
            }

        } catch (IOException e) {
            e.getMessage();
        }
        return qList;
    }
}
