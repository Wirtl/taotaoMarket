package com.taotao.portal.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Wirt on 2017/8/30
 */
@Service
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId, Integer num,
                          HttpServletResponse response, HttpServletRequest request) {
        TaotaoResult result = cartService.addCart(itemId, num, request, response);
        return "cartSuccess";
    }

}
