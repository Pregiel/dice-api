package pl.pregiel.diceapi.diceapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pregiel.diceapi.diceapi.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findById(int id);
}
