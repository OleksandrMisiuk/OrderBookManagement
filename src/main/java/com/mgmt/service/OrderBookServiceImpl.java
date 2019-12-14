package com.mgmt.service;

import com.mgmt.entity.Book;
import com.mgmt.entity.Type;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class OrderBookServiceImpl implements OrderBookService {

    private List<Book> books;

    public OrderBookServiceImpl() {
        books = new LinkedList<>();
    }

    public OrderBookServiceImpl(List<Book> books) {
        books.sort((o1, o2) -> o2.getPrice()-o1.getPrice());
        this.books = books;
    }

    @Override
    public void updateBook(int price, int size, Type type) {
        books.stream()
                .filter(book -> book.getType() == type && book.getPrice() == price)
                .forEachOrdered(book -> book.setSize(size));
    }

    @Override
    public Book queryBestAsk() {
        Optional<?> optBook = books.stream()
                .filter(book -> book.getType() == Type.A && book.getPrice() != 0)
                .min(Comparator.comparingInt(Book::getPrice));
        if (optBook.isPresent()) {
            return (Book) optBook.get();
        } else throw new RuntimeException("Best ask doesn't exist");
    }

    @Override
    public Book queryBestBid() {
        Optional<?> optBook = books.stream()
                .filter(book -> book.getType() == Type.B && book.getPrice() != 0)
                .max(Comparator.comparingInt(Book::getPrice));
        if (optBook.isPresent()) {
            return (Book) optBook.get();
        } else throw new RuntimeException("Best bid doesn't exist");
    }

    @Override
    public Book queryBookSize(int price) {
        Optional<?> optBook = books.stream()
                .filter(book -> book.getPrice() == price)
                .findFirst();
        if (optBook.isPresent()) {
            return (Book) optBook.get();
        } else throw new RuntimeException("Book doesn't exist at specified price");
    }

    @Override
    public void orderBuy(int size) {
        books.stream()
                .filter(book -> book.getType() == Type.A && book.getPrice() == books.stream()
                        .filter(b -> b.getType() == Type.A && b.getPrice() != 0)
                        .min(Comparator.comparingInt(Book::getPrice)).get().getPrice())
                .forEach(book -> book.setSize(book.getSize() - size));
    }

    @Override
    public void orderSell(int size) {
        books.stream()
                .filter(book -> book.getType() == Type.B && book.getPrice() == books.stream()
                        .filter(b -> b.getType() == Type.B && b.getPrice() != 0)
                        .max(Comparator.comparingInt(Book::getPrice)).get().getPrice())
                .forEach(book -> book.setSize(book.getSize() - size));
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        this.books.sort((o1, o2) -> o2.getPrice() - o1.getPrice());
    }

    public void printBooks() {
        books.forEach(System.out::println);
    }
}
