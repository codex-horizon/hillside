package com.metaverse.hillside.work.controller;

import com.metaverse.hillside.common.restful.response.ApiPageable;
import com.metaverse.hillside.common.restful.response.ApiResult;
import com.metaverse.hillside.work.dto.AccountDto;
import com.metaverse.hillside.work.dto.groups.ValidatedGroups;
import com.metaverse.hillside.work.qry.AccountQry;
import com.metaverse.hillside.work.service.IAccountService;
import com.metaverse.hillside.work.vo.AccountVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(
        name = "账户 控制器",
        path = "/account",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AccountController {

    private final IAccountService iAccountService;

    AccountController(final IAccountService iAccountService) {
        this.iAccountService = iAccountService;
    }

    /**
     * 获取 RSA非对称加密 公钥 接口
     *
     * @return 响应 RSA非对称加密 公钥 结果
     */
    @GetMapping(
            name = "获取 RSA非对称加密 公钥 接口",
            path = "/toB/fetchPublicKey"
    )
    public ApiResult<String> fetchPublicKey() {
        return ApiResult.succeeded("获取 RSA非对称加密 公钥 完成", iAccountService.fetchPublicKey());
    }

    /**
     * 获取 X-Token 接口
     *
     * @param accountDto 账户信息
     * @return 响应 X-Token 结果
     */
    @PostMapping(
            name = "获取 X-Token 接口",
            path = "/toB/fetchXToken",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResult<String> fetchXToken(@RequestBody @Validated(ValidatedGroups.Login.class) AccountDto accountDto) {
        String account = accountDto.getAccount();
        String password = accountDto.getPassword();
        return ApiResult.succeeded("获取 X-Token 完成", iAccountService.fetchXToken(account, password));
    }

    /**
     * 查询 账户列表 分页 接口
     *
     * @param accountQry 账户查询条件参数 对象
     * @return 响应 账户列表 分页 结果
     */
    @PostMapping(
            name = "查询 账户列表 分页 接口",
            path = "/toB/findByPageable",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResult<ApiPageable<List<AccountVo>>> findByPageable(@RequestBody AccountQry accountQry) {
        return ApiResult.succeeded("查询 账户列表 分页 完成", iAccountService.findByPageable(accountQry));
    }

    /**
     * 根据 账户Id 查询 详情 接口
     *
     * @param id 账户Id
     * @return 响应 用户 详情 结果
     */
    @GetMapping(
            name = "根据 账户Id 查询 详情 接口",
            path = "/toB/findDetailById/{id}"
    )
    public ApiResult<AccountVo> findDetailById(@PathVariable("id") Long id) {
        return ApiResult.succeeded("根据 账户Id 查询 详情 完成", iAccountService.findDetailById(id));
    }

    /**
     * 新增 账户 信息 接口
     *
     * @param accountDto 账户信息
     * @return 响应 新增 账户 状态
     */
    @PostMapping(
            name = "新增 账户 信息 接口",
            path = "/toB/add",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResult<Boolean> add(@RequestBody @Validated(ValidatedGroups.Add.class) AccountDto accountDto) {
        return ApiResult.succeeded("新增 账户 信息 完成", iAccountService.add(accountDto));
    }

    /**
     * 修改 账户 信息 接口
     *
     * @param accountDto 账户信息
     * @return 响应 修改 账户 状态
     */
    @PutMapping(
            name = "修改 账户 信息 接口",
            path = "/toB/modify",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResult<Boolean> modify(@RequestBody @Validated(ValidatedGroups.Modify.class) AccountDto accountDto) {
        return ApiResult.succeeded("修改 账户 信息 接口", iAccountService.modify(accountDto));
    }

    /**
     * 根据 账户Id 删除 接口
     *
     * @param id 账户Id
     * @return 响应 账户 删除 状态
     */
    @DeleteMapping(
            name = "根据 账户Id 删除 接口",
            path = "/toB/deleteById/{id}"
    )
    public ApiResult<Boolean> deleteById(@PathVariable("id") Long id) {
        return ApiResult.succeeded("根据 账户Id 删除 完成", iAccountService.deleteSoftById(id));
    }

}
