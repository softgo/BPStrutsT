package com.javabase.entity;

import java.util.*;
import java.util.Date;
import java.io.*;

/**
 * 实体bean
 * 
 * @author bruce
 *
 */

@SuppressWarnings("serial")
public class SysUsers implements Serializable {

	private final long serialVersionUID = 1L;
	private String roleName;  //    角色名字
	private Set<SysRoles> roles = new HashSet<SysRoles>(0);
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
