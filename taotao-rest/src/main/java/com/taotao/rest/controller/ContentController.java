package com.taotao.rest.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbContent;
import com.taotao.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 内容管理
 *
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;


    /**
     * 根据category_id查 tb_content,得到指定类的内容
     * @param cid
     * @return
     */
    @RequestMapping("/{cid}")
    public TaotaoResult getContentList(@PathVariable Long cid){
        try{
            List<TbContent> contentList = contentService.getContentList(cid);
            return TaotaoResult.ok(contentList);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/sync/content/{cid}")
    public TaotaoResult syncContent(@PathVariable Long cid){
        return contentService.syncContent(cid);
    }
}
