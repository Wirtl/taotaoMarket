package com.taotao.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCatgoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ContentCatgoryServiceImpl implements ContentCatgoryService{


    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {
        //根据parentId查询子节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        //转换成EasyUITreeNode列表
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
            //创建一EasyUITreeNode节点
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            //添加到列表
            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public TaotaoResult insertCatgory(Long parentId, String name) {
        TbContentCategory contentCategory=new TbContentCategory();
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        contentCategory.setIsParent(false);
        contentCategory.setSortOrder(1);
        Date now = new Date();
        contentCategory.setCreated(now);
        contentCategory.setUpdated(now);
        contentCategory.setStatus(1);
        contentCategoryMapper.insert(contentCategory);

        Long id = contentCategory.getId();
        //查询父节点
        TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
        if(!parentNode.getIsParent()){
            parentNode.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parentNode);
        }

        return TaotaoResult.ok(id);
    }

    //重命名
    @Override
    public TaotaoResult updateCatgory(Long id, String name) {
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        contentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKey(contentCategory);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteCatgory(Long id) {

        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        Long parentId = contentCategory.getParentId();
        //删除子节点
        if(contentCategory.getIsParent()){
            deleteChildCatgory(id);
        }
        //删除节点本身
        contentCategoryMapper.deleteByPrimaryKey(id);
        //判断父节点状态
        TbContentCategory parentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> children = contentCategoryMapper.selectByExample(example);
        if(children.isEmpty()){
            parentCategory.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKey(parentCategory);
        }


        return TaotaoResult.ok();
    }

    private void deleteChildCatgory(Long parentId){
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> children = contentCategoryMapper.selectByExample(example);
        for(TbContentCategory child:children){
            deleteChildCatgory(child.getId());
            contentCategoryMapper.deleteByPrimaryKey(child.getId());
        }
    }
}

