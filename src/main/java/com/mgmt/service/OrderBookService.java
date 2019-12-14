package com.mgmt.service;

import com.mgmt.entity.Book;
import com.mgmt.entity.Type;

public interface OrderBookService {
    void updateBook(int price, int size, Type type);

    Book queryBestAsk();

    Book queryBestBid();

    Book queryBookSize(int price);

    void orderBuy(int size);

    void orderSell(int size);

    void printBooks();
}
