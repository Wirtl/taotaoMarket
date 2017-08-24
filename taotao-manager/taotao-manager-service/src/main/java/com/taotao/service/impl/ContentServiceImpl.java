package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {

        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(categoryId);
        if(contentCategory==null || contentCategory.getIsParent()){
            return null;
        }

        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);

        List<TbContent> contents = contentMapper.selectByExampleWithBLOBs(example);
        //分页
        PageHelper.startPage(page,rows);
        PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(contents);

        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(contents);
        result.setTotal(pageInfo.getTotal());

        return result;

    }

    @Override
    public TaotaoResult insertContent(TbContent content) {

        Date now = new Date();
        content.setCreated(now);
        content.setUpdated(now);
        contentMapper.insert(content);
        return TaotaoResult.build(200,null);

    }

    @Override
    public TaotaoResult deleteContent(String ids) {

        String[] strs = ids.split(",");
        for(String str:strs){
            Long id = Long.valueOf(str);
            contentMapper.deleteByPrimaryKey(id);
        }
        return TaotaoResult.build(200,null);

    }

    @Override
    public TaotaoResult updateContent(TbContent content){
        Date now = new Date();
        content.setUpdated(now);
        contentMapper.updateByPrimaryKeyWithBLOBs(content);
        return TaotaoResult.build(200,null);
    }
}
