package com.crux.crowd.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * 角色实体类
 *
 * @since 2022-03-14
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_role")
public class Role implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    public Role(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Role role = (Role)obj;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

    @Override
    public String toString(){
        return "角色{id:" + id + ','
                + "name:" + name + '}';
    }
}
