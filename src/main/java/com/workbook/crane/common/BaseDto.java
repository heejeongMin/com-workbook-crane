package com.workbook.crane.common;

public abstract class BaseDto<T> {
  public abstract T toEntity();
}