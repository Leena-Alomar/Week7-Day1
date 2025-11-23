package com.example.amazon.Controller;

import com.example.amazon.API.ApiResponse;
import com.example.amazon.Model.Category;
import com.example.amazon.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<?> getCategory(){
        return ResponseEntity.status(200).body(categoryService.getCategory());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        categoryService.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("The Category Is Added Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody Category category, Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdated= categoryService.updateCategory(id, category);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("The Category Is Updated Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("The Category Is Not Found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id){

        boolean isDeleted= categoryService.deleteCategory(id);
        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("The Category Is Deleted Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("The Category Is Not Found"));
    }
}
