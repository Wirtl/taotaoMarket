package com.taotao.portal.service;

import com.taotao.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Wirt on 2017/8/30
 */
public interface UserService {

    TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response);
}
