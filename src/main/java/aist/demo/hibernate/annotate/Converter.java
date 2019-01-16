package aist.demo.hibernate.annotate;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public @interface Converter {
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";
}
