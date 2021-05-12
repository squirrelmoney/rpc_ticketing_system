package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysMovieAge;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysMovieAgeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 电影年代控制器
 */
@RestController
@RequestMapping("/Movie")
public class SysMovieAgeController extends BaseController {

    @RpcReference(group = "Movie",version = "V1.0")
    private SysMovieAgeService sysMovieAgeService;

    @GetMapping("/sysMovieAge")
    public ResponseResult findAll(@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        List<SysMovieAge> data = sysMovieAgeService.findAll();
        return getResult(data,pageSize,pageNum);
    }

    @GetMapping("/sysMovieAge/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysMovieAgeService.findById(id));
    }

    @PostMapping("/sysMovieAge")
    public ResponseResult add(@Validated @RequestBody SysMovieAge sysMovieAge){
        return getResult(sysMovieAgeService.add(sysMovieAge));
    }

    /**
     * 通过@Validated验证前端传来的数据正确性，使用BindResult接收结果
     * @param sysMovieAge
     */
    @PutMapping("/sysMovieAge")
    public ResponseResult update(@Validated @RequestBody SysMovieAge sysMovieAge){
        return getResult(sysMovieAgeService.update(sysMovieAge));
    }

    @DeleteMapping("/sysMovieAge/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysMovieAgeService.delete(ids));
    }

}
