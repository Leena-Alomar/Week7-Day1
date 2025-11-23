package com.example.amazon.Controller;

import com.example.amazon.API.ApiResponse;
import com.example.amazon.Model.Merchant;
import com.example.amazon.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchant(){
        return ResponseEntity.status(200).body(merchantService.getMerchant());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        merchantService.addMerchant(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("The Merchant Is Added Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable Integer id,@RequestBody Merchant merchant,Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdated= merchantService.updateMerchant(id, merchant);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("The Merchant Is Updated Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("The Merchant Is Not Found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable Integer id){

        boolean isDeleted= merchantService.deleteMerchant(id);
        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("The Merchant Is Deleted Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("The Merchant Is Not Found"));
    }
}
