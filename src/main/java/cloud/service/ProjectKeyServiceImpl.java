package cloud.service;

import cloud.domain.ProjectKeyEntity;
import cloud.repository.ProjectKeyRepository;

public class ProjectKeyServiceImpl implements ProjectKeyService {
    private final static Long ENTITY_ID = 1L;
    private ProjectKeyRepository projectKeyRepository;

    public ProjectKeyServiceImpl(ProjectKeyRepository projectKeyRepository) {
        this.projectKeyRepository = projectKeyRepository;
    }

    @Override
    public void createSubTaskPluginEntity(String[] projectKeys) {
        ProjectKeyEntity projectKeyEntity = new ProjectKeyEntity(ENTITY_ID, projectKeys);
        projectKeyRepository.save(projectKeyEntity);
    }

    @Override
    public void deleteSubTaskPluginEntity() {
        projectKeyRepository.delete(ENTITY_ID);
    }

    @Override
    public String[] findSubTaskPluginEntity() {
        return projectKeyRepository.findOne(ENTITY_ID).getProjectKeys();
    }
}
