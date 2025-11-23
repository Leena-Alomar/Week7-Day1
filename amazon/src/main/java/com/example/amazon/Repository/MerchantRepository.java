package com.example.amazon.Repository;

import com.example.amazon.Model.Merchant;
import com.example.amazon.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant,Integer> {
}
