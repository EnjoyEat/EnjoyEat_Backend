package com.example.EnjoyEat.Repository;

import com.example.EnjoyEat.Model.ShopAdd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<ShopAdd,Long> {
}
