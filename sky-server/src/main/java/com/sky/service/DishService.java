package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;

public interface DishService {

    void newDish(DishDTO dishDTO);

    PageResult dishPageQuery(DishPageQueryDTO dishPageQueryDTO);
}
