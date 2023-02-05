package test_task_for_bootcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import test_task_for_bootcamp.model.Category;
import test_task_for_bootcamp.model.Item;

import java.util.List;

@Repository
@Transactional
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByIsActiveOrderByNameAsc(Boolean isActive);

    List<Item> findAllByIsActiveAndCategoryOrderByNameAsc(Boolean isActive, Category category);

}

