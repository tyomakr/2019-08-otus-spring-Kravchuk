package ru.otus.spring01.questionnaire.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring01.questionnaire.domain.Question;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class QuestionDaoImpl implements QuestionDao {

    @Value("${qa.file.prefix}")
    private String qaFilePrefix;
    @Value("${qa.file.suffix}")
    private String qaFileSuffix;
    private Locale locale;


    @Override
    public List<Question> getQuestionList() {
        List<Question> qList = new ArrayList<>();

        String questionsFile = qaFilePrefix + locale.getCountry() + qaFileSuffix;

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

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
