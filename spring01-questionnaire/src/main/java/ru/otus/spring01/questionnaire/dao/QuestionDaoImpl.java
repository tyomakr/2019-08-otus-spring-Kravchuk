package ru.otus.spring01.questionnaire.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.otus.spring01.questionnaire.domain.Question;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuestionDaoImpl implements QuestionDao {

    private String questionsFile;
    private Locale locale;

    public QuestionDaoImpl(String questionsFile) {
        this.questionsFile = questionsFile;
    }

    @Override
    public List<Question> getQuestionList() {
        List<Question> qList = new ArrayList<>();

        try (InputStream inputStream = getClass().getResourceAsStream("/" + questionsFile)) {
            if (inputStream != null) {
                Reader reader = new InputStreamReader(inputStream);
                CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);

                int countryCSVIndex;
                if (locale.getCountry().equalsIgnoreCase("RU")) {
                    countryCSVIndex = 1;
                }
                else if (locale.getCountry().equalsIgnoreCase("EN")) {
                    countryCSVIndex = 2;
                }
                else throw new IOException();

                for (CSVRecord csvRecord : parser) {
                    String correctAnswer = csvRecord.get(0);
                    String question = csvRecord.get(countryCSVIndex);

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
