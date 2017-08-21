package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ItemParamServiceImpl implements ItemParamService{

    @Autowired
    private TbItemParamMapper itemParamMapper;

    @Override
    public EasyUIDataGridResult getParamItemList(int page, int rows) {
        //分页处理
        PageHelper.startPage(page,rows);

        //执行查询
        TbItemParamExample example=new TbItemParamExample();
        List<TbItemParam> list=itemParamMapper.selectByExampleWithBLOBs(example);

        //取分页信息
        PageInfo<TbItemParam> pageInfo=new PageInfo<TbItemParam>(list);

        //返回结果
        EasyUIDataGridResult result=new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        return result;
    }

    @Override
    public TaotaoResult getItemParamByCid(Long cid) {

        TbItemParamExample itemParamExample = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = itemParamExample.createCriteria();
        criteria.andItemCatIdEqualTo(cid);


        List<TbItemParam> itemParams = itemParamMapper.selectByExampleWithBLOBs(itemParamExample);
        if(!itemParams.isEmpty()){
            TbItemParam itemParam=itemParams.get(0);
            return TaotaoResult.ok(itemParam);
        }

        return TaotaoResult.ok();
    }


    /**
     * 插入规格参数
     * @param cid
     * @param paramData
     * @return
     */
    @Override
    public TaotaoResult insertItemParam(Long cid, String paramData) {

        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        Date now=new Date();
        itemParam.setCreated(now);
        itemParam.setUpdated(now);

        itemParamMapper.insert(itemParam);

        return TaotaoResult.ok();
    }

    /**
     * 删除规格参数
     * @param ids
     * @return
     */
    @Override
    public TaotaoResult deleteItemParam(String ids) {
        String[] strs=ids.split(",");
        for(String str:strs){
            Long id=Long.valueOf(str);
            itemParamMapper.deleteByPrimaryKey(id);
        }
        return TaotaoResult.ok();
    }
}
