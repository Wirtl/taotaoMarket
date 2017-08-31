package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Wirt on 2017/8/30
 */
public interface CartService {
    TaotaoResult addCart(Long itemId, Integer num,
                                HttpServletRequest request, HttpServletResponse response);


}
