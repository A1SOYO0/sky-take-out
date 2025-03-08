package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "套餐接口")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    @ApiOperation("新增套餐")
    public Result<String> newSetmeal(@RequestBody SetmealDTO setmealDTO) {
        setmealService.newSetmeal(setmealDTO);
        return Result.success();
    }

    @GetMapping("page")
    @ApiOperation("分页查询")
    public Result<PageResult> setmealPageQuery(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("套餐分页查询");
        PageResult pageResult = setmealService.setmealPageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result<String> deleteSetmeal(@RequestParam List<Long> ids){
        log.info("菜品批量删除：{}",ids);
        setmealService.deleteSetmeal(ids);

        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("启售停售套餐")
    public Result<String> OnOrOff(@PathVariable int status,@RequestParam long id){
        log.info("启售停售套餐：{}",id);
        setmealService.OnOrOff(status,id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("修改套餐")
    public Result<String> editSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("修改套餐：{}",setmealDTO);
        setmealService.editSetmeal(setmealDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<SetmealVO> selectById(@PathVariable Long id){
        log.info("根据id查询套餐：{}",id);
        SetmealVO setmealVO = new SetmealVO();
        setmealVO = setmealService.selectById(id);
        return Result.success(setmealVO);
    }
}
