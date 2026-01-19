package ch.zhaw.ssdd.bookstack.domain.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import ch.zhaw.ssdd.bookstack.domain.model.Book;

public final class DeduplicateBooks {
    public static List<Book> execute(List<Book> inputList) {
        return inputList.stream()
            .collect(Collectors.collectingAndThen(
                    Collectors.toMap(
                        Book::title,
                        Function.identity(),
                        (a, b) -> a,        
                        LinkedHashMap::new),
                    m -> List.copyOf(m.values())));
    }
}
