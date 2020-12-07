package org.owoto.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * @author zzfn
 * @date 2020-12-07 23:56
 */
@Data
public class BaseEntity {

    @TableId(value = "id",type= IdType.ASSIGN_UUID)
    private String id;

    @TableField(select = false)
    private String createBy;

    @TableField(select = false)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT,jdbcType= JdbcType.TIMESTAMP)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE,jdbcType= JdbcType.TIMESTAMP,select = false)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT,select = false)
    @TableLogic
    private Integer isDelete;
}
