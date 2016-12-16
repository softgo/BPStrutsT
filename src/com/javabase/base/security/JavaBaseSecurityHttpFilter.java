package com.javabase.base.security;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.javabase.base.app.LOG4jUtils;

/**
 * 加密配置器
 * 
 * @author admin
 *
 */
public class JavaBaseSecurityHttpFilter implements Filter{

	private String desKey;
	private String isSecret;
	private String[] secretPaths;

	private final static String SECRET_ERROR = "{\"flag\":0,\"msg\":\"版本过期，请更新!\",\"data\":{\"mobilePhone\":\"0\"}}";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		desKey = filterConfig.getInitParameter("desSecretKey");
		isSecret = filterConfig.getInitParameter("isSecret");
		String secretPath = filterConfig.getInitParameter("securityPath");
		secretPaths = secretPath.split(";");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String requestPath = ((HttpServletRequest) request).getRequestURI();
		String param_t = request.getParameter("t");
		boolean needSecurity = false;
		if (isSecretPath(requestPath)) {
			Map<String, String[]> additionalParams = new TreeMap<>();
			if(isSecretFilter(isSecret)) {
				needSecurity = true;
			} else {
				String clientVersion = request.getParameter("client_version");
				String clientType = request.getParameter("client_type");
				if(("iOS".equals(clientType) && "v4.5.4".compareTo(clientVersion) > 0)
					|| ("android".equals(clientType) && "4.5.6".compareTo(clientVersion) > 0)
					|| ("BS".equals(clientType) && "1.0.0".compareTo(clientVersion) > 0)) {
					needSecurity =false;
				} else {
					needSecurity = true;
				}
			}
			
			if (needSecurity) {
				if (StringUtils.isNotBlank(param_t)) {
					try {
						byte[] paramBytes = JavaBaseSecurityUtil.decryptBASE64(param_t);
						String descParams = JavaBaseSecurityUtil.desDecrypt(desKey, paramBytes);
						String removeLast = descParams.substring(0, descParams.length() - 8);
						String[] rsaParamArray = removeLast.split("\\|");
						StringBuilder sb = new StringBuilder(256);
						for (String rParam : rsaParamArray) {
							sb.append(JavaBaseSecurityUtil.decryptByPrivateKey(JavaBaseSecurityUtil.decryptBASE64(rParam)));
						}
						Map<String, Object> parameterMap = JSONObject.parseObject(sb.toString(), Map.class);
	
						parameterMap.forEach(new BiConsumer<String, Object>() {
							public void accept(String key, Object value) {
								additionalParams.put(key, new String[] { String.valueOf(value) });
							}
						});
					} catch (Exception e) {
						LOG4jUtils.error("参数加密安全验证失败", e);
						response.setContentType("applicaton/json;charset=utf-8");
						response.getWriter().write(SECRET_ERROR);
						return;
					}
					
					//过滤操作...
					chain.doFilter(new JavaBaseSecurityHttpRequestWrapper((HttpServletRequest) request, additionalParams),response);
					
					return;
				} else {
					LOG4jUtils.error("参数加密时t为空");
					response.setContentType("applicaton/json;charset=utf-8");
					response.getWriter().write(SECRET_ERROR);
					return;
				}
			}
		}

		chain.doFilter(request, response);
	}

	public void destroy() {

	}

	private boolean isSecretFilter(String filterFlag) {
		if ("1".equals(filterFlag)) {
			return true;
		}
		return false;
	}

	private boolean isSecretPath(String path) {
		if (StringUtils.isBlank(path)) {
			return false;
		}
		for (String p : secretPaths) {
			if (path.endsWith(p)) {
				return true;
			}
		}
		return false;
	}

}
