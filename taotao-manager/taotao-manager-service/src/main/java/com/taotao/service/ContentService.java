package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {

    EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows);

    TaotaoResult insertContent(TbContent content);

    TaotaoResult deleteContent(String ids);

    TaotaoResult updateContent(TbContent content);
}
