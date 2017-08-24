package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_CONTENT_SYNC_URL}")
    private String REST_CONTENT_SYNC_URL;

    @RequestMapping("/query/list")
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows){
        return contentService.getContentList(categoryId, page, rows);
    }

    @RequestMapping("/save")
    public TaotaoResult insertContent(TbContent content){
        TaotaoResult result= contentService.insertContent(content);
        syncRedis(content.getId());
        return result;
    }

    @RequestMapping("/delete")
    public TaotaoResult deleteContent(String ids){

        TaotaoResult result= contentService.deleteContent(ids);
        String[] strs = ids.split(",");
        for(String str:strs){
            Long id = Long.valueOf(str);
            syncRedis(id);
        }
        return result;
    }

    @RequestMapping("/edit")
    public TaotaoResult updateContent(TbContent content){
        TaotaoResult result=  contentService.updateContent(content);
        syncRedis(content.getId());
        return result;
    }

    //同步redis数据
    private void syncRedis(Long id){
        HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+id);
    }


}
