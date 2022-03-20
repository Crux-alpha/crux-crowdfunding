package com.crux.crowd.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Auth implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Integer id;
	private String name;
	private String title;
	private Integer categoryId;

	public Auth(String name, String title, Integer categoryId){
		this(null, name, title, categoryId);
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		Auth auth = (Auth)obj;
		return Objects.equals(id, auth.id);
	}

	@Override
	public int hashCode(){
		return Objects.hash(id);
	}

	@Override
	public String toString(){
		return "权限{" +
				"id:" + id +
				", name:" + name +
				", title:" + title +
				", categoryId:" + categoryId +
				'}';
	}
}
