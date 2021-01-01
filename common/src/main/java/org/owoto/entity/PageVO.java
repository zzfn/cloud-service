package org.owoto.entity;

import lombok.Data;

/**
 * @author zzf
 */
@Data
public class PageVO {
    private Integer pageNumber = 1;
    private Integer pageSize = 10;
    private String field;
    private String order;
}
