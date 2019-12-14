package com.mgmt.service;

import com.mgmt.entity.Book;
import com.mgmt.entity.Type;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * The FileServiceImpl class loads a file and makes actions according to file's commands
 */
public class FileServiceImpl implements FileService {
    private List<String> lines;
    private OrderBookService orderBookService;
    private String out;

    public FileServiceImpl(String in, String out, OrderBookService orderBookService) {
        this.orderBookService = orderBookService;
        this.out = out;
        lines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(in))) {
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method analyzes strings and executes appropriate command
     */
    @Override
    public void read() {
        for (String s : lines) {
            String[] strings = s.split(",");
            switch (strings[0]) {
                case "u":
                    update(strings);
                    break;
                case "q":
                    query(strings);
                    break;
                case "o":
                    order(strings);
                    break;
            }
        }
    }

    /**
     * The method writes the price and the size to a file
     * @param price
     * @param size
     */
    private void write(int price, int size) {
        try (FileWriter fileWriter = new FileWriter(out, true)) {
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(price + "," + size);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method writes the size to a file
     * @param size
     */
    private void write(int size) {
        try (FileWriter fileWriter = new FileWriter(out, true)) {
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(size);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void update(String[] strings) {
        int price = Integer.parseInt(strings[1]);
        int size = Integer.parseInt(strings[2]);
        if (strings[3].toLowerCase().equals("bid")) {
            orderBookService.updateBook(price, size, Type.B);
        } else if (strings[3].toLowerCase().equals("ask")) {
            orderBookService.updateBook(price, size, Type.A);
        }
    }

    /**
     * The method checks length of command, book's type, gets appropriate data and calls method to write it to a file
     * @param strings
     */
    private void query(String[] strings) {
        if (strings.length == 3) {
            int price = Integer.parseInt(strings[2]);
            Book book = orderBookService.queryBookSize(price);
            write(book.getSize());
        } else if (strings.length == 2) {
            if (strings[1].toLowerCase().equals("best_bid")) {
                Book book = orderBookService.queryBestBid();
                write(book.getPrice(), book.getSize());
            } else if (strings[1].toLowerCase().equals("best_ask")) {
                Book book = orderBookService.queryBestAsk();
                write(book.getPrice(), book.getSize());
            }
        }
    }

    /**
     * The method checks book's type and calls appropriate method from orderBookService
     * @param strings
     */
    private void order(String[] strings) {
        int size = Integer.parseInt(strings[2]);
        if (strings[1].toLowerCase().equals("buy")) {
            orderBookService.orderBuy(size);
        } else if (strings[1].toLowerCase().equals("sell")) {
            orderBookService.orderSell(size);
        }
    }
}
