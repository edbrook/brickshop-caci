package uk.co.dekoorb.caci.brickshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.co.dekoorb.caci.brickshop.dao.BrickOrder;
import uk.co.dekoorb.caci.brickshop.service.BrickOrderService;

import java.util.List;

@RestController
public class BrickOrderController {

    private final BrickOrderService orderService;

    @Autowired
    public BrickOrderController(BrickOrderService orderService) {
        this.orderService = orderService;
    }

    // Mapping for Stage 1 story 1
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public Long createOrder(@RequestBody BrickOrder brickOrder) {
        return orderService.createOrder(brickOrder).getId();
    }

    // Mappings for Stage 1 story 2
    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
    public BrickOrder getSingleOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public List<BrickOrder> getAllOrders() {
        return orderService.getOrders();
    }

    // Mappings for Stage 2 story 1
    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.PUT)
    public Long updateExistingOrder(
            @RequestBody BrickOrder brickOrder,@PathVariable Long orderId) {
        return orderService.updateOrder(orderId, brickOrder).getId();
    }
}
