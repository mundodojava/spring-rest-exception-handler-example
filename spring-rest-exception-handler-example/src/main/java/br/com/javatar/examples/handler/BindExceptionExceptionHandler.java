/*
 * Copyright 2014 Jakub Jirutka <jakub@jirutka.cz>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.javatar.examples.handler;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import cz.jirutka.spring.exhandler.handlers.ErrorMessageRestExceptionHandler;
import cz.jirutka.spring.exhandler.messages.ErrorMessage;
import cz.jirutka.spring.exhandler.messages.ValidationErrorMessage;

public class BindExceptionExceptionHandler extends ErrorMessageRestExceptionHandler<BindException> {

    private ConversionService conversionService = new DefaultConversionService();

    public BindExceptionExceptionHandler() {
        super(UNPROCESSABLE_ENTITY);
    }

    @Override
    public ValidationErrorMessage createBody(BindException ex, HttpServletRequest req) {

        ErrorMessage tmpl = super.createBody(ex, req);
        ValidationErrorMessage msg = new ValidationErrorMessage(tmpl);

        for (FieldError violation : ex.getFieldErrors()) {
            String pathNode = violation.getField();

            // path is probably useful only for properties (fields)
            msg.addError(pathNode, convertToString(violation.getRejectedValue()), violation.getDefaultMessage());
        }
        
        return msg;
    }

    /**
     * Conversion service used for converting an invalid value to String. When no service provided, the {@link DefaultConversionService} is used.
     *
     * @param conversionService
     *            must not be null.
     */
    @Autowired(required = false)
    public void setConversionService(ConversionService conversionService) {
        Assert.notNull(conversionService, "conversionService must not be null");
        this.conversionService = conversionService;
    }

    private String convertToString(Object invalidValue) {

        if (invalidValue == null) {
            return null;
        }
        try {
            return conversionService.convert(invalidValue, String.class);

        } catch (ConversionException ex) {
            return invalidValue.toString();
        }
    }

}
