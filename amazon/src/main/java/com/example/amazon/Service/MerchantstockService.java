package com.example.amazon.Service;

import com.example.amazon.Model.Merchant;
import com.example.amazon.Model.Merchantstock;
import com.example.amazon.Model.Product;
import com.example.amazon.Repository.MerchantstockRepository;
import com.example.amazon.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantstockService {

    private final MerchantstockRepository merchantstockRepository;

    private final MerchantService merchantService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    public List<Merchantstock> getMerchantstock() {
        return merchantstockRepository.findAll();
    }

    public void addMerchantstock(Merchantstock merchantstock) {
        merchantstockRepository.save(merchantstock);
    }


    public boolean updateMerchant(Integer id, Merchantstock merchantstock) {
        Merchantstock oldMerchantstock=merchantstockRepository.getById(id);
        if(oldMerchantstock==null){
            return false;
        }
        oldMerchantstock.setMerchantID(merchantstock.getMerchantID());
        oldMerchantstock.setProductID(merchantstock.getProductID());
        oldMerchantstock.setStock(merchantstock.getStock());
        merchantstockRepository.save(oldMerchantstock);
        return true;

    }

    public boolean deleteMerchantstock(Integer id) {
        Merchantstock merchantstock=merchantstockRepository.getById(id);

        if(merchantstock==null){
            return false;
        }

        merchantstockRepository.delete(merchantstock);
        return  true;
    }


    public boolean updateStock(Integer id,Integer productID,Integer merchantID ,Integer stock){
        ArrayList<Merchantstock> merchantStocks=new ArrayList<>(merchantstockRepository.findAll());
        boolean isProductId= productService.checkId(productID);
        boolean isMerchantId=merchantService.checkId(merchantID);
        if(isProductId&&isMerchantId){
            for(Merchantstock m:merchantStocks){
                if(m.getProductID().equals(productID)&&m.getMerchantID().equals(merchantID)){
                    int newStock=stock+m.getStock();
                    m.setStock(newStock);
                    merchantstockRepository.save(m);
                    return true;
                }
            }
        }
        return false;
    }


    public ArrayList<Product> checkStock( ){
        ArrayList<Product> products1=new ArrayList<>(productRepository.findAll());
        ArrayList<Product> availabeProucdt=new ArrayList<>();
        ArrayList<Merchantstock> merchantStocks=new ArrayList<>(merchantstockRepository.findAll());

        for(Merchantstock m:merchantStocks){
            if(m.getStock()>0)
                for(Product p:products1){
                    if(p.getId().equals(m.getProductID())){
                        availabeProucdt.add(p);
                    }
                }

        }

        return availabeProucdt;
    }
}
