package com.kensure.mdt.controller;

import javax.servlet.http.HttpServletRequest;

import com.kensure.mdt.entity.AuthUser;

public class BaseController {

	public AuthUser getCurrentUser(HttpServletRequest req) {
        AuthUser user = (AuthUser) req.getSession().getAttribute("user");
        return user;
    }

}
