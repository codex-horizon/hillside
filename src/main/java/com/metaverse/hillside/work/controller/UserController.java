package com.metaverse.hillside.work.controller;

import com.metaverse.hillside.common.restful.response.ApiPageable;
import com.metaverse.hillside.common.restful.response.ApiResult;
import com.metaverse.hillside.work.dto.UserDto;
import com.metaverse.hillside.work.qry.UserQry;
import com.metaverse.hillside.work.service.IUserService;
import com.metaverse.hillside.work.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(
        name = "用户 控制器",
        path = "/user",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class UserController {

    private final IUserService iUserService;

    UserController(final IUserService iUserService) {
        this.iUserService = iUserService;
    }

    /**
     * 查询 用户列表 分页 接口
     *
     * @param userQry 用户查询条件参数 对象
     * @return 响应 用户列表 分页 结果
     */
    @PostMapping(
            name = "查询 用户列表 分页 接口",
            path = "/t2b/findByPageable",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResult<ApiPageable<List<UserVo>>> findByPageable(@RequestBody UserQry userQry) {
        return ApiResult.succeeded("查询 用户列表 分页 完成", iUserService.findByPageable(userQry));
    }

    /**
     * 根据 用户Id 查询 详情 接口
     *
     * @param id 用户Id
     * @return 响应 用户 详情 结果
     */
    @GetMapping(
            name = "根据 用户Id 查询 详情 接口",
            path = "/t2b/findDetailById/{id}"
    )
    public ApiResult<UserVo> findDetailById(@PathVariable("id") Long id) {
        return ApiResult.succeeded("根据 用户Id 查询 详情 完成", iUserService.findDetailById(id));
    }

    /**
     * 根据 用户Id 删除 接口
     *
     * @param id 用户Id
     * @return 响应 用户 删除 状态
     */
    @DeleteMapping(
            name = "根据 用户Id 删除 接口",
            path = "/t2b/deleteById/{id}"
    )
    public ApiResult<Boolean> deleteById(@PathVariable("id") Long id) {
        return ApiResult.succeeded("根据 用户Id 删除 完成", iUserService.deleteById(id));
    }

    /**
     * 新增 用户 信息 接口
     *
     * @param userDto 用户信息
     * @return 响应 新增 用户 状态
     */
    @PostMapping(
            name = "新增 用户 信息 接口",
            path = "/t2b/add",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResult<Boolean> add(@RequestBody @Validated UserDto userDto) {
        return ApiResult.succeeded("新增 用户 信息 完成", iUserService.add(userDto));
    }

    @PutMapping(
            name = "",
            path = ""
    )
    public void modify() {

    }
}
