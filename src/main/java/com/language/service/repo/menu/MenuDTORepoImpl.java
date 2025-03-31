package com.language.service.repo.menu;

import com.language.service.common.Constants;
import com.language.service.rest.dto.request.searchparams.MenuSearchParams;
import com.language.service.domain.dtos.MenuCodeDTO;
import com.language.service.domain.dtos.MenuDTO;
import com.language.service.domain.entities.Menu;
import com.language.service.domain.entities.Menu_;
import com.language.service.repo.DynamicSearchRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import java.util.ArrayList;
import java.util.List;

@Component
public class MenuDTORepoImpl extends DynamicSearchRepo<MenuDTO, MenuSearchParams, Menu> implements MenuDTORepo {

    private static final Logger logger = LoggerFactory.getLogger(MenuDTORepoImpl.class);

    @Autowired
    public MenuDTORepoImpl(EntityManager em) {
        super(em, MenuDTO.class, Menu.class);
    }


    @Override
    public List<Predicate> getPredicates(CriteriaBuilder cb, Root<Menu> root, MenuSearchParams params) {
        List<Predicate> predicates = new ArrayList<>();
        String code = params.getCode();
        if (code != null) {
            predicates.add(cb.like(root.get(Menu_.CODE), "%" + code + "%"));
        }
        return predicates;
    }

    @Override
    public Selection[] getSelections(Root<Menu> root) {
        return new Selection[]{
                root.get(Menu_.ID),
                root.get(Menu_.CODE),
                root.get(Menu_.ORDER),
                root.get(Menu_.LEVEL),
                root.get(Menu_.PARENT_CODE),
                root.get(Menu_.DESCRIPTION)
        };
    }

    @Override
    public List<MenuDTO> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MenuDTO> cq = cb.createQuery(MenuDTO.class);
        Root<Menu> root = cq.from(Menu.class);

        Selection[] selections = getSelections(root);

        cq.select(cb.construct(MenuDTO.class, selections));
        cq.orderBy(
                cb.asc(root.get(Menu_.ORDER)),
                cb.asc(root.get(Menu_.LEVEL)),
                cb.asc(root.get(Menu_.PARENT_CODE))
        );
        TypedQuery<MenuDTO> typeQuery = em.createQuery(cq);

        return typeQuery.getResultList();
    }

    @Override
    public List<String> getMenuCodesByUser(long userId) {
        TypedQuery<MenuCodeDTO> query = em.createNamedQuery("getMenuCodesByUser", MenuCodeDTO.class);
        query.setParameter("userId", userId);
        query.setParameter("deleted", Constants.DELETE.INACTIVE);
        List<MenuCodeDTO> menuCodeDTOList = query.getResultList();
        final List<String> codes = new ArrayList<>();
        if (menuCodeDTOList != null && menuCodeDTOList.size() > 0){
            menuCodeDTOList.forEach(m -> codes.add(m.getCode()));
        }
        return codes;
    }


}
