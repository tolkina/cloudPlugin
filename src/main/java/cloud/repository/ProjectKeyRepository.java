package cloud.repository;

import cloud.domain.ProjectKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectKeyRepository extends JpaRepository<ProjectKeyEntity, Long> {
}
