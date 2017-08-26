package com.taotao.rest.controller;


import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 查询商品基本信息
     * <p>Title: getItemById</p>
     * <p>Description: </p>
     * @param itemId
     * @return
     */
    @RequestMapping("/base/{itemId}")
    public TaotaoResult getItemById(@PathVariable Long itemId) {
        try {
            TbItem item = itemService.getItemById(itemId);
            return TaotaoResult.ok(item);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/desc/{itemId}")
    public TaotaoResult getItemDescById(@PathVariable Long itemId) {
        try {
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);
            return TaotaoResult.ok(itemDesc);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/param/{itemId}")
    public TaotaoResult getItemParamById(@PathVariable Long itemId) {
        try {
            TbItemParamItem itemParamItem = itemService.getItemParamById(itemId);
            return TaotaoResult.ok(itemParamItem);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }




}
