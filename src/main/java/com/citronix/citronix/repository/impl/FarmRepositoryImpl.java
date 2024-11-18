package com.citronix.citronix.repository.impl;

import com.citronix.citronix.domain.Farm;
import com.citronix.citronix.web.vm.Field.SearchDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FarmRepositoryImpl {
    private final EntityManager entityManager;

    public FarmRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    }
    public List<Farm> findByCriteria(SearchDTO searchDTO){
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Farm> criteriaQuery=criteriaBuilder.createQuery(Farm.class);
        Root<Farm> farmRoot=criteriaQuery.from(Farm.class);

        List<Predicate> predicateList=new ArrayList<>();
        if(searchDTO.getName()!=null && !searchDTO.getName().isEmpty())
            predicateList.add(criteriaBuilder.like(farmRoot.get("name"),"%"+searchDTO.getName()));
        if(searchDTO.getLocation()!=null && !searchDTO.getLocation().isEmpty())
            predicateList.add(criteriaBuilder.like(farmRoot.get("location"),"%"+searchDTO.getLocation()));

        criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[0])));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
