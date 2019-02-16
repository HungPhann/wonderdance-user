package tk.wonderdance.user.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tk.wonderdance.user.exception.exception.*;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="User Not Found")  // 404
    @ExceptionHandler(UserNotFoundException.class)
    public void handleUserNotFoundException(HttpServletRequest request, UserNotFoundException e){
        logger.info("[UserNotFoundException] " + e.getMessage() +  " at: " + request.getRequestURL());
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Parameter value is invalid")  // 400
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void handleMethodArgumentTypeMismatchException(HttpServletRequest request){
        logger.info("[MethodArgumentTypeMismatchException] at: " + request.getRequestURL());
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Achievement Not Found")  // 404
    @ExceptionHandler(AchievementNotFoundException.class)
    public void handleAchievementNotFoundException(HttpServletRequest request, AchievementNotFoundException e){
        logger.info("[AchievementNotFoundException] " + e.getMessage() + " at: " + request.getRequestURL());
    }

    @ResponseStatus(value= HttpStatus.FORBIDDEN, reason="Permission Denied")  // 403
    @ExceptionHandler(ForbiddenException.class)
    public void handleForbiddenException(HttpServletRequest request, ForbiddenException e){
        logger.info("[AchievementNotFoundException] " + e.getMessage() + " at: " + request.getRequestURL());
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Resource has existed")
    @ExceptionHandler(ResourceConflictException.class)
    public void handleResourceConflictException(HttpServletRequest request, ResourceConflictException e){
        logger.info("[ResourceConflictException] " + e.getMessage() + " at: " + request.getRequestURL());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Parameter value is invalid")
    @ExceptionHandler(CustomMethodArgumentTypeMismatchException.class)
    public void handleCustomMethodArgumentTypeMismatchException(HttpServletRequest request, CustomMethodArgumentTypeMismatchException e){
        logger.info("[CustomMethodArgumentTypeMismatchException] " + e.getMessage() + " at: " + request.getRequestURL());
    }
}
