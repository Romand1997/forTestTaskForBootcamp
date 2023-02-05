package test_task_for_bootcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import test_task_for_bootcamp.model.Permission;

@Repository
@Transactional
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByName(String name);
}
