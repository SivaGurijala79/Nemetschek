package com.spacewell.tenissdesign.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(StartMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleCantStartOtherMatch(StartMatchException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setDescription(ex.getMessage());
        if (ex.getExceptionType().equals(ExceptionType.MATCH_IN_PROGRESS))
            response.setFullDesc("Already team are playing you need to close the match before starting new one. To close a match URI is /close-match.");
        else if (ex.getExceptionType().equals(ExceptionType.TEAMS_SIZE))
            response.setFullDesc("To start a match only/minimum 2 teams should be available");
        else if (ex.getExceptionType().equals(ExceptionType.SAME_NAME))
            response.setFullDesc("It is required to have different names for each team.");
        return response;
    }

    @ExceptionHandler(NoTeamFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleNoTeamFound(NoTeamFound ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setDescription(ex.getMessage());
        return response;
    }

}
