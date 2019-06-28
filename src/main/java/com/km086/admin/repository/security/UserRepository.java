package com.km086.admin.repository.security;

import com.km086.admin.model.security.User;
import com.km086.admin.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long>, UserRepositoryCustom {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u where u.loginName = :loginName")
    Boolean existsByLoginName(@Param("loginName") String paramString);

    @Query("select u from User u where u.loginName = :loginName")
    Optional<User> findByLoginName(@Param("loginName") String paramString);

    @Query("select u from User u where u.name like %:name%")
    List<User> findByName(@Param("name") String paramString);
}
