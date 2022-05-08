package com.metaverse.hillside.work.service;

import com.metaverse.hillside.common.restful.response.ApiPageable;
import com.metaverse.hillside.work.dto.AccountDto;
import com.metaverse.hillside.work.qry.AccountQry;
import com.metaverse.hillside.work.vo.AccountVo;

import java.util.List;

public interface IAccountService {

    /**
     * 查询 账户列表 分页 服务
     *
     * @param accountQry 账户查询条件参数 对象
     * @return 返回 账户列表 分页 结果
     */
    ApiPageable<List<AccountVo>> findByPageable(AccountQry accountQry);

    /**
     * 新增 账户 信息 服务
     *
     * @param accountDto 账户信息
     * @return 返回 新增 账户 状态
     */
    Boolean add(AccountDto accountDto);

    /**
     * 修改 账户 信息 服务
     *
     * @param accountDto 账户信息
     * @return 返回 修改 账户 状态
     */
    Boolean modify(AccountDto accountDto);

    /**
     * 根据 账户Id 删除 服务
     *
     * @param id 账户Id
     * @return 返回 账户 删除 状态
     */
    Boolean deleteById(Long id);

    /**
     * 根据 账户Id 查询 详情 服务
     *
     * @param id 账户Id
     * @return 返回 用户 详情 结果
     */
    AccountVo findDetailById(Long id);

    /**
     * 获取 RSA非对称加密 公钥 服务
     *
     * @return 返回 RSA非对称加密 公钥 结果
     */
    String fetchPublicKey();

    /**
     * 获取 X-Token 服务
     *
     * @param account  账户
     * @param password 密码
     * @return 返回 X-Token 结果
     */
    String fetchXToken(String account, String password);

    /**
     * 校验 账户 是否存在 服务
     *
     * @param account  账户
     * @param password 密码
     * @return 返回 账户 是否存在
     */
    Boolean exists(String account, String password);

}
