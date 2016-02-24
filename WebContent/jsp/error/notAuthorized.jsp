<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="content">
	<div class="inside">
		<fieldset>
			<h2><s:property value="%{notAuthorizedErrorMessage}"/> <s:a href="mailto:%{gdsContactEmail}"><s:property value="gdsContactEmail"/></s:a>.</h2>
		</fieldset>
	</div>
</div>

<br /><br /><br /><br />