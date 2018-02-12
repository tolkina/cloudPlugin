package cloud.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProjectKeyEntity {
    @Id
    private Long id;
    private String[] projectKeys;

    public ProjectKeyEntity(Long id, String[] projectKeys) {
        this.id = id;
        this.projectKeys = projectKeys;
    }

    public ProjectKeyEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getProjectKeys() {
        return projectKeys;
    }

    public void setProjectKeys(String[] projectKeys) {
        this.projectKeys = projectKeys;
    }
}
