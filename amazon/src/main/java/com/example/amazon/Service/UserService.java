package com.example.amazon.Service;

import com.example.amazon.Model.Merchantstock;
import com.example.amazon.Model.Product;
import com.example.amazon.Model.User;
import com.example.amazon.Repository.MerchantRepository;
import com.example.amazon.Repository.MerchantstockRepository;
import com.example.amazon.Repository.ProductRepository;
import com.example.amazon.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final MerchantstockRepository merchantstockRepository;

    private final MerchantstockService merchantStockService;
    private final MerchantService merchantService;
    private final ProductService productService;

    ArrayList<User> users = new ArrayList<>();
    ArrayList<Product> wishlist=new ArrayList<>();
    private final MerchantRepository merchantRepository;
    private final ProductRepository productRepository;


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }


    public boolean updateUser(Integer id, User user) {
        User oldUser=userRepository.getById(id);
        if(oldUser==null){
            return false;
        }
        oldUser.setUserName(user.getUserName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setBalance(user.getBalance());
        oldUser.setRole(user.getRole());

        userRepository.save((oldUser));
        return true;
    }

    public boolean deleteUser(Integer id) {
        User user=userRepository.getById(id);

        if(user==null){
            return false;
        }

        userRepository.delete(user);
        return  true;
    }


    public boolean BuyProduct(Integer id, Integer productID, Integer merchantID) {
        ArrayList<Merchantstock> merchantStocks =new ArrayList<>(merchantstockRepository.findAll());
        boolean isProductId = productService.checkId(productID);
        boolean isMerchantId = merchantService.checkId(merchantID);

        if (!isProductId || !isMerchantId) {
            return false;
        }

        for (User u:users) {

            if (u.getId().equals(id)) {

                for(Merchantstock m:merchantStocks){

                    if(m.getStock()>0&&m.getProductID().equals(productID)&&m.getMerchantID().equals(merchantID)){
                        double price= productService.getProcuctPriceByID(productID);

                        if(u.getBalance()>=price){
                            int newstock=m.getStock()-1;
                            m.setStock(newstock);
                            merchantstockRepository.save(m);
                            u.setBalance((int) (u.getBalance()-price));
                            userRepository.save(u);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // Extra End Points ****************
    public boolean transferToUserById(Integer id,String email,double amount){
        for(User u:users){
            if(u.getId().equals(id)&&u.getBalance()>=amount){
                for(User e:users){
                    if(e.getEmail().equalsIgnoreCase(email)){
                        u.setBalance((int) (u.getBalance()-amount));
                        userRepository.save(u);
                        e.setBalance((int) (e.getBalance()+amount));
                        userRepository.save(e);
                        return true;
                    }
                }
            }
        }
        return false;
    }


    // Extra End Points ****************
    public ArrayList<Product> addProductToWishlist(Integer id,Integer proudctID){
        ArrayList<Product> products=new ArrayList<>(productRepository.findAll());

        for(Product w:wishlist){
            if(w.getId().equals(proudctID)){
                return wishlist;
            }
        }
        for(Product p:products) {
            if (p.getId().equals(proudctID)) {
                wishlist.add(p);
            }
        }
        return wishlist;
    }

    public boolean checkId(Integer id){
        return userRepository.existsById(id);
    }

    // Extra End Points ****************
    public ArrayList<Product> getRecommenedProdcut(Integer id){
        ArrayList<Product> products=new ArrayList<>(productRepository.findAll());
        ArrayList<Product> recomended=new ArrayList<>();
        boolean isUser=checkId(id);
        double max=wishlist.get(0).getPrice();
        double min=wishlist.get(0).getPrice();
        String maxcate="";

        if(isUser){
            for(int i=0;i< wishlist.size();i++){
                if(wishlist.get(i).getPrice()>max){
                    max=wishlist.get(i).getPrice();

                }
                if(wishlist.get(i).getPrice()<min){
                    min=wishlist.get(i).getPrice();
                }
            }

            int countMax=0;
            int count=0;
            for(int i=0;i<wishlist.size();i++){
                String catid=wishlist.get(i).getCategoryID();
                for(int j=0;j<wishlist.size();j++)
                    if(wishlist.get(j).getCategoryID().equals(catid)){
                        count++;
                        maxcate=catid;
                    }

            }
            if(count>countMax){
                countMax=count;
                for(Product p:products){
                    if(p.getPrice()>=min&&p.getPrice()<=max&&p.getCategoryID().equals(maxcate)){
                        recomended.add(p);
                    }
                }
            }
        }

        return recomended;
    }
}
