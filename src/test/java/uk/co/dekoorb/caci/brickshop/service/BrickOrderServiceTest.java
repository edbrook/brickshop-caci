package uk.co.dekoorb.caci.brickshop.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.dekoorb.caci.brickshop.dao.BrickOrder;
import uk.co.dekoorb.caci.brickshop.exception.InvalidBrickOrderException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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

    private BrickOrder createBrickOrder(int numberOfBricks) {
        return service.createOrder(new BrickOrder(numberOfBricks));
    }
}