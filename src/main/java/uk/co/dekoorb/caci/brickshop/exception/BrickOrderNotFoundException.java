package uk.co.dekoorb.caci.brickshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Brick Order Not Found")
public class BrickOrderNotFoundException extends RuntimeException {
}
