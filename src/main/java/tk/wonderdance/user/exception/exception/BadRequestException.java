package tk.wonderdance.user.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Missing required request parameters or parameter value is invalid")  // 404
public class BadRequestException extends Exception{
}
