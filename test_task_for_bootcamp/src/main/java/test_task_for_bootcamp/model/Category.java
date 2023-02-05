package test_task_for_bootcamp.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import test_task_for_bootcamp.BaseEntity;

import java.util.prefs.BackingStoreException;
@Entity
@Getter
@Setter
@Table (name = "t_categories")
public class Category extends BaseEntity {
    private String name;
}

