package com.ultils.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModSpecificationsBuilder<T> {
    private final List<SearchCriteria> params;

    public ModSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public ModSpecificationsBuilder with(String column, String expression, String keySearch) {
        params.add(new SearchCriteria(column, expression, keySearch));
        return this;
    }

    public Specification<T> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(ModelSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

//        build for case or
//        for (int i = 1; i < params.size(); i++) {
//            result = params.get(i).isOrPredicate()
//                    ? Specification.where(result).or(specs.get(i))
//                    : Specification.where(result).and(specs.get(i));
//        }
        return result;
    }
}
