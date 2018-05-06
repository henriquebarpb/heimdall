
package br.com.conductor.heimdall.gateway.configuration;

/*-
 * =========================LICENSE_START==================================
 * heimdall-gateway
 * ========================================================================
 * Copyright (C) 2018 Conductor Tecnologia SA
 * ========================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ==========================LICENSE_END===================================
 */
 
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NoHttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.collect.Lists;
import com.netflix.zuul.exception.ZuulException;

import br.com.conductor.heimdall.core.exception.BadRequestException;
import br.com.conductor.heimdall.core.exception.ExceptionMessage;
import br.com.conductor.heimdall.core.exception.ForbiddenException;
import br.com.conductor.heimdall.core.exception.HeimdallException;
import br.com.conductor.heimdall.core.exception.NotFoundException;
import br.com.conductor.heimdall.core.exception.ServerErrorException;
import br.com.conductor.heimdall.core.exception.TimeoutException;
import br.com.conductor.heimdall.core.exception.UnauthorizedException;
import br.com.conductor.heimdall.core.util.UrlUtil;
import br.com.conductor.heimdall.gateway.configuration.GlobalExceptionHandler.BindExceptionInfo.BindError;
import br.com.twsoftware.alfred.object.Objeto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * This class captures the exceptions generated by the system and redirects them to the Heimdall custom exceptions.
 * 
 * @author Thiago Sampaio
 * @author Filipe Germano
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

     /**
      * Method that captures all the {@link NotFoundException} exceptions.
      * 
      * @param response
      * {@link HttpServletResponse}
      * @param request
      * {@link HttpServletRequest}
      * @param exception
      * {@link Exception}
      * @return {@link ErroInfo}
      */
     @ResponseStatus(HttpStatus.NOT_FOUND)
     @ExceptionHandler(NotFoundException.class)
     public @ResponseBody ErroInfo handleExceptionNotFound(HttpServletResponse response, HttpServletRequest request, Exception exception) {

          ErroInfo erroInfo = buildErrorInfo(request, exception);
          return erroInfo;

     }

     /**
      * Method that captures all the {@link ServerErrorException} exceptions.
      * 
      * @param response
      * {@link HttpServletResponse}
      * @param request
      * {@link HttpServletRequest}
      * @param exception
      * {@link Exception}
      * @return {@link ErroInfo}
      */
     @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
     @ExceptionHandler(ServerErrorException.class)
     public @ResponseBody ErroInfo handleExceptionServerError(HttpServletResponse response, HttpServletRequest request, Exception exception) {

          ErroInfo erroInfo = buildErrorInfo(request, exception);
          return erroInfo;

     }

     /**
      * Method that captures all the {@link ServerErrorException} exceptions.
      * 
      * @param response
      * {@link HttpServletResponse}
      * @param request
      * {@link HttpServletRequest}
      * @param exception
      * {@link Exception}
      * @return {@link ErroInfo}
      */
     @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
     @ExceptionHandler(Exception.class)
     public @ResponseBody ErroInfo handleException(HttpServletResponse response, HttpServletRequest request, Exception exception) {
          
          ErroInfo erroInfo = buildErrorInfoException(request, exception);
          log.error(exception.getMessage(), exception);
          return erroInfo;
          
     }

     /**
      * Method that captures all the {@link ServerErrorException} exceptions.
      * 
      * @param response
      * {@link HttpServletResponse}
      * @param request
      * {@link HttpServletRequest}
      * @param exception
      * {@link Exception}
      * @return {@link ErroInfo}
      */
     @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
     @ExceptionHandler(ZuulException.class)
     public @ResponseBody ErroInfo zuulExceptionServerError(HttpServletResponse response, HttpServletRequest request, ZuulException exception) {
          
          ErroInfo erroInfo = buildErrorInfoException(request, exception);
          log.error(exception.getMessage(), exception);
          return erroInfo;
          
     }

     /**
      * Method that captures all the {@link ServerErrorException} exceptions.
      * 
      * @param response
      * {@link HttpServletResponse}
      * @param request
      * {@link HttpServletRequest}
      * @param exception
      * {@link Exception}
      * @return {@link ErroInfo}
      */
     @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
     @ExceptionHandler(NoHttpResponseException.class)
     public @ResponseBody ErroInfo noHttpResponseExceptionServerError(HttpServletResponse response, HttpServletRequest request, NoHttpResponseException exception) {
          
          ErroInfo erroInfo = buildErrorInfoException(request, exception);
          log.error(exception.getMessage(), exception);
          return erroInfo;
          
     }

     /**
      * Method that captures all the {@link BadRequestException} exceptions.
      * 
      * @param response
      * {@link HttpServletResponse}
      * @param request
      * {@link HttpServletRequest}
      * @param exception
      * {@link Exception}
      * @return {@link ErroInfo}
      */
     @ResponseStatus(HttpStatus.BAD_REQUEST)
     @ExceptionHandler(BadRequestException.class)
     public @ResponseBody ErroInfo handleExceptionBadRequest(HttpServletResponse response, HttpServletRequest request, Exception exception) {

          ErroInfo erroInfo = buildErrorInfo(request, exception);
          return erroInfo;

     }
     
     /**
      * Method that captures all the {@link UnauthorizedException} exceptions.
      * 
      * @param response
      * {@link HttpServletResponse}
      * @param request
      * {@link HttpServletRequest}
      * @param exception
      * {@link Exception}
      * @return {@link ErroInfo}
      */
     @ResponseStatus(HttpStatus.UNAUTHORIZED)
     @ExceptionHandler(UnauthorizedException.class)
     public @ResponseBody ErroInfo handleExceptionUnauthorized(HttpServletResponse response, HttpServletRequest request, Exception exception) {

          ErroInfo erroInfo = buildErrorInfo(request, exception);
          return erroInfo;

     }
     
     /**
      * Method that captures all the {@link ForbiddenException} exceptions.
      * 
      * @param response
      * {@link HttpServletResponse}
      * @param request
      * {@link HttpServletRequest}
      * @param exception
      * {@link Exception}
      * @return {@link ErroInfo}
      */
     @ResponseStatus(HttpStatus.FORBIDDEN)
     @ExceptionHandler(ForbiddenException.class)
     public @ResponseBody ErroInfo handleExceptionForbidden(HttpServletResponse response, HttpServletRequest request, Exception exception) {
          
          ErroInfo erroInfo = buildErrorInfo(request, exception);
          return erroInfo;
          
     }
     
     /**
      * Method that captures all the {@link SocketTimeoutException} exceptions.
      * 
      * @param response
      * {@link HttpServletResponse}
      * @param request
      * {@link HttpServletRequest}
      * @param exception
      * {@link Exception}
      * @return {@link ErroInfo}.
      */
     @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
     @ExceptionHandler(SocketTimeoutException.class)
     public @ResponseBody ErroInfo handleExceptionTimeout(HttpServletResponse response, HttpServletRequest request, Exception exception) {

          TimeoutException exceptionPIER = new TimeoutException(ExceptionMessage.GLOBAL_TIMEOUT);
          ErroInfo erroInfo = new ErroInfo(LocalDateTime.now(), exceptionPIER.getMsgEnum().getHttpCode(), exceptionPIER.getClass().getSimpleName(), exceptionPIER.getMessage(), UrlUtil.getCurrentUrl(request));
          return erroInfo;

     }
     
     @Autowired
     private MessageSource messageSource;
     
     /**
      * Method that captures all the {@link BindExceptionInfo} exceptions.
      * 
      * @param response
      * {@link HttpServletResponse}
      * @param request
      * {@link HttpServletRequest}
      * @param exception
      * {@link BindExceptionInfo}
      * @return {@link BindExceptionInfo}
      */
     @ResponseStatus(HttpStatus.BAD_REQUEST)
     @ExceptionHandler(BindException.class)
     public @ResponseBody BindExceptionInfo validationBindException(HttpServletResponse response, HttpServletRequest request, BindException exception) {

          BindExceptionInfo bindException = new BindExceptionInfo();
          List<BindError> errors = Lists.newArrayList();
          List<ObjectError> objectsError = exception.getBindingResult().getAllErrors();

          objectsError.forEach(objectError -> {
               FieldError fieldError = (FieldError) objectError;
               
               String message = null;

               try {
                    
                    String code = fieldError.getCodes()[0];
                    message = messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
               } catch (Exception e) {
                    
                    message = null;
               }
               
               bindException.timestamp = LocalDateTime.now();
               bindException.status = 400;
               bindException.exception = "BindExceptionPIER";

               BindError error = bindException.new BindError();
               error.defaultMessage = Objeto.notBlank(message) ? message : fieldError.getDefaultMessage();
               error.objectName = fieldError.getObjectName();
               error.field = fieldError.getField();
               error.code = fieldError.getCode();

               errors.add(error);
          });
          
          bindException.erros = errors;

          return bindException;
     }

     /**
      * Method that captures all the {@link BindExceptionInfo} exceptions.
      * 
      * @param response
      * {@link HttpServletResponse}
      * @param request
      * {@link HttpServletRequest}
      * @param exception
      * {@link BindExceptionInfo}
      * @return {@link BindExceptionInfo}
      */
     @ResponseStatus(HttpStatus.BAD_REQUEST)
     @ExceptionHandler(MethodArgumentNotValidException.class)
     public @ResponseBody BindExceptionInfo validationMethodArgumentNotValidException(HttpServletResponse response, HttpServletRequest request, MethodArgumentNotValidException exception) {
          
          BindExceptionInfo bindException = new BindExceptionInfo();
          List<BindError> errors = Lists.newArrayList();
          List<ObjectError> objectsError = exception.getBindingResult().getAllErrors();
          
          objectsError.forEach(objectError -> {
               FieldError fieldError = (FieldError) objectError;
               
               bindException.timestamp = LocalDateTime.now();
               bindException.status = 400;
               bindException.exception = "MethodArgumentNotValidException";
               
               BindError error = bindException.new BindError();
               error.defaultMessage = fieldError.getDefaultMessage();
               error.objectName = fieldError.getObjectName();
               error.field = fieldError.getField();
               error.code = fieldError.getCode();
               
               errors.add(error);
          });
          
          bindException.erros = errors;
          
          return bindException;
     }

     /**
      * Method responsible to create the exception object.
      * 
      * @param request
      * {@link HttpServletRequest}
      * @param exception
      * {@link Exception}
      * @return {@link ErroInfo}
      */
     private ErroInfo buildErrorInfo(HttpServletRequest request, Exception exception) {

          HeimdallException exceptionPIER = (HeimdallException) exception;
          ErroInfo erroInfo = new ErroInfo(LocalDateTime.now(), exceptionPIER.getMsgEnum().getHttpCode(), exceptionPIER.getClass().getSimpleName(), exceptionPIER.getMessage(), UrlUtil.getCurrentUrl(request));
          return erroInfo;
     }

     /**
      * Method responsible to create the exception object.
      * 
      * @param request
      * {@link HttpServletRequest}
      * @param exception
      * {@link Exception}
      * @return {@link ErroInfo}
      */
     private ErroInfo buildErrorInfoException(HttpServletRequest request, Exception exception) {
          
          HeimdallException exceptionPIER = new HeimdallException(ExceptionMessage.GLOBAL_ERROR_ZUUL);
          ErroInfo erroInfo = new ErroInfo(LocalDateTime.now(), exceptionPIER.getMsgEnum().getHttpCode(), exceptionPIER.getClass().getSimpleName(), exceptionPIER.getMessage(), UrlUtil.getCurrentUrl(request));
          return erroInfo;
     }

     /**
      * Class that represents the return object used by all Heimdall Exceptions.
      * 
      * @author Thiago Sampaio
      *
      */
     @AllArgsConstructor
     @Getter
     public class ErroInfo {

          /**
           * TImestamp from the moment that the exception was created.
           */
          public LocalDateTime timestamp;

          /**
           * Exception identifier.
           */
          public Integer code;

          /**
           * Exception class name.
           */
          public String exception;

          /**
           * Exception description.
           */
          public String message;

          /**
           * Path that generated the request that caused the exception.
           */
          public String path;

     }

     /**
      * Class that represents the exceptions created by the Heimdall validations.
      * 
      * @author Filipe Germano
      *
      */     
     public class BindExceptionInfo {

          @Getter
          public LocalDateTime timestamp;

          @Getter
          public Integer status;

          @Getter
          public String exception;

          @Getter
          public List<BindError> erros;

          public class BindError {

               public String defaultMessage;

               public String objectName;

               public String field;

               public String code;
          }

     }

}
