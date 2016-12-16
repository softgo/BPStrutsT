package com.javabase.base.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.javabase.base.app.LOG4jUtils;
import com.javabase.dao.SysUsersDao;

/**
 * 用户过滤限制.
 *  
 * @author admin
 *
 */
public class SysUserFilter extends PathMatchingFilter{

	@Autowired
	private SysUsersDao sysUsersDao;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String userName = (String)SecurityUtils.getSubject().getPrincipal();
        System.err.println("过滤的用户名是："+userName);
        LOG4jUtils.audit("过滤的用户名是："+userName);
        if (StringUtils.isNotBlank(userName)) {
        	request.setAttribute("user", sysUsersDao.findByName(userName));
            return true;
		}else{
			return false;
		}
    }

}
