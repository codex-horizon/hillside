package com.metaverse.hillside.work.service.impl;

import com.metaverse.hillside.common.constants.DeleteStatusEnum;
import com.metaverse.hillside.common.converter.IConverter;
import com.metaverse.hillside.common.exception.BusinessException;
import com.metaverse.hillside.common.restful.response.ApiPageable;
import com.metaverse.hillside.work.dto.UserDto;
import com.metaverse.hillside.work.entity.UserEntity;
import com.metaverse.hillside.work.qry.UserQry;
import com.metaverse.hillside.work.repository.IAccountRepository;
import com.metaverse.hillside.work.repository.IUserRepository;
import com.metaverse.hillside.work.service.IUserService;
import com.metaverse.hillside.work.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Slf4j
@Service
public class UserService implements IUserService {

    private final IUserRepository iUserRepository;

    private final IAccountRepository iAccountRepository;

    private final IConverter iConverter;

    UserService(final IUserRepository iUserRepository,
                final IAccountRepository iAccountRepository,
                final IConverter iConverter) {
        this.iUserRepository = iUserRepository;
        this.iAccountRepository = iAccountRepository;
        this.iConverter = iConverter;
    }

    /**
     * 查询 用户列表 分页 服务
     *
     * @param userQry 用户查询条件参数 对象
     * @return 返回 用户列表 分页 结果
     */
    @Transactional(readOnly = true)
    @Override
    public ApiPageable<List<UserVo>> findByPageable(UserQry userQry) {
        Page<UserEntity> userEntities = iUserRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    Predicate predicate = criteriaBuilder.conjunction();
                    if (!StringUtils.hasText(userQry.getName())) {
                        predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + userQry.getName() + "%"));
                    }
                    return predicate;
                },
                PageRequest.of(
                        userQry.getStartPage(),
                        userQry.getPageSize(),
                        Sort.Direction.DESC,
                        "lastModifiedDate"
                )
        );
        return ApiPageable.response(
                userEntities.getTotalElements(),
                iConverter.convert(userEntities, UserVo.class)
        );
    }

    /**
     * 根据 用户Id 查询 详情 服务
     *
     * @param id 用户Id
     * @return 返回 用户 详情 结果
     */
    @Transactional(readOnly = true)
    @Override
    public UserVo findDetailById(Long id) {
        UserEntity userEntity = iUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("查询用户Id:[%s] 空", id)));
        return iConverter.convert(userEntity, UserVo.class);
    }

    /**
     * 根据 用户Id 删除 服务
     *
     * @param id 用户Id
     * @return 返回 用户 删除 状态
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteById(Long id) {
        if (iUserRepository.existsById(id)) {
            iUserRepository.deleteSoftById(id, DeleteStatusEnum.NOT_DELETED.getCode());
        }
        return true;
    }

    /**
     * 新增 用户 信息 服务
     *
     * @param userDto 用户信息
     * @return 返回 新增 用户 状态
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean add(UserDto userDto) {
        UserEntity userEntity = iConverter.convert(userDto, UserEntity.class);

        // 1、预判用户关联账号是否存在
        if (!iAccountRepository.existsById(userEntity.getAccountId())) {
            throw new BusinessException("用户关联账号信息不存在");
        }

        // 2、用户信息落库
        iUserRepository.save(userEntity);
        return true;
    }

}
