package com.pea.service.masterservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

public interface MessageByLocaleService {
  String getMessage(String id);

  @Component
  class MessageByLocaleServiceImpl implements MessageByLocaleService {

    @Autowired private MessageSource messageSource;

    @Override
    public String getMessage(String id) {
      /*Locale locale = LocaleContextHolder.getLocale();*/
      Locale locale = new Locale("en", "US");
      return messageSource.getMessage(id, null, locale);
    }
  }
}
