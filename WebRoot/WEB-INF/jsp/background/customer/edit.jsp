<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<%@include file="../../common/taglib.jsp" %>
    <%@include file="../../common/common-css.jsp" %>
  </head>
  
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.min.js"></script>
 
  <script type="text/javascript">	  
	  function saveData(){
		 var userName = $("#userName").val();
    	 var userPassword = $("#userPassword").val();		  
    	 if(userName=="" || userName==null){
    	 	alert("请填入用户名称!");
    	 	return false;
    	 }
    	 if( userPassword=="" || userPassword==null ){
    	 	alert("请填入用户密码!");
    	 	return false;
    	 }
    	 
    	 //提交.
    	  document.userForm.submit();
   	  	  return true;
	  }
  </script>
  
  <body>
  <div style="height: 100%;overflow-y: auto;">
<br/>
<br/>  
<form action="<%=request.getContextPath()%>/background/sysuser/update.html" method="post" name="userForm" id="userForm">
		<input type="hidden" name="userId" value="${user.userId}">
		<input type="hidden" name="userPassword" value="${user.userPassword}">
		
		<table class="ttab" height="100" width="85%" border="0" cellpadding="0" cellspacing="1" align="center">
				<tr>
					<td height="30" colspan="4">
						<div align="center">
							<font color="blue" size="4"><b>修改信息</b>
							</font>
						</div></td>
				</tr>
				
				<tr>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">用户名：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							${user.userName}
						</div></td>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">开户所在省：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="province"  value="${user.province}"/>
							<font color="red">*</font>
						</div></td>
				</tr>
				
				<tr>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">所属角色：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
						<select name="roleId" class="STYLE1" >
							<option value="">选择角色</option>
							<c:forEach var="key" items="${roles}">
								<option value="${key.id}" <c:if test="${key.name eq user.roleName}">selected="selected"</c:if>>${key.name}</option>
							</c:forEach>
						</select>
						</div>
					</td>
					
					<td height="30" width="10%">
						<div align="right" class="STYLE1">开户所在市：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="city"  value="${user.city}"/>
							<font color="red">*</font>
						</div></td>
				</tr>
				
				<tr>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">昵称：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="userNickname" value="${user.userNickname}"/>
						</div></td>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">银行名称：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="bankName" value="${user.bankName}"/>
							<font color="red">*</font>
						</div></td>
				</tr>
				<tr>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">真实姓名：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="userRealname" value="${user.userRealname}"/>
						</div></td>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">所属支行或分行：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="subbranchBank" value="${user.subbranchBank}"/>
							<font color="red">*</font>
						</div></td>
				</tr>
				<tr>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">年龄：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="userAge" value="${user.userAge}"/>
						</div></td>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">银行户名：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="bankAccountName" value="${user.bankAccountName}"/>
							<font color="red">*</font>
						</div></td>
				</tr>
				<tr>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">性别：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="userSex" value="${user.userSex}"/>
						</div></td>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">银行账号：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="bankAccount" value="${user.bankAccount}"/>
							<font color="red">*</font>
						</div></td>
				</tr>
				<tr>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">地址：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="userAddress" value="${user.userAddress}"/>
						</div></td>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">账号类型：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input type="radio" name="accountType" value="个人账号" <c:if test="${user.accountType eq '个人账号'}">checked="checked"</c:if>/>：个人账号
							<input type="radio" name="accountType" value="企业账号" <c:if test="${user.accountType eq '企业账号'}">checked="checked"</c:if>/>：企业账号
						</div></td>
				</tr>
				<tr>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">电话：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="userPhone" value="${user.userPhone}"/>
						</div></td>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">是否付费：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input type="radio" name="pay" value="是" <c:if test="${user.pay eq '是'}">checked="checked"</c:if>/>：是
							<input type="radio" name="pay" value="否" <c:if test="${user.pay eq '否'}">checked="checked"</c:if>/>：否
						</div></td>
				</tr>
				<tr>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">邮箱：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="userMail" value="${user.userMail}"/>
						</div></td>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">备注：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="mark"  value="${user.mark}"/>
						</div></td>
				</tr>
				<tr>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">QQ：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="userQQ" value="${user.userQQ}"/>
						</div></td>
					<td height="30" width="10%">
						<div align="right" class="STYLE1">用户状态：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input type="radio" name="status" value="待审核" <c:if test="${user.status eq '待审核'}">checked="checked"</c:if>/>：待审核
							<input type="radio" name="status" value="审核通过" <c:if test="${user.status eq '审核通过'}">checked="checked"</c:if>/>：审核通过
						</div></td>
				</tr>
				<tr>
				<td height="30" width="10%">
						<div align="right" class="STYLE1">级别：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="level"
								readonly="readonly" value="${user.level}"/>
						</div></td>
					<td height="30" width="13%">
						<div align="right" class="STYLE1">上级代理用户名编号：</div></td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="parentNumber"
								readonly="readonly" value="${user.parentNumber}"/>
						</div></td>
				</tr>
				<tr>
					<td colspan="4" style="padding: 10px">
						<div align="center">
						   <sec:authorize ifAnyGranted="ROLE_sys_user_edit">
							<input type="button" value="　保　存　" class="input_btn_style1" onclick="return saveData()" />　　　
				           </sec:authorize>
							<input id="backBt" type="button" value="　返　回　" class="input_btn_style1" onclick="javascript:window.location.href='javascript:history.back()'" />
						</div>
					</td>
				</tr>
			</table>
</form>
</div>
  </body>
</html>
