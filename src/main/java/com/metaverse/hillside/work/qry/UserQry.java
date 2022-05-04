package com.metaverse.hillside.work.qry;

import com.metaverse.hillside.common.restful.request.Pageable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQry extends Pageable {

    /**
     * 姓名
     */
    private String name;
}
