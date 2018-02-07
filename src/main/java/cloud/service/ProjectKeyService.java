package cloud.service;

public interface ProjectKeyService {
    void createSubTaskPluginEntity(String[] projectKeys);

    void deleteSubTaskPluginEntity();

    String[] findSubTaskPluginEntity();
}
