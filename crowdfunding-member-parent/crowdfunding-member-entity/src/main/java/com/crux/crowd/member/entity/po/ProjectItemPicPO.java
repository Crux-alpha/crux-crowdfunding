package com.crux.crowd.member.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 项目详情图片
 * @author 作者
 * @since 2022-04-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_project_item_pic")
public class ProjectItemPicPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer projectId;

    private String itemPicPath;

    public ProjectItemPicPO(Integer projectId, String itemPicPath){
        this(null, projectId, itemPicPath);
    }


}
