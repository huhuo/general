package com.huhuo.integration.db.mysql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation for Model bean field which won't mapped as DB's table columns
 * @author root
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface NotSqlField {

	String key() default "";
	
	String value() default "";
	
}
