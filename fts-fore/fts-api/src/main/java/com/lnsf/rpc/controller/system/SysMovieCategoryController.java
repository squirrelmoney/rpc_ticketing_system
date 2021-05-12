package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysMovieCategory;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysMovieCategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Movie")
public class SysMovieCategoryController extends BaseController {

    @RpcReference(group = "Movie",version = "V1.0")
    private SysMovieCategoryService sysMovieCategoryService;

    @GetMapping("/sysMovieCategory")
    public ResponseResult findAll(@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        List<SysMovieCategory> data = sysMovieCategoryService.findAll();
        return getResult(data,pageSize,pageNum);
    }

    @GetMapping("/sysMovieCategory/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysMovieCategoryService.findById(id));
    }

    @PostMapping("/sysMovieCategory")
    public ResponseResult add(@Validated @RequestBody SysMovieCategory sysMovieCategory){
        return getResult(sysMovieCategoryService.add(sysMovieCategory));
    }


    @PutMapping("/sysMovieCategory")
    public ResponseResult update(@Validated @RequestBody SysMovieCategory sysMovieCategory){
        return getResult(sysMovieCategoryService.update(sysMovieCategory));
    }

    @DeleteMapping("/sysMovieCategory/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysMovieCategoryService.delete(ids));
    }
}
