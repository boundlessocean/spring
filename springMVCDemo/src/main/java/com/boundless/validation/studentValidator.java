package com.boundless.validation;

import com.boundless.model.student;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class studentValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return student.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        student s = (student) target;
        if (s.getName().length() < 3){
            errors.rejectValue("name","Length","名字长度大于3");
        }
    }
}
