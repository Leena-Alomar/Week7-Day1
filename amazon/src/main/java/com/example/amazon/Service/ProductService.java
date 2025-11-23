package com.example.amazon.Service;

import com.example.amazon.Model.Product;
import com.example.amazon.Model.User;
import com.example.amazon.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryService categoryService;

    private final ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }


    public boolean updateProduct(Integer id, Product product) {
        Product oldProduct=productRepository.getById(id);
        if(oldProduct==null){
            return false;
        }
        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setCategoryID(product.getCategoryID());
        productRepository.save((oldProduct));
        return true;
    }

    public boolean deleteProduct(Integer id) {
        Product product=productRepository.getById(id);

        if(product==null){
            return false;
        }

        productRepository.delete(product);
        return  true;
    }


    public boolean checkId(Integer id){
        return productRepository.existsById(id);
    }

    public double getProcuctPriceByID(Integer id){
        List<Product> products = productRepository.findAll();

        boolean isProuductId=checkId(id);
        if(isProuductId){
            for(Product p:products){
                if(p.getId().equals(id)){
                    return p.getPrice();
                }
            }
        }
        return 0.0;
    }

    // Extra End Points ****************
    public ArrayList<Product> filterByProductPrice(double minPrice,double maxPrice,String lowesthighest){
        ArrayList<Product> priceRange=new ArrayList<>();
        List<Product> products = productRepository.findAll();
        ArrayList<Product> priceRangeRev=new ArrayList<>();
        for(Product p:products){
            if(p.getPrice()>=minPrice&&p.getPrice()<=maxPrice){
                priceRange.add(p);
            }
        }

        if(lowesthighest.equalsIgnoreCase("lowest")){
            priceRange.sort(Comparator.comparingDouble(Product::getPrice));
        } else if (lowesthighest.equalsIgnoreCase("highest")) {
            priceRange.sort(Comparator.comparingDouble(Product::getPrice).reversed());
        }
        return priceRange;

    }

    // Bounce End Points*******
    public ArrayList<Product> getProductBycategoryID(Integer cartegotyID){
        List<Product> products = productRepository.findAll();
        ArrayList<Product> productCatgory=new ArrayList<>();
        boolean isCategoryId= categoryService.checkId(cartegotyID);
        if(isCategoryId){
            for(Product p:products){
                if(p.getCategoryID().equals(cartegotyID)){
                    productCatgory.add(p);

                }
            }
        }
        return productCatgory;
    }

    public ArrayList<Product> getProductBynName(String name){
        List<Product> products = productRepository.findAll();
        ArrayList<Product> productByName=new ArrayList<>();
        for(Product p:products){
            if(p.getName().contains(name)){
                productByName.add(p);
            }
        }
        return productByName;
    }
}
