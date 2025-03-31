package com.language.service.service.impl.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.language.service.repo.BaseSearchRepo;
import com.language.service.service.abs.base.BaseSearchService;

public abstract class BaseSearchServiceImpl<LI, SP> implements BaseSearchService<LI, SP> {

    private final BaseSearchRepo<LI, SP> baseSearchRepo;

    public BaseSearchServiceImpl(BaseSearchRepo<LI, SP> baseSearchRepo) {
        this.baseSearchRepo = baseSearchRepo;
    }

    @Override
    public Page<LI> search(SP params, Pageable pageable) {
        return baseSearchRepo.search(params, pageable);
    }

}
