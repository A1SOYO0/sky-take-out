package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品接口")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    //清理缓存
    private void CleanCache(String key) {
        log.info("已清理菜品缓存:{}",key);
        Set keys = redisTemplate.keys(key);
        redisTemplate.delete(keys);
    }

    @PostMapping
    @ApiOperation(value = "新增菜品")
    public Result<String> newDish(@RequestBody DishDTO  dishDTO) {

        dishService.newDish(dishDTO);
        String key = "dish_" +dishDTO.getCategoryId();
        CleanCache(key);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "菜品分页查询")
    public Result<PageResult> dishPageQuery(DishPageQueryDTO  dishPageQueryDTO) {
        log.info("菜品分页查询");
        PageResult pageResult = dishService.dishPageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation(value = "删除菜品")
    public Result deleteDish(@RequestParam List<Long> ids) {
        log.info("菜品批量删除：{}",ids);
        dishService.deleteBatch(ids);
        CleanCache("dish_*");
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("/根据id查询菜品")
    public Result<DishVO> getDishById(@PathVariable Long id){
        log.info("根据id查询菜品：{}",id);
        DishVO dishVO = dishService.getDishById(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("/修改菜品")
    public Result<String> editDish(@RequestBody DishDTO dishDTO){
        log.info("修改菜品:{}",dishDTO);
        dishService.editDish(dishDTO);
        CleanCache("dish_*");
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售、停售")
    public Result<String> OnOrOff(@PathVariable int status,long id){
        log.info("菜品停售起售：{},{}",status,id);
        dishService.OnOrOff(status,id);
        CleanCache("dish_*");
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("/根据分类ID查询菜品")
    public Result<List<Dish>> selectByCategoryId(@RequestParam long categoryId){
        log.info("根据分类ID查询菜品：{}",categoryId);
        List<Dish> list = dishService.getDishByCategoryId(categoryId);
        return Result.success(list);
    }
}
