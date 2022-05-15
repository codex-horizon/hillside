package com.metaverse.hillside.work.service.impl;

import com.metaverse.hillside.common.constants.Constants;
import com.metaverse.hillside.common.constants.DeleteStatusEnum;
import com.metaverse.hillside.common.converter.IConverter;
import com.metaverse.hillside.common.exception.BusinessException;
import com.metaverse.hillside.common.restful.response.ApiPageable;
import com.metaverse.hillside.common.utils.CommonUtil;
import com.metaverse.hillside.core.helper.PromiseRSAHelper;
import com.metaverse.hillside.core.helper.XTokenHelper;
import com.metaverse.hillside.work.dto.AccountDto;
import com.metaverse.hillside.work.entity.AccountEntity;
import com.metaverse.hillside.work.qry.AccountQry;
import com.metaverse.hillside.work.repository.IAccountRepository;
import com.metaverse.hillside.work.service.IAccountService;
import com.metaverse.hillside.work.vo.AccountVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class AccountService implements IAccountService {

    private final PromiseRSAHelper promiseRSAHelper;

    private final XTokenHelper xTokenHelper;

    private final IAccountRepository iAccountRepository;

    private final IConverter iConverter;

    AccountService(final PromiseRSAHelper promiseRSAHelper,
                   final XTokenHelper xTokenHelper,
                   final IAccountRepository iAccountRepository,
                   final IConverter iConverter) {
        this.promiseRSAHelper = promiseRSAHelper;
        this.xTokenHelper = xTokenHelper;
        this.iAccountRepository = iAccountRepository;
        this.iConverter = iConverter;
    }

    /**
     * 查询 账户列表 分页 服务
     *
     * @param accountQry 账户查询条件参数 对象
     * @return 返回 账户列表 分页 结果
     */
    @Transactional(readOnly = true)
    @Override
    public ApiPageable<List<AccountVo>> findByPageable(AccountQry accountQry) {
        Specification<AccountEntity> qryConditionSpecification = (Specification<AccountEntity>) (root, query, criteriaBuilder) -> {
            // 账号分类
            if (!ObjectUtils.isEmpty(accountQry.getCategory())) {
                // 全量匹配
                criteriaBuilder.equal(root.get("category"), accountQry.getCategory());
            }
            // 账号
            if (!ObjectUtils.isEmpty(accountQry.getAccount())) {
                // 模糊匹配
                criteriaBuilder.like(root.get("account"), accountQry.getAccount());
            }
            return criteriaBuilder.conjunction();
        };
        Page<AccountEntity> accountEntities = iAccountRepository.findAll(qryConditionSpecification,
                PageRequest.of(
                        accountQry.getStartPage(),
                        accountQry.getPageSize(),
                        Sort.Direction.DESC,
                        "lastModifiedDate"
                )
        );
        return ApiPageable.response(
                accountEntities.getTotalElements(),
                iConverter.convert(accountEntities, AccountVo.class)
        );
    }

    /**
     * 新增 账户 信息 服务
     *
     * @param accountDto 账户信息
     * @return 返回 新增 账户 状态
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean add(AccountDto accountDto) {
        iAccountRepository.save(iConverter.convert(accountDto, AccountEntity.class));
        return true;
    }

    /**
     * 修改 账户 信息 服务
     *
     * @param accountDto 账户信息
     * @return 返回 修改 账户 状态
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean modify(AccountDto accountDto) {
        // 1、预判账户Id是否存在
        Optional.ofNullable(accountDto).map(AccountDto::getId).orElseThrow(() -> new BusinessException("账户Id 空"));

        // 2、预判账户信息是否存在
        AccountEntity accountEntity = iAccountRepository.findById(accountDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("查询账户Id[%s]不存在", accountDto.getId())));

        accountEntity.setCategory(accountDto.getCategory());
        accountEntity.setState(accountDto.getState());
        accountEntity.setAccount(accountDto.getAccount());
        accountEntity.setPassword(accountDto.getPassword());
        accountEntity.setNickName(accountDto.getNickName());
        accountEntity.setAvatarUrl(accountDto.getAvatarUrl());

        iAccountRepository.save(accountEntity);
        return true;
    }

    /**
     * 根据 账户Id 删除 服务
     *
     * @param id 账户Id
     * @return 返回 账户 删除 状态
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteSoftById(Long id) {
        if (iAccountRepository.existsById(id)) {
            iAccountRepository.deleteSoftById(id, DeleteStatusEnum.DELETED.getCode());
        }
        return true;
    }

    /**
     * 根据 账户Id 查询 详情 服务
     *
     * @param id 账户Id
     * @return 返回 用户 详情 结果
     */
    @Transactional(readOnly = true)
    @Override
    public AccountVo findDetailById(Long id) {
        AccountEntity accountEntity = iAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("查询账户Id[%s]空", id)));
        return iConverter.convert(accountEntity, AccountVo.class);
    }

    /**
     * 校验 账户 是否存在 服务
     *
     * @param account  账户
     * @param password 密码
     * @return 返回 账户 是否存在
     */
    @Override
    public Boolean exists(String account, String password) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccount(account);
        accountEntity.setPassword(password);
        return iAccountRepository.exists(Example.of(accountEntity));
    }

    /**
     * 获取 X-Token 服务
     *
     * @param account  账户
     * @param password 密码
     * @return 返回 X-Token 结果
     */
    @Override
    public String fetchXToken(String account, String password) {
        // 1、账号、密码解密
        String publicKey = CommonUtil.getHttpServletRequest().getHeader(Constants.RSA_PUBLIC_KEY);
        if (!StringUtils.hasText(publicKey)) {
            throw new BusinessException("RSA-Public-Key空");
        }
        account = promiseRSAHelper.decrypt(account, publicKey);
        password = promiseRSAHelper.decrypt(password, publicKey);
        promiseRSAHelper.removeKey(publicKey);

        // 2、库里是否存在
        if (!exists(account, password)) {
            throw new BusinessException("用户不存在");
        }

        // 3、根据账号、密码生成X-Token并返回
        Map<String, String> payload = new HashMap<>();
        payload.put(Constants.ACCOUNT_ID, account);
        payload.put(Constants.ACCOUNT_PASSWORD, password);
        return xTokenHelper.generateXToken(payload);
    }

    /**
     * 获取 RSA非对称加密 公钥 服务
     *
     * @return 返回 RSA非对称加密 公钥 结果
     */
    @Override
    public String fetchPublicKey() {
        return promiseRSAHelper.getPublicKey();
    }

}
