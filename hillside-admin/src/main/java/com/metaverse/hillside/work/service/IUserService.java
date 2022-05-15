package com.metaverse.hillside.work.service;

import com.metaverse.hillside.common.restful.response.ApiPageable;
import com.metaverse.hillside.work.dto.UserDto;
import com.metaverse.hillside.work.qry.UserQry;
import com.metaverse.hillside.work.vo.UserVo;

import java.util.List;

public interface IUserService {

    /**
     * 查询 用户列表 分页 服务
     *
     * @param userQry 用户查询条件参数 对象
     * @return 返回 用户列表 分页 结果
     */
    ApiPageable<List<UserVo>> findByPageable(UserQry userQry);

    /**
     * 根据 用户Id 查询 详情 服务
     *
     * @param id 用户Id
     * @return 返回 用户 详情 结果
     */
    UserVo findDetailById(Long id);

    /**
     * 根据 用户Id 删除 服务
     *
     * @param id 用户Id
     * @return 返回 用户 删除 状态
     */
    Boolean deleteById(Long id);

    /**
     * 新增 用户 信息 服务
     *
     * @param userDto 用户信息
     * @return 返回 新增 用户 状态
     */
    Boolean add(UserDto userDto);
}
