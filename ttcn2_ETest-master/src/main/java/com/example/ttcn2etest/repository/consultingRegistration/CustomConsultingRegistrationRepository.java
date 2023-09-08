package com.example.ttcn2etest.repository.consultingRegistration;

import com.example.ttcn2etest.model.etity.ConsultingRegistration;
import com.example.ttcn2etest.request.consultingRegistration.FilterConsultingRegistrationRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Repository
public class CustomConsultingRegistrationRepository {
    public static Specification<ConsultingRegistration> filterSpecification(Date dateFrom, Date dateTo,
                                                                            FilterConsultingRegistrationRequest request){
        return ((((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (dateFrom != null && dateTo != null) {
                predicates.add(criteriaBuilder.between(root.get("createdDate"), dateFrom, dateTo));
            }
            if (StringUtils.hasText(request.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
            }
            if (StringUtils.hasText(request.getPhone())) {
                predicates.add(criteriaBuilder.like(root.get("phone"), "%" + request.getPhone() + "%"));
            }
            if (StringUtils.hasText(request.getEmail())) {
                predicates.add(criteriaBuilder.equal(root.get("email"), request.getEmail()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        })));
    }
}
