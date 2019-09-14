package ru.otus.spring.spring03.questionnaire.dao;

import lombok.Data;
import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import ru.otus.spring.spring03.questionnaire.domain.Question;
import ru.otus.spring.spring03.questionnaire.settings.AppSettingsLoader;

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
    private Locale locale;

    public QuestionDaoImpl(AppSettingsLoader appSettingsLoader) {
        this.appSettingsLoader = appSettingsLoader;
    }

    @Override
    public List<Question> getQuestionList() {
        List<Question> qList = new ArrayList<>();

        String questionsFile = appSettingsLoader.getQaFilePrefix() + locale.getCountry() + appSettingsLoader.getQaFileSuffix();

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
