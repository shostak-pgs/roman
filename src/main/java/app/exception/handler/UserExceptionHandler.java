package app.exception.handler;

import static app.page_path.PagePath.*;
import static org.h2.mvstore.DataUtils.getErrorCode;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class UserExceptionHandler  {
    private static String ERROR = "error";
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleError404(HttpServletRequest request, Exception e)   {
        System.out.println("httpErrorCode");
        return new ModelAndView(ERROR_PATH_404.getURL()).addObject(ERROR, e.getMessage());
    }
}
