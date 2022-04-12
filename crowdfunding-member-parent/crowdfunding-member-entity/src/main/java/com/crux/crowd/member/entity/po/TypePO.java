package com.crux.crowd.member.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * 分类表
 * @since 2022-04-07
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_type")
public class TypePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类介绍
     */
    private String remark;

    public TypePO(String name, String remark){
        this(null, name, remark);
    }

    @Override
    public final boolean equals(Object obj){
        if(this == obj) return true;
        if(!(obj instanceof TypePO)) return false;
        TypePO other = (TypePO)obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public final int hashCode(){
        return Objects.hash(id);
    }

    @Override
    public String toString(){
        return getClass().getSimpleName() +
                "{id=" + id +
                ", name=" + name +
                ", remark=" + remark +
                '}';
    }
}
