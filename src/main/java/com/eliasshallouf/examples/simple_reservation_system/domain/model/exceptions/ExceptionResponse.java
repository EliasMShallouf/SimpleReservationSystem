package com.eliasshallouf.examples.simple_reservation_system.domain.model.exceptions;

import java.io.Serializable;

public record ExceptionResponse(String cause) implements Serializable {

}
