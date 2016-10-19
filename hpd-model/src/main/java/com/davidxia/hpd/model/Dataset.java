package com.davidxia.hpd.model;

import java.time.YearMonth;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface Dataset {

  Type type();

  short year();

  short month();

  enum Type {
    BUILDING("building"),
    CHARGE("charge"),
    COMPLAINT("complaint"),
    LITIGATION("litigation"),
    REGISTRATION("registration"),
    VIOLATION("violation");

    private final String name;

    Type(final String name) {
      this.name = name;
    }
  }
}
