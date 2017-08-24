package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCatgoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/content/category")
public class ContentCatgoryController {

    @Autowired
    private ContentCatgoryService contentCatgoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
        List<EasyUITreeNode> list = contentCatgoryService.getContentCatList(parentId);
        return list;

    }

    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult insertCatgory(Long parentId, String name){
        return contentCatgoryService.insertCatgory(parentId,name);
    }

    //重命名
    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateCatgory(Long id, String name){
        return contentCatgoryService.updateCatgory(id, name);
    }

    //删除节点
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteCatgory(Long id){
        return contentCatgoryService.deleteCatgory(id);
    }


}
