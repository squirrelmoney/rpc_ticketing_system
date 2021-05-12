package com.lnsf.rpc.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnsf.rpc.page.Page;
import com.lnsf.rpc.page.PageBuilder;
import com.lnsf.rpc.response.ResponseResult;

import java.util.List;

/**
 * 抽取重复功能为基类
 */
public class BaseController {

    //当前页码
    public static final String PAGE_NUM = "pageNum";

    //页大小
    public static final String PAGE_SIZE = "pageSize";

    //总记录数
    public static final String TOTAL = "total";
    /**
     * 开启分页
     */
    public void startPage(){
        Page page = PageBuilder.buildPage();
        Integer pageNum = page.getPageNum();
        Integer pageSize = page.getPageSize();
        if(pageNum != null && pageSize != null){
            PageHelper.startPage(pageNum, pageSize, page.getOrderByColumn());
        }
    }

    /**
     * 根据修改行数返回响应消息
     * @param rows
     * @return
     */
    public ResponseResult getResult(int rows){
        return rows == 0 ? ResponseResult.error() : ResponseResult.success();
    }

    /**
     * 分页响应消息
     * @param data
     * @return
     */
    public ResponseResult getResult(List<?> data){
        PageInfo pageInfo = new PageInfo(data);
        ResponseResult responseResult = ResponseResult.success(data);
        responseResult.put(PAGE_NUM, pageInfo.getPageNum());
        responseResult.put(PAGE_SIZE, pageInfo.getPageSize());
        responseResult.put(TOTAL, pageInfo.getTotal());
        return responseResult;
    }
    /**
     * 分页响应消息
     * @param data
     * @return
     */
    public ResponseResult getResult(List<?> data,int pageSize,int pageNum){
        if(pageSize == 0){
           pageSize = data.size();
        }
        if (pageNum == 0){
            pageNum = 1;
        }
        PageInfo pageInfo = new PageInfo(data);
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize,data.size());
        ResponseResult responseResult = ResponseResult.success(data.subList(startIndex,endIndex));
        responseResult.put(PAGE_NUM, pageNum);
        responseResult.put(PAGE_SIZE, pageSize);
        responseResult.put(TOTAL, pageInfo.getTotal());
        return responseResult;
    }
    /**
     * 对象类型响应消息
     * @param data
     * @return
     */
    public ResponseResult getResult(Object data){
        return ResponseResult.success(data);
    }


}
