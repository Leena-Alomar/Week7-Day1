package com.example.amazon.Controller;

import com.example.amazon.API.ApiResponse;
import com.example.amazon.Model.Merchantstock;
import com.example.amazon.Service.MerchantstockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchantstock")
@RequiredArgsConstructor
public class MerchantstockController {

    private final MerchantstockService merchantstockService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStock(){
        return ResponseEntity.status(200).body(merchantstockService.getMerchantstock());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock(@RequestBody @Valid Merchantstock merchantStock, Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        merchantstockService.addMerchantstock(merchantStock);
        return ResponseEntity.status(200).body(new ApiResponse("The Merchant Stock Is Added Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable Integer id,@RequestBody Merchantstock merchantStock,Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdated= merchantstockService.updateMerchant(id, merchantStock);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("The Merchant Stock Is Updated Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("The Merchant Stock Is Not Found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable Integer id){

        boolean isDeleted= merchantstockService.deleteMerchantstock(id);
        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("The Merchant Stock Is Deleted Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("The Merchant Stock Is Not Found"));
    }


    @PutMapping("/update/{id}/{productID}/{merchantID}/{stock}")
    public ResponseEntity<?> updateStock(@PathVariable  Integer id,@PathVariable Integer productID,@PathVariable Integer merchantID ,@PathVariable Integer stock){
        boolean isUpateStock= merchantstockService.updateMerchant(id,productID,merchantID,stock);
        if(isUpateStock){
            return ResponseEntity.status(200).body(new ApiResponse("The Merchant Stock Value Is Updated Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("The Merchant Stock Is Not Found"));
    }

    // Extra End Points ****************
    @GetMapping("/check")
    public ResponseEntity<?> CheckStock(){

        if( !merchantstockService.checkStock().isEmpty()){
            return ResponseEntity.status(200).body(merchantstockService.checkStock());
        }
        return ResponseEntity.status(400).body(new ApiResponse("There is no Availabe Products "));
    }
}
