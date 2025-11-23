package com.example.amazon.Service;

import com.example.amazon.Model.Category;
import com.example.amazon.Model.Merchant;
import com.example.amazon.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getCategory() {
        return categoryRepository.findAll();
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }


    public boolean updateCategory(Integer id, Category category) {
        Category oldCategory=categoryRepository.getById(id);
        if(oldCategory==null){
            return false;
        }
        oldCategory.setName(category.getName());
        categoryRepository.save(oldCategory);
        return true;

    }

    public boolean deleteCategory(Integer id) {
        Category category=categoryRepository.getById(id);

        if(category==null){
            return false;
        }

        categoryRepository.delete(category);
        return  true;
    }

    public boolean checkId(Integer id){
       return categoryRepository.existsById(id);
    }
}
