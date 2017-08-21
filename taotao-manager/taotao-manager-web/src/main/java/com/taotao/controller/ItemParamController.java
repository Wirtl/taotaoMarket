package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getParamItemList(int page, int rows){
        return itemParamService.getParamItemList(page, rows);
    }

    @RequestMapping(value = {"/query/itemcatid/{cid}","/cid/{cid}"})
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable("cid") Long cid){
        return itemParamService.getItemParamByCid(cid);
    }

    @RequestMapping(value = "/save/{cid}",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult insertItemParam(@PathVariable Long cid, String paramData){
        return itemParamService.insertItemParam(cid,paramData);
    }

    /**
     * 删除
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteItemParam(HttpServletRequest request){
        String ids = request.getParameter("ids");
        return itemParamService.deleteItemParam(ids);
    }


}
