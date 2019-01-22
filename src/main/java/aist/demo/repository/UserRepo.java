package aist.demo.repository;

import aist.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByLogin(String login);

    boolean existsByToken(String token);

    Set<User> findByLogin(String login);

    Set<User> findByToken(String token);

    @Modifying
    @Query("UPDATE User u SET u.token = :token WHERE u.login = :login")
    int updateTokenByLogin(@Param("token") String token, @Param("token") String login);

}
