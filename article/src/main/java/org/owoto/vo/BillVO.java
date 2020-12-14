package org.owoto.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.owoto.annotation.Dict;

import java.math.BigDecimal;

@Data
public class BillVO {
    private BigDecimal value;
    @Dict(target = "keepTypeDesc",codeType = "KEEP")
    private String keepType;
    @TableField(exist = false)
    private String keepTypeDesc;
    private String remark;
    private String time;
}
