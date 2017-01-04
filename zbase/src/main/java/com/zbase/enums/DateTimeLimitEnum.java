package com.zbase.enums;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/6
 * 描述：日期时间选择限制，ALL没限制，NO_BEFORE限制不能是之前的时间，NO_AFTER限制不能是之后的时间
 * NO_BEFORE_DEFAULT限制不能是之前的时间并且还原到默认时间，NO_AFTER_DEFAULT限制不能是之后的时间并且还原到默认时间
 */
public enum DateTimeLimitEnum {
    ALL, NO_BEFORE, NO_AFTER, NO_BEFORE_DEFAULT, NO_AFTER_DEFAULT
}
