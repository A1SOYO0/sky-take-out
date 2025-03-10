package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {

    void newDish(DishDTO dishDTO);

    PageResult dishPageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);

    DishVO getDishById(Long id);

    void editDish(DishDTO dishDTO);

    void OnOrOff(int status, long id);

    List<Dish> getDishByCategoryId(Long categoryId);

    /**
     * 条件查询菜品和口味
     *
     *
     */
    List<DishVO> listWithFlavor(Long categoryId);

}
