package rs.levi9.library.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import rs.levi9.library.domain.BaseEntity;
import rs.levi9.library.domain.Book;

@Repository
public class InMemoryBookRepository implements BookRepository {
    
    private Map<Long, Book> books = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);
    
    @Override
    public Book findOne(Long id) throws IllegalArgumentException {
        Book book = books.get(id);
        if (book == null) {
            throw new IllegalArgumentException();
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException {
        if (!books.containsKey(id)) {
            throw new IllegalArgumentException("book does not exist");
        }
        books.remove(id);
    }

    @Override
    public <T extends BaseEntity> T save(T entity) {
        if (entity.getId() == null) {
            entity.setId(sequence.getAndIncrement());
        }
        books.put(entity.getId(), (Book) entity);
        return entity;
    }

    @Override
    public List<Book> findByCategory(Long id) {
        List<Book> result = new ArrayList<>();
        for (Map.Entry<Long, Book> book : books.entrySet()) {
            if (book.getValue().getCategory().getId().equals(id)) {
                result.add(book.getValue());
            }
        }
        return result;
    }
}
