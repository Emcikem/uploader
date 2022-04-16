package com.lyq.file.repository;

import com.lyq.file.dto.entity.FIlePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FilePoRepository extends JpaRepository<FIlePO, Integer> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "delete from tbl_file where identifier = ?", nativeQuery = true)
    int deleteByIdentifier(String identifier);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update tbl_file t set t.file_name = ?2 where t.identifier = ?1", nativeQuery = true)
    int reNameByIdentifier(@Param("identifier") String identifier, @Param("name") String name);


    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "delete from tbl_file where identifier in ?", nativeQuery = true)
    int deleteByIdentifiers(List<String> identifiers);

    /**
     * 加了nativeQuery = true后，sql为原生sql，from后写的是表名；
     * 不加nativeQuery = true时，sql语句中from写的是entity名称。
     */
}
