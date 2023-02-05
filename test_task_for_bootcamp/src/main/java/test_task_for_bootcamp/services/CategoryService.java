package test_task_for_bootcamp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_task_for_bootcamp.model.Category;
import test_task_for_bootcamp.model.Item;
import test_task_for_bootcamp.repositories.CategoryRepository;

import java.util.List;
@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public List<Category> getAllCategories (){
        return categoryRepository.findAll();
    }
    public void addingCategory (){
            Category category = new Category();
            category.setName("Phone");
            Category category2 = new Category();
            category2.setName("Car");
            categoryRepository.save(category);
            categoryRepository.save(category2);
    }
}
