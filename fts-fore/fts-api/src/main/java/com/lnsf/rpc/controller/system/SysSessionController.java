package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysSession;
import com.lnsf.rpc.domain.vo.SysSessionVo;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysSessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SysSessionController extends BaseController {

    @RpcReference(group = "Session",version = "V1.0")
    private SysSessionService sysSessionService;

    /**
     * 根据vo中的条件查询所有场次，如果在前台购票部分注意设置pageSize=100或者其他大一些的数
     * @param sysSessionVo
     * @return
     */
    @GetMapping("/sysSession")
    public ResponseResult findByVo(SysSessionVo sysSessionVo,@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        List<SysSession> list = sysSessionService.findByVo(sysSessionVo);
        return getResult(list,pageSize,pageNum);
    }

    @GetMapping("/sysSession/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysSessionService.findById(id));
    }

    @PostMapping("/sysSession")
    public ResponseResult add(@RequestBody SysSession sysSession){
        return getResult(sysSessionService.add(sysSession));
    }

    @PutMapping("/sysSession")
    public ResponseResult update(@RequestBody SysSession sysSession){
        return getResult(sysSessionService.update(sysSession));
    }

    @DeleteMapping("/sysSession/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysSessionService.delete(ids));
    }

}
