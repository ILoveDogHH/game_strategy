package jedi.game.db;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface DatabaseTableAnnotation {
	String name();

	int type();

	String[] columns();

	Class<?> mapperClass();


}