package com.crux.crowd.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * <p>
 * 
 * </p>
 *
 * @author 作者
 * @since 2022-03-09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ApiModel(value = "Admin对象", description = "")
public class Admin{

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String loginAcct;

    private String userPswd;

    private String userName;

    private String email;

    private String createTime;

    public Admin(String loginAcct, String userName, String userPswd, String email){
        this(null, loginAcct, userPswd, userName, email, null);
    }

    @Override
    public boolean equals(Object obj){
        if(super.equals(obj)) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Admin other = (Admin)obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

    @Override
    public String toString(){
        return "管理员{id:" + id + ',' +
                "昵称:" + loginAcct + ',' +
                "密码:" + userPswd + ',' +
                "姓名:" + userName + ',' +
                "邮箱:" + email + ',' +
                "注册时间:" + createTime + '}';

    }

}
