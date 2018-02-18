package com.freelance.netanel.androidsearchapp.ioc_container;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Netanel on 05/02/2018.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchViewScope {
}
