package group.tarea.Persistence.Repository;

import group.tarea.Persistence.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUser(String user);

    User findByUser(String username);


    boolean existsByUser(String user);


}
