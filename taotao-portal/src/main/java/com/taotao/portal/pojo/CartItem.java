package com.taotao.portal.pojo;

import lombok.Data;

/**
 * 购物车
 * Created by Wirt on 2017/8/30
 */
@Data
public class CartItem {
    private Long id;

    private String title;

    private Long price;

    private Integer num;

    private String image;

}
