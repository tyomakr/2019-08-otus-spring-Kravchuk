package ru.otus.spring.library.webflux.event;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.library.webflux.domain.Book;
import ru.otus.spring.library.webflux.repository.CommentRepository;

@Component
@RequiredArgsConstructor
public class BookEventListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        Document document = event.getSource();
        String id = document.get("id").toString();
        commentRepository.removeAllCommentsFromBookByBookId(id);
    }
}
