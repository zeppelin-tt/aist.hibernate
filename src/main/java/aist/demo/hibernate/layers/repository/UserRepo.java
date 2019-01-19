package aist.demo.hibernate.layers.repository;

import aist.demo.hibernate.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByLogin(String login);

    boolean existsByToken(Integer token);

    User findOneByLogin(String login);

    @Modifying
    @Query("UPDATE users u SET u.token = :token WHERE u.login = :login")
    int updateTokenByLogin(@Param("token") Integer token, @Param("token") String login);

}
