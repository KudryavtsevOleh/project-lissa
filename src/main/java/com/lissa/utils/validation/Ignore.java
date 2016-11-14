package com.lissa.utils.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used by any {@link com.lissa.storage.Storage} implementation to identify fields
 * of {@link com.lissa.objects.Entity} that need to be avoided from being stored
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Ignore {
}
