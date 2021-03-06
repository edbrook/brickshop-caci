package uk.co.dekoorb.caci.brickshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Brick Order Id")
public class InvalidBrickOrderIdException extends RuntimeException {
}
