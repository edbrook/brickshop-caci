package uk.co.dekoorb.caci.brickshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.dekoorb.caci.brickshop.dao.BrickOrder;
import uk.co.dekoorb.caci.brickshop.dao.BrickOrderRepository;
import uk.co.dekoorb.caci.brickshop.exception.InvalidBrickOrderException;

import java.util.List;

@Service
public class BrickOrderService {

    @Autowired
    private BrickOrderRepository repository;

    public BrickOrder createOrder(BrickOrder brickOrder) {
        if (brickOrder.getNumBricks() <= 0) {
            throw new InvalidBrickOrderException();
        }
        return repository.save(brickOrder);
    }

    public BrickOrder getOrder(Long id) {
        return null;
    }

    public List<BrickOrder> getOrders() {
        return null;
    }
}
