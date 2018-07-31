package uk.co.dekoorb.caci.brickshop.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.dekoorb.caci.brickshop.dao.BrickOrder;
import uk.co.dekoorb.caci.brickshop.exception.BrickOrderNotFoundException;
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
        BrickOrder order = createBrickOrder(numberOfBricks);
        assertNotNull(order.getId());
        assertEquals(numberOfBricks, order.getNumBricks());
    }

    @Test
    public void shouldIgnoreIdWhenCreatingOrder() {
        int numberOfBricks = Integer.MAX_VALUE;
        int newBrickCount = 100;
        BrickOrder order = createBrickOrder(numberOfBricks);
        order.setNumBricks(newBrickCount);
        BrickOrder newOrder = service.createOrder(order);
        assertNotNull(newOrder);
        assertNotEquals(order.getId(), newOrder.getId());
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

    /*
     * Stage 2 - Story 1 tests
     */
    // NOTE: Story does not say what happens with the old order id just
    //       that a new order id is returned.
    //       For the purposes of this task it gets deleted/replaced.
    //       (I've assumed that the order id is the same as the primary key for
    //       the entity)
    @Test
    public void canUpdateExistingOrder() {
        int initialBrickCount = 100;
        int newBrickCount = 200;
        BrickOrder order = createBrickOrder(initialBrickCount);
        order.setNumBricks(newBrickCount);
        BrickOrder updatedOrder = service.updateOrder(order.getId(), order);
        assertNotNull(updatedOrder);
        assertNotEquals(order.getId(), updatedOrder.getId());
        assertEquals(newBrickCount, updatedOrder.getNumBricks());
    }

    @Test(expected = InvalidBrickOrderIdException.class)
    public void cannotUpdateUsingInvalidOrderId() {
        BrickOrder order = new BrickOrder(100);
        service.updateOrder(0L, order);
    }

    @Test(expected = InvalidBrickOrderException.class)
    public void cannotUpdateWithInvalidOrder() {
        int initialBrickCount = 100;
        BrickOrder order = createBrickOrder(initialBrickCount);
        order.setNumBricks(0);
        service.updateOrder(order.getId(), order);
    }

    @Test(expected = BrickOrderNotFoundException.class)
    public void cannotUpdateNonexistentOrder() {
        BrickOrder order = new BrickOrder(100);
        service.updateOrder(Long.MAX_VALUE, order);
    }

    /*
     * Helper methods
     */
    private BrickOrder createBrickOrder(int numberOfBricks) {
        return service.createOrder(new BrickOrder(numberOfBricks));
    }
}