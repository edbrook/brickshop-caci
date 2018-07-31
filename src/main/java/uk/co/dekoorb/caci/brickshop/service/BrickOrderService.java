package uk.co.dekoorb.caci.brickshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.dekoorb.caci.brickshop.dao.BrickOrder;
import uk.co.dekoorb.caci.brickshop.dao.BrickOrderRepository;
import uk.co.dekoorb.caci.brickshop.exception.BrickOrderNotFoundException;
import uk.co.dekoorb.caci.brickshop.exception.InvalidBrickOrderException;
import uk.co.dekoorb.caci.brickshop.exception.InvalidBrickOrderIdException;

import java.util.List;
import java.util.Optional;

@Service
public class BrickOrderService {

    @Autowired
    private BrickOrderRepository repository;

    public BrickOrder createOrder(BrickOrder brickOrder) {
        checkIsValidOrder(brickOrder);
        return repository.save(new BrickOrder(brickOrder.getNumBricks()));
    }

    public BrickOrder getOrder(Long id) {
        checkIsValidOrderIdRange(id);
        Optional<BrickOrder> existingOrder = repository.findById(id);
        if (!existingOrder.isPresent()) {
            throw new BrickOrderNotFoundException();
        }
        return existingOrder.get();
    }

    public List<BrickOrder> getOrders() {
        return repository.findAll();
    }

    public BrickOrder updateOrder(Long id, BrickOrder brickOrder) {
        checkIsValidOrderIdRange(id);
        checkIsValidOrder(brickOrder);
        repository.deleteById(id);
        return repository.save(new BrickOrder(brickOrder.getNumBricks()));
    }

    private void checkIsValidOrderIdRange(Long id) {
        if (id <= 0) {
            throw new InvalidBrickOrderIdException();
        }
    }

    private void checkIsValidOrder(BrickOrder brickOrder) {
        if (brickOrder.getNumBricks() <= 0) {
            throw new InvalidBrickOrderException();
        }
    }
}
