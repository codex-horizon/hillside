package com.metaverse.hillside.work.service.impl;

import com.metaverse.hillside.common.constants.Constants;
import com.metaverse.hillside.common.constants.DeleteStatusEnum;
import com.metaverse.hillside.common.converter.IConverter;
import com.metaverse.hillside.common.exception.BusinessException;
import com.metaverse.hillside.common.restful.response.ApiPageable;
import com.metaverse.hillside.common.utils.RSAUtil;
import com.metaverse.hillside.common.utils.XTokenUtil;
import com.metaverse.hillside.core.env.EnvProperties;
import com.metaverse.hillside.core.setting.ISettingDefault;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AccountService implements IAccountService {

    private final IAccountRepository iAccountRepository;

    private final IConverter iConverter;

    private final EnvProperties envProperties;

    private final ISettingDefault iSettingDefault;

    AccountService(final IAccountRepository iAccountRepository,
                   final IConverter iConverter,
                   final EnvProperties envProperties,
                   final ISettingDefault iSettingDefault) {
        this.iAccountRepository = iAccountRepository;
        this.iConverter = iConverter;
        this.envProperties = envProperties;
        this.iSettingDefault = iSettingDefault;
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
        Page<AccountEntity> accountEntities = iAccountRepository.findAll(
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
        if (!iAccountRepository.existsById(accountDto.getId())) {
            throw new BusinessException("By账号Id查询，信息不存在");
        }

        // 1、预判账户信息是否存在
        AccountEntity accountEntity = iAccountRepository.findById(accountDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("By查询账户Id:[%s] 空", accountDto.getId())));

        accountEntity.setCategory(accountDto.getCategory());
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
    public Boolean deleteById(Long id) {
        if (iAccountRepository.existsById(id)) {
            iAccountRepository.deleteSoftById(id, DeleteStatusEnum.NOT_DELETED.getCode());
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
                .orElseThrow(() -> new EntityNotFoundException(String.format("查询账户Id:[%s] 空", id)));
        return iConverter.convert(accountEntity, AccountVo.class);
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
        String publicKey = iSettingDefault.getPublicKeyByCookie();
        String accountStr = RSAUtil.decrypt(account, publicKey);
        String passwordStr = RSAUtil.decrypt(password, publicKey);

        // 2、库里是否存在
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccount(accountStr);
        accountEntity.setPassword(passwordStr);
        if (!iAccountRepository.exists(Example.of(accountEntity))) {
            throw new BusinessException("用户不存在");
        }

        // 3、根据账号、密码生成X-Token并返回
        return XTokenUtil.generateXToken(new HashMap<String, String>() {{
            put(Constants.ACCOUNT_ID, accountStr);
            put(Constants.ACCOUNT_PASSWORD, passwordStr);
        }}, envProperties.getJwtSignatureSecretKey());
    }

    /**
     * 获取 RSA非对称加密 公钥 服务
     *
     * @return 返回 RSA非对称加密 公钥 结果
     */
    @Override
    public String fetchPublicKey() {
        String publicKey = RSAUtil.getPublicKey();
        iSettingDefault.addPublicKeyByCookie(publicKey);
        return publicKey;
    }

}
