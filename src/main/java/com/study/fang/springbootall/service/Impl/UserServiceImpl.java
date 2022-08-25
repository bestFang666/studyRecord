package com.study.fang.springbootall.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.fang.springbootall.mapper.UserMapper;
import com.study.fang.springbootall.pojo.User;
import com.study.fang.springbootall.service.UserService;
import org.springframework.stereotype.Service;

/**
 *
 * @author bestFang666
 * @date 2022/8/24 21:35
 * 继承了 ServiceImpl<Mapper,pojo> 就无需编写实现接口的方法
 * 注意：先继承后实现
 *      Mapper 一定要继承 baseMapper，要不然会报错
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
