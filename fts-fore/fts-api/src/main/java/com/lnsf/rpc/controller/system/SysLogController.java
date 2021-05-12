package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysActor;
import com.lnsf.rpc.entity.RpcLog;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.RpcLogService;
import com.lnsf.rpc.service.SysActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/Log")
public class SysLogController extends BaseController {
    @Autowired
    private RpcLogService rpcLogService;

    @GetMapping("/syslog")
    public ResponseResult findAll(@RequestParam(required = false) String codec,@RequestParam int pageSize,@RequestParam int pageNum){
        startPage();
        RpcLog rpcLog = new RpcLog();
        rpcLog.setCodec(codec);
        List<RpcLog> data = rpcLogService.queryAll(rpcLog);
        return getResult(data,pageSize,pageNum);
    }

    @GetMapping("/getrpctime")
    public ResponseResult getrpctime(){
        Map<String, List> data = rpcLogService.getrpctime();
        return  ResponseResult.success(data);
    }
}
