package org.owoto.vo;

import lombok.Data;

/**
 * @author zzf
 */
@Data
public class PageVO {
    private Integer pageNumber;
    private Integer pageSize;
    private String field;
    private String order;
}
