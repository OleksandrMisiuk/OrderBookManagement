package com.mgmt;

import com.mgmt.entity.Book;
import com.mgmt.entity.Type;
import com.mgmt.service.FileService;
import com.mgmt.service.FileServiceImpl;
import com.mgmt.service.OrderBookService;
import com.mgmt.service.OrderBookServiceImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        String in = Objects.requireNonNull(Main.class.getClassLoader().getResource("Command")).getPath();
        in = in.substring(1);
        String out = System.getProperty("user.dir") + "\\src\\main\\resources\\Output";
	    List<Book> books = new LinkedList<>(List.of(
	            new Book(100, 50, Type.A), new Book(99, 0, Type.A),
                new Book(97,0,Type.S), new Book(98, 0, Type.S),
                new Book(95, 40, Type.B), new Book(94, 30, Type.B),
                new Book(93, 15, Type.B), new Book(92,77, Type.B)));

        OrderBookService orderBookService = new OrderBookServiceImpl(books);
        FileService fileService = new FileServiceImpl(in, out, orderBookService);
        fileService.read();
    }
}
