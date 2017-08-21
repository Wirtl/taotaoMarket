package com.taotao.rest.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService{

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public ItemCatResult getItemCatList() {

        List catList = getItemCatList(0l);
        ItemCatResult result = new ItemCatResult();
        result.setData(catList);
        return result;
    }

    private List getItemCatList(Long parentId){
        //根据parentId查询列表
        TbItemCatExample itemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = itemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(itemCatExample);
        List resultList=new ArrayList();
        for (TbItemCat cat:list) {
            if(cat.getIsParent()){
                CatNode node=new CatNode();
                node.setUrl("/products/"+cat.getId()+".html");
                if(parentId == 0){
                    node.setName("<a href='/products/"+cat.getId()+".html'>"+cat.getName()+"</a>");
                }else{
                    node.setName(cat.getName());
                }
                node.setItems(getItemCatList(cat.getId()));
                resultList.add(node);
            }else {
                String item="/products/"+cat.getId()+".html|" + cat.getName();
                resultList.add(item);
            }
            //元素不能超过14个
            if(resultList.size()>=14){
                break;
            }
        }
        return resultList;


    }
}
