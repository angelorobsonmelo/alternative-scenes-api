package com.angelorobson.alternativescene.repositories.event.impl;

import com.angelorobson.alternativescene.entities.*;
import com.angelorobson.alternativescene.enums.StatusEnum;
import com.angelorobson.alternativescene.repositories.FavoriteRepositoryQuery;
import com.angelorobson.alternativescene.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteRepositoryImpl implements FavoriteRepositoryQuery {

    private EntityManager manager;
    private Util util;

    @Autowired
    public FavoriteRepositoryImpl(EntityManager manager, Util util) {
        this.manager = manager;
        this.util = util;
    }

    @Override
    public Page<Favorite> findAllPagedByUser(UserApp userApp, Pageable page) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Favorite> criteria = builder.createQuery(Favorite.class);
        Root<Favorite> root = criteria.from(Favorite.class);

        Predicate[] predicates = createConstraints(userApp, builder, root);
        criteria.where(predicates);
        criteria.orderBy(builder.asc(root.get(Favorite_.ID)));

        TypedQuery<Favorite> query = manager.createQuery(criteria);
        util.addPagingRestrictions(query, page);

        return new PageImpl<>(query.getResultList(), page, total(userApp));
    }

    private Predicate[] createConstraints(UserApp userApp, CriteriaBuilder builder, Root<Favorite> root) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Favorite, Event> eventJoin = root.join(Favorite_.EVENT);

        if (userApp.getId() != null) {
            predicates.add(
                    builder.equal(root.get(Favorite_.USER_APP), userApp.getId()));

            predicates.add(builder.equal(eventJoin.get(Event_.status), StatusEnum.APPROVED));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private Long total(UserApp userApp) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Favorite> root = criteria.from(Favorite.class);

        Predicate[] predicates = createConstraints(userApp, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }


}
