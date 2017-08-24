package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

public interface ContentCatgoryService {
    List<EasyUITreeNode> getContentCatList(Long parentId);

    TaotaoResult insertCatgory(Long parentId, String name);

    //重命名
    TaotaoResult updateCatgory(Long id, String name);

    TaotaoResult deleteCatgory(Long id);

}
