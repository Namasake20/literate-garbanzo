package com.namasake.LoginAPI.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@SuperBuilder
public class HttpResponse {
    protected String timestamp;
    protected HttpStatus status;
    protected int statusCode;
    protected String message;
    protected String developerMessage;
    protected Map<?,?> data;
}
