package com.sky.service;

import com.sky.dto.*;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    //新增员工
    void newEmplyee(EmployeeDTO employeeDTO);

    //员工分页查询
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    //启动禁用员工账号
    void setStatus(int status, long id);

    //查询员工信息
    Employee getById(long id);

    //更新员工信息
    void updateEmployee(EmployeeDTO employeeDTO);

    //修改密码
    void editPassword(PasswordEditDTO passwordEditDTO);
}
