package com.mgmt.entity;

import java.util.Objects;

public class Book {
    private int price;
    private int size;
    private  Type type;

    public Book() {
    }

    public Book(int price, int size, Type type) {
        this.price = price;
        this.size = size;
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Book{" +
                "price=" + price +
                ", size=" + size +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return price == book.price &&
                size == book.size &&
                type == book.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, size, type);
    }
}
