package com.example.amazon.Controller;

import com.example.amazon.API.ApiResponse;
import com.example.amazon.Model.Product;
import com.example.amazon.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<?> getProducts(){
        return ResponseEntity.status(200).body(productService.getProducts());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProducts(@RequestBody @Valid Product product, Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        productService.addProduct(product);
        return ResponseEntity.status(200).body(new ApiResponse("The Product Is Added Successfully"));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Product product, Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdated= productService.updateProduct(id,product);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("The Product Is Updated Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("The Product Is Not Found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id){
        boolean isDeleted= productService.deleteProduct(id);
        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("The Product Is Deleted Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("The Product Is Not Found"));
    }

    // Extra End Points ****************
    @GetMapping("/get/{minPrice}/{maxPrice}/{lowesthighest}")
    public ResponseEntity<?> filterByProductPrice(@PathVariable double minPrice,@PathVariable double maxPrice,@PathVariable String lowesthighest){
        if(!productService.filterByProductPrice(minPrice,maxPrice,lowesthighest).isEmpty()){
            return ResponseEntity.status(200).body(productService.filterByProductPrice(minPrice,maxPrice,lowesthighest));
        }
        return ResponseEntity.status(400).body(new ApiResponse("The Product Is Not Found"));
    }

    // Bounce End Points*******
    @GetMapping("/checkstock/{cartegotyID}")
    public ResponseEntity<?> getProductBycategoryID(@PathVariable Integer cartegotyID){
        if(!productService.getProductBycategoryID(cartegotyID).isEmpty()){

            return ResponseEntity.status(200).body(productService.getProductBycategoryID(cartegotyID));
        }
        return ResponseEntity.status(400).body(new ApiResponse("There is no Product Of Given Category"));
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable String name){
        if(!productService.getProductBynName(name).isEmpty()){

            return ResponseEntity.status(200).body(productService.getProductBynName(name));
        }
        return ResponseEntity.status(400).body(new ApiResponse("There is no Product Of Given Name"));
    }
}
