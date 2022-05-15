package com.metaverse.hillside.work.repository;

import com.metaverse.hillside.core.repository.IBaseRepository;
import com.metaverse.hillside.work.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IUserRepository extends IBaseRepository<UserEntity, Long> {

    @Transactional(rollbackFor = Exception.class)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(nativeQuery = true, value = "update user set status = :status where id = :id")
    public void deleteSoftById(@Param(value = "id") Long id, @Param(value = "status") Integer status);

}
