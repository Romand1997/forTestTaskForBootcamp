package test_task_for_bootcamp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import test_task_for_bootcamp.BaseEntity;

@Entity
@Table (name = "t_permissions")
@Getter
@Setter
public class Permission extends BaseEntity implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return name;
    }

    @Column (name = "name")
    private String name;
}
