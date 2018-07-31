package uk.co.dekoorb.caci.brickshop.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.dekoorb.caci.brickshop.dao.BrickOrder;
import uk.co.dekoorb.caci.brickshop.exception.InvalidBrickOrderException;
import uk.co.dekoorb.caci.brickshop.exception.InvalidBrickOrderIdException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BrickOrderServiceTest {
    @Autowired
    private BrickOrderService service;

    /*
     * Stage 1 - Story 1 tests
     */
    @Test
    public void canCreateValidOrder() {
        int numberOfBricks = Integer.MAX_VALUE;
        BrickOrder order = service.createOrder(new BrickOrder(numberOfBricks));
        assertNotNull(order.getId());
        assertEquals(numberOfBricks, order.getNumBricks());
    }

    @Test
    public void ordersHaveUniqueIds() {
        final int ordersCount = 100;
        Set<Long> orderIds = new HashSet<>();
        for (int i = 0; i < ordersCount; i++) {
            BrickOrder order = createBrickOrder(i + 1);
            assertFalse(orderIds.contains(order.getId()));
            orderIds.add(order.getId());
        }
    }

    // NOTE: Story does not say what should happen with invalid orders,
    //       or what an invalid order looks like. So are the next two
    //       tests strictly valid/required ??
    @Test(expected = InvalidBrickOrderException.class)
    public void cannotCreateZeroBrickOrder() {
        int numberOfBricks = 0;
        BrickOrder order = createBrickOrder(numberOfBricks);
        assertNotNull(order.getId());
        assertEquals(numberOfBricks, order.getNumBricks());
    }

    @Test(expected = InvalidBrickOrderException.class)
    public void cannotCreateNegativeBrickOrder() {
        int numberOfBricks = Integer.MIN_VALUE;
        BrickOrder order = createBrickOrder(numberOfBricks);
        assertNotNull(order.getId());
        assertEquals(numberOfBricks, order.getNumBricks());
    }


    /*
     * Stage 1 - Story 2 tests
     */
    @Test
    public void getValidBrickOrder() {
        int numberOfBricks = Integer.MAX_VALUE;
        BrickOrder order = createBrickOrder(numberOfBricks);
        BrickOrder retrievedOrder = service.getOrder(order.getId());
        assertNotNull(retrievedOrder);
        assertEquals(order.getId(), retrievedOrder.getId());
        assertEquals(numberOfBricks, retrievedOrder.getNumBricks());
    }

    @Test(expected = InvalidBrickOrderIdException.class)
    public void cannotGetBrickOrderIdZero() {
        service.getOrder(0L);
    }

    @Test(expected = InvalidBrickOrderIdException.class)
    public void cannotGetNegativeBrickOrderId() {
        service.getOrder(Long.MIN_VALUE);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getAllBrickOrders() {
        int brickOrderCount = 100;
        for (int i = 0; i < brickOrderCount; i++) {
            createBrickOrder(i + 1);
        }
        List<BrickOrder> brickOrders = service.getOrders();
        assertNotNull(brickOrders);
        assertEquals(brickOrderCount, brickOrders.size());
        for (BrickOrder order : brickOrders) {
            assertNotNull(order.getId());
            assertEquals(order.getId().longValue(), order.getNumBricks());
        }
    }

    private BrickOrder createBrickOrder(int numberOfBricks) {
        return service.createOrder(new BrickOrder(numberOfBricks));
    }
}