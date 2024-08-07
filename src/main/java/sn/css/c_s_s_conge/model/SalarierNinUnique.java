package sn.css.c_s_s_conge.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;
import sn.css.c_s_s_conge.service.SalarierService;


/**
 * Validate that the nin value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = SalarierNinUnique.SalarierNinUniqueValidator.class
)
public @interface SalarierNinUnique {

    String message() default "{Exists.salarier.nin}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class SalarierNinUniqueValidator implements ConstraintValidator<SalarierNinUnique, String> {

        private final SalarierService salarierService;
        private final HttpServletRequest request;

        public SalarierNinUniqueValidator(final SalarierService salarierService,
                final HttpServletRequest request) {
            this.salarierService = salarierService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equalsIgnoreCase(salarierService.get(Long.parseLong(currentId)).getNin())) {
                // value hasn't changed
                return true;
            }
            return !salarierService.ninExists(value);
        }

    }

}
