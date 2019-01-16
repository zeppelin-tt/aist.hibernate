package aist.demo.hibernate.layers.repository;

import aist.demo.hibernate.domain.entry.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
