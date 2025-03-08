package com.sky.service;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    void newSetmeal(SetmealDTO setmealDTO);

    PageResult setmealPageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void deleteSetmeal(List<Long> ids);

    void OnOrOff(int status, long id);

    void editSetmeal(SetmealDTO setmealDTO);

    SetmealVO selectById(Long id);
}
