package com.smartosc.fintech.lms.repository.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseSpecification {
    public static <T> Specification<T> where(Specification specification, Specification<T> condition) {
        return specification == null ? Specification.where(condition) :
                specification.and(condition);
    }
}
