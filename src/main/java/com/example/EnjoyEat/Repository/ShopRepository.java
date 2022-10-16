package com.example.EnjoyEat.Repository;

import com.example.EnjoyEat.Model.ShopAdd;
import com.example.EnjoyEat.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<ShopAdd,Long> {
    ShopAdd findByUserAndShopId(User user, Long shopId);
}
