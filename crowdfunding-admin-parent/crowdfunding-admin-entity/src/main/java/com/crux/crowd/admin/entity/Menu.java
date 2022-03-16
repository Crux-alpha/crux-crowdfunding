package com.crux.crowd.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @since 2022-03-16
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    private String name;
    private String url;
    private String icon;

    // 以下非数据库字段
    @TableField(exist = false)
    private List<Menu> children = new ArrayList<>();
    @TableField(exist = false)
    private volatile boolean open = true;



}
