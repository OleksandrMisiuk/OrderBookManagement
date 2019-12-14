import com.mgmt.entity.Book;
import com.mgmt.entity.Type;
import com.mgmt.service.OrderBookService;
import com.mgmt.service.OrderBookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderBookServiceTest {
    private List<Book> books = new LinkedList<>(List.of(
            new Book(100, 50, Type.A), new Book(99, 0, Type.A),
            new Book(97, 0, Type.S), new Book(98, 0, Type.S),
            new Book(95, 40, Type.B), new Book(94, 30, Type.B),
            new Book(93, 15, Type.B), new Book(92, 77, Type.B)));

    private OrderBookService orderBookService;

    @BeforeEach
    void init() {
        orderBookService = new OrderBookServiceImpl(books);
    }

    @Test
    void test_queryBestAsk_successFlow() {
        assertEquals(orderBookService.queryBestAsk(), new Book(99, 0, Type.A));
    }

    @Test
    void test_queryBestBid_successFlow() {
        assertEquals(orderBookService.queryBestBid(), new Book(95, 40, Type.B));
    }

    @Test
    void test_queryBestAsk_badFlow() {
        OrderBookService orderBookService1 = new OrderBookServiceImpl();
        assertThrows(RuntimeException.class, orderBookService1::queryBestAsk);
    }

    @Test
    void test_queryBestBid_badFlow() {
        OrderBookService orderBookService1 = new OrderBookServiceImpl();
        assertThrows(RuntimeException.class, orderBookService1::queryBestBid);
    }

    @Test
    void test_queryBookSize_badFlow() {
        assertThrows(RuntimeException.class, () -> orderBookService.queryBookSize(Integer.MAX_VALUE));
    }

    @Test
    void test_queryBookSize_successFlow() {
        assertEquals(orderBookService.queryBookSize(99).getSize(), 0);
    }

    @Test
    void test_updateBook() {
        orderBookService.updateBook(99, 2, Type.A);
        assertEquals(orderBookService.queryBookSize(99).getSize(), 2);
    }

    @Test
    void test_orderBuy(){
        orderBookService.orderBuy(1);
        assertEquals(orderBookService.queryBookSize(99).getSize(), -1);
    }

    @Test
    void test_orderSell(){
        orderBookService.orderSell(20);
        assertEquals(orderBookService.queryBookSize(95).getSize(), 20);
    }


}
