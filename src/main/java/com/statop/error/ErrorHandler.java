package com.statop.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.statop.response.ErrorResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RestController
public class ErrorHandler extends ResponseEntityExceptionHandler implements ErrorController
{
    @ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request) {
        if (ex instanceof NotFoundException) {
            return super.handleExceptionInternal(ex, new ErrorResponse(HttpStatus.NOT_FOUND.value(), ""), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
        }
        return super.handleException(ex, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
        HttpHeaders headers, HttpStatus status, WebRequest request) {
        //todo: better messages, don't use exception.getMessage because xss
        return super.handleExceptionInternal(ex, new ErrorResponse(status.value(), ""), headers, status, request);
    }

    //needed for stuff that doesn't throw exceptions
    @RequestMapping("/error")
    public ErrorResponse error(WebRequest webRequest, HttpServletRequest request, HttpServletResponse response) {
        return new ErrorResponse(response.getStatus(), "");
    }

    @Override
    public String getErrorPath()
    {
        return "/error";
    }
}
