package com.language.service.repo.group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.language.service.domain.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupRepo extends JpaRepository<Group, Long>, GroupRepoCustom {

    @Query("select g from Group g left join fetch g.allowedMenus where g.id = :id")
    Optional<Group> findByIdWithAllowedMenus(@Param("id") long id);

    Optional<Group> findByCode(String code);


    @Query(" from Group g where g.name like :name or :name is null and g.deleted = :deleted")
    Page<Group> searchAll(String name, Pageable p, int deleted);

    @Query(" from Group g where (g.code like :code escape '\\' or :code is null) and (g.name like :name or :name is null) and g.deleted = :deleted")
    Page<Group> searchByFilter(String code, String name, int deleted ,Pageable p);

    @Query(" from Group g where (g.code like :quickSearch escape '\\' or g.name like :quickSearch escape '\\' or :quickSearch is null) and g.deleted = :deleted")
    Page<Group> quickSearchGroup(String quickSearch, int deleted, Pageable p);

    @Query(" from Group g where (g.code = :code or g.name =:name)")
    Optional<Group> getGroupUserByCodeOrName(String code, String name);

    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END " +
            "FROM Group g WHERE g.deleted = :deleted AND g.id <> :id " +
            "AND (:code IS NULL OR g.code = :code) " +
            "AND (:name IS NULL OR g.name = :name)")
    boolean getGroupUserByCodeOrNameAndIdNotEquasl(Long id, String code, String name, Integer deleted);

    /**
     * Lấy toàn bộ danh sách {@link Group}*/
    @Query(" from Group g where g.deleted = :deleted")
    List<Group> getAllGroupsByDeleted(@Param("deleted") int deleted);

    @Query("""
    FROM Group g
    JOIN UserGroupMap ugm on g.id = ugm.groupId
    WHERE ugm.userId = :id
    AND ugm.deleted = :deleted
""")
    List<Group> findGroupByUserId(@Param("id") Long id, @Param("deleted") int deleted);

    @Query("select g from Group g where g.id = :id and g.deleted = :deleted")
    Optional<Group> findGroupById(@Param("id") Long id,
                                  @Param("deleted") int deleted);
}
