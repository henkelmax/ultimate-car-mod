package de.maxhenkel.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ReflectionTools {

    public static boolean hasAnnotation(Field field, Class<? extends Annotation> annotationClass) {
        for (Annotation annotation : field.getAnnotations()) {
            if (annotation.annotationType().equals(annotationClass)) {
                return true;
            }
        }
        return false;
    }

}
