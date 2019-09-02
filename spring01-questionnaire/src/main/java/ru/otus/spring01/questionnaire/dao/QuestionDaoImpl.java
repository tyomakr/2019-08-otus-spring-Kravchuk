package ru.otus.spring01.questionnaire.dao;

import lombok.AllArgsConstructor;
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

@AllArgsConstructor
public class QuestionDaoImpl implements QuestionDao {


    private final String questionsFile;

    @Override
    public List<Question> getQuestionList() {
        List<Question> qList = new ArrayList<>();

        try (InputStream inputStream = getClass().getResourceAsStream("/" + questionsFile)) {
            if (inputStream != null) {
                Reader reader = new InputStreamReader(inputStream);
                CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);

                for (CSVRecord csvRecord : parser) {
                    String question = csvRecord.get(0);
                    String correctAnswer = csvRecord.get(1);

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
