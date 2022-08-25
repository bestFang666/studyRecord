package com.study.fang.springbootall.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 *
 * @author bestFang666
 * @date 2022/8/24 16:35
 */
@Data
@TableName("user")
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
