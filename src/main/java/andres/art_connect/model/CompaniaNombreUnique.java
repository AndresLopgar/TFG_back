package andres.art_connect.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import andres.art_connect.service.CompaniaService;
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


/**
 * Validate that the nombre value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = CompaniaNombreUnique.CompaniaNombreUniqueValidator.class
)
public @interface CompaniaNombreUnique {

    String message() default "{Exists.compania.nombre}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class CompaniaNombreUniqueValidator implements ConstraintValidator<CompaniaNombreUnique, String> {

        private final CompaniaService companiaService;
        private final HttpServletRequest request;

        public CompaniaNombreUniqueValidator(final CompaniaService companiaService,
                final HttpServletRequest request) {
            this.companiaService = companiaService;
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
            if (currentId != null && value.equalsIgnoreCase(companiaService.get(Long.parseLong(currentId)).getNombre())) {
                // value hasn't changed
                return true;
            }
            return !companiaService.nombreExists(value);
        }

    }

}
