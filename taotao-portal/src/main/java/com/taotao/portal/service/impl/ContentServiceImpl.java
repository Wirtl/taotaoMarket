package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AdNode;
import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容管理
 */
@Service
public class ContentServiceImpl implements ContentService{

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_URL}")
    private String REST_CONTENT_URL;
    @Value("${REST_CONTENT_AD1_CID}")
    private String REST_CONTENT_AD1_CID;

    @Override
    public String getAd1List() {

        //调用服务获得数据
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_URL + REST_CONTENT_AD1_CID);
        //把json转化为java对象
        TaotaoResult result = TaotaoResult.formatToList(json, TbContent.class);
        //取data
        List<TbContent> contentList = (List<TbContent>) result.getData();
        //转化为AdNode
        List<AdNode> resultList = new ArrayList<>();
        for(TbContent content:contentList ){
            AdNode node = new AdNode();
            node.setHeight(240);
            node.setWidth(670);
            node.setSrc(content.getPic());

            node.setHeightB(240);
            node.setWidthB(550);
            node.setSrcB(content.getPic2());

            node.setAlt(content.getSubTitle());
            node.setHref(content.getUrl());
            resultList.add(node);
        }
        //转化为JSON
        return JsonUtils.objectToJson(resultList);
    }
}
