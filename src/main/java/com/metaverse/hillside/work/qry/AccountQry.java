package com.metaverse.hillside.work.qry;

import com.metaverse.hillside.common.restful.request.Pageable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountQry extends Pageable {

    /**
     * 账户分类
     */
    private Integer category;

    /**
     * 账户
     */
    private String account;
}
