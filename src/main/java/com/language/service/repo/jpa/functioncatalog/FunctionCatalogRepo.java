package com.language.service.repo.jpa.functioncatalog;

import com.language.service.domain.entities.FunctionCatalog;
import com.language.service.domain.entities.Group;
import com.language.service.domain.entities.UserGroupMap;
import com.language.service.repo.dao.functioncatalog.FunctionCatalogCustomRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FunctionCatalogRepo extends JpaRepository<FunctionCatalog, Long>, FunctionCatalogCustomRepo {

    @Query("from FunctionCatalog f where (f.functionCode like :functionCode or :functionCode is null) " +
            "and (f.functionName like :functionName or :functionName is null) " +
            "and f.status = :status " +
            "and f.deleted = :deleted order by f.id desc")
    Page<FunctionCatalog> findByFunctionCodeAndFunctionName(String functionCode, String functionName, Integer status, Integer deleted, Pageable p);
    @Query("SELECT fc FROM FunctionCatalog fc WHERE fc.parentId IS NULL AND fc.deleted = 0")
    List<FunctionCatalog> findTopLevelFunctions();

    @Query("SELECT fc FROM FunctionCatalog fc WHERE fc.parentId = :parentId AND fc.deleted = 0")
    List<FunctionCatalog> findChildrenByParentId(Long parentId);

    @Query("SELECT fc FROM FunctionCatalog fc WHERE fc.id = :id AND fc.deleted = 0")
    FunctionCatalog findByIdAndNotDeleted(Long id);

    @Query("SELECT fc.functionCode FROM FunctionCatalog fc WHERE fc.id = :parentId AND fc.deleted = 0")
    String findParentFunctionCode(Long parentId);

}
