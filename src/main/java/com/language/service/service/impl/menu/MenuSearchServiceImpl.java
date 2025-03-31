package com.language.service.service.impl.menu;

import com.language.service.rest.dto.request.searchparams.MenuSearchParams;
import com.language.service.domain.dtos.MenuDTO;
import com.language.service.repo.menu.MenuRepo;
import com.language.service.service.impl.base.BaseSearchServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.service.abs.menu.MenuSearchService;


@Service
@Transactional
public class MenuSearchServiceImpl extends BaseSearchServiceImpl<MenuDTO, MenuSearchParams> implements MenuSearchService {

    public MenuSearchServiceImpl(MenuRepo menuRepo) {
        super(menuRepo);
    }

}
