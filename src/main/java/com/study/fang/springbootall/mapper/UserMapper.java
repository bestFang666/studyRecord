package com.study.fang.springbootall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.fang.springbootall.pojo.User;

/**
 *
 * @author bestFang666
 * @date 2022/8/24 21:35
 * Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得 CRUD 功能
 */
public interface UserMapper extends BaseMapper<User> {
}
