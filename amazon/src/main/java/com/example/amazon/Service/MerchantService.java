package com.example.amazon.Service;

import com.example.amazon.Model.Merchant;
import com.example.amazon.Model.Product;
import com.example.amazon.Repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public List<Merchant> getMerchant() {
        return merchantRepository.findAll();
    }

    public void addMerchant(Merchant merchant) {
        merchantRepository.save(merchant);
    }


    public boolean updateMerchant(Integer id, Merchant merchant) {
        Merchant oldMerchant=merchantRepository.getById(id);
        if(oldMerchant==null){
            return false;
        }
        oldMerchant.setName(merchant.getName());
        merchantRepository.save(oldMerchant);
        return true;

    }

    public boolean deleteMerchant(Integer id) {
        Merchant merchant=merchantRepository.getById(id);

        if(merchant==null){
            return false;
        }

        merchantRepository.delete(merchant);
        return  true;
    }

    public boolean checkId(Integer id){
        return merchantRepository.existsById(id);
    }
}
