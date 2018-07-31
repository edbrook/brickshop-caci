package uk.co.dekoorb.caci.brickshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.co.dekoorb.caci.brickshop.dao.BrickOrder;
import uk.co.dekoorb.caci.brickshop.service.BrickOrderService;

@RestController
public class BrickOrderController {

    private final BrickOrderService orderService;

    @Autowired
    public BrickOrderController(BrickOrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public Long createOrder(@RequestBody BrickOrder brickOrder) {
        return orderService.createOrder(brickOrder).getId();
    }
}
