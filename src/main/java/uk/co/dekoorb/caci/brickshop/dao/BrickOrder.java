package uk.co.dekoorb.caci.brickshop.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BrickOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int numberOfBricks;

    public BrickOrder(int numberOfBricks) {
        this.numberOfBricks = numberOfBricks;
    }

    public Long getId() {
        return id;
    }

    public int getNumBricks() {
        return numberOfBricks;
    }

    public void setNumBricks(int numberOfBricks) {
        this.numberOfBricks = numberOfBricks;
    }
}
