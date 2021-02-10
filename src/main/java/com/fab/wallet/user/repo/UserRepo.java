package com.fab.wallet.user.repo;

import com.fab.wallet.user.entities.UserTbl;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<UserTbl,String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("from UserTbl u where u.userId=:userId")
    Optional<UserTbl> findByIdWithLock(@Param("userId") String userId);
}
