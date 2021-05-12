package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysUser;
import com.lnsf.rpc.domain.vo.SysUserVo;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SysUserController extends BaseController {
    @RpcReference(group = "User",version = "V1.0")
    private SysUserService  sysUserService;

    @GetMapping("/sysUser")
    public ResponseResult findAll(SysUser sysUser,@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        List<SysUser> data = sysUserService.findAll(sysUser);
        return getResult(data,pageSize,pageNum);
    }

    @GetMapping("/sysUser/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysUserService.findById(id));
    }

    /**
     * 添加用户请求，注册也在这里
     * @param sysUser
     * @return
     */
    @PostMapping("/sysUser")
    public ResponseResult add(@Validated @RequestBody SysUser sysUser){
        return getResult(sysUserService.add(sysUser));
    }

    @PutMapping("/sysUser")
    public ResponseResult update(@Validated @RequestBody SysUser sysUser){
        return getResult(sysUserService.update(sysUser));
    }

    @DeleteMapping("/sysUser/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysUserService.delete(ids));
    }

    //用户登录
    @RequestMapping("/sysUser/login")
    public ResponseResult login(@RequestBody SysUserVo sysUserVo){
        return getResult(sysUserService.login(sysUserVo));
    }
}
