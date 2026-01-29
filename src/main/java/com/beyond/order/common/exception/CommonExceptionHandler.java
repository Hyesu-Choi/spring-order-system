package com.beyond.order.common.exception;

import com.beyond.order.common.dto.CommonErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception e) {
        e.printStackTrace();
        CommonErrorDto dto = CommonErrorDto.builder()
                .status_code(500).error_message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
    }
}
