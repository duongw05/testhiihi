package com.language.service.repo.dao.functioncatalog;

import java.util.List;

public interface FunctionCatalogCustomRepo {

    boolean hasPermission(long userId, String permissionCode);
    List<String> allPermCode(long userId);


}
