package com.metaverse.hillside.core.configurer.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.metaverse.hillside.common.constants.Constants;
import com.metaverse.hillside.common.exception.BusinessException;
import com.metaverse.hillside.core.env.EnvProperties;
import com.metaverse.hillside.core.helper.XTokenHelper;
import com.metaverse.hillside.work.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class XTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private EnvProperties envProperties;

    @Autowired
    private XTokenHelper xTokenHelper;

    @Autowired
    private IAccountService iAccountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String xToken = request.getHeader(Constants.X_TOKEN);
        if (!StringUtils.hasText(xToken)) {
            throw new BusinessException("X-Token 空");
        }
        try {
            Map<String, Claim> claims = xTokenHelper.getClaims(xToken, envProperties.getJwtSignatureSecretKey());
            String account = claims.get(Constants.ACCOUNT_ID).asString();
            String password = claims.get(Constants.ACCOUNT_PASSWORD).asString();
            if (!iAccountService.exists(account, password)) {
                throw new BusinessException();
            }
        } catch (SignatureVerificationException e) {
            throw new BusinessException("X-Token 无效签名");
        } catch (TokenExpiredException e) {
            throw new BusinessException("X-Token 已经过期");
        } catch (AlgorithmMismatchException e) {
            throw new BusinessException("X-Token 算法不一致");
        } catch (Exception e) {
            throw new BusinessException("X-Token 无效");
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

}
