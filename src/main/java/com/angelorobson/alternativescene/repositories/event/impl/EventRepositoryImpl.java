package com.angelorobson.alternativescene.repositories.event.impl;

import com.angelorobson.alternativescene.entities.Event;
import com.angelorobson.alternativescene.entities.Event_;
import com.angelorobson.alternativescene.repositories.event.EventRepositoryQuery;
import com.angelorobson.alternativescene.repositories.event.filter.EventFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

public class EventRepositoryImpl implements EventRepositoryQuery {

    @Autowired
    private EntityManager manager;

    @Override
    public Page<Event> findAllByFilter(EventFilter eventFilter, Pageable page) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Event> criteria = builder.createQuery(Event.class);
        Root<Event> root = criteria.from(Event.class);

        Predicate[] predicates = createConstraints(eventFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Event> query = manager.createQuery(criteria);

        addPagingRestrictions(query, page);

        return new PageImpl<>(query.getResultList(), page, total(eventFilter));
    }

    private Predicate[] createConstraints(EventFilter eventFilter, CriteriaBuilder builder, Root<Event> root) {
        List<Predicate> predicates = new ArrayList<>();


        if (eventFilter.getStatus() != null) {
            if (!isEmpty(eventFilter.getStatus())) {
                predicates.add(
                        builder.equal(root.get(Event_.status), eventFilter.getStatus()));
            }
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void addPagingRestrictions(TypedQuery<?> query, Pageable pageRequest) {
        int currentPage = pageRequest.getPageNumber();
        int totalRecordsPerPage = pageRequest.getPageSize();
        int firstPageRegistration = currentPage * totalRecordsPerPage;

        query.setFirstResult(firstPageRegistration);
        query.setMaxResults(totalRecordsPerPage);
    }

    private Long total(EventFilter eventFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Event> root = criteria.from(Event.class);

        Predicate [] predicates = createConstraints(eventFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
