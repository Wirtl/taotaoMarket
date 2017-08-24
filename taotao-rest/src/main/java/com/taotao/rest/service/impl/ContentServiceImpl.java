package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.component.JedisClient;
import com.taotao.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    private TbContentMapper contentMapper;

    @Value("${REDIS_CONTENT_KEY}")
    private String REDIS_CONTENT_KEY;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public List<TbContent> getContentList(Long cid) {

        //添加缓存
        //查询数据库之前先查询缓存，如果有直接返回
        try{
            String json= jedisClient.hget(REDIS_CONTENT_KEY,cid+"").trim();
            if(json!=null && !json.isEmpty()){
                List<TbContent> list= JsonUtils.jsonToList(json,TbContent.class);
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //没在缓存中查到，在数据库中查
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> contents = contentMapper.selectByExampleWithBLOBs(example);

        //返回结果前把结果添加到缓存中
        try{
            jedisClient.hset(REDIS_CONTENT_KEY,cid+"",JsonUtils.objectToJson(contents));
        }catch (Exception e){
            e.printStackTrace();
        }

        return contents;
    }

    @Override
    public TaotaoResult syncContent(Long id) {
        try {
            jedisClient.hdel(REDIS_CONTENT_KEY, id + "");
            return TaotaoResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
