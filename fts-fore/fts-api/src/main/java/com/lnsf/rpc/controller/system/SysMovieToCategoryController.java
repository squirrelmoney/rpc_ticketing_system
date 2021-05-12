package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysMovieToCategory;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysMovieToCategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Movie")
public class SysMovieToCategoryController extends BaseController {

    @RpcReference(group = "Movie",version = "V1.0")
    private SysMovieToCategoryService sysMovieToCategoryService;

    @GetMapping("/sysMovieToCategory")
    public ResponseResult findAll(SysMovieToCategory sysMovieToCategory,@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        return getResult(sysMovieToCategoryService.findAll(sysMovieToCategory),pageSize,pageNum);
    }

    @PostMapping("/sysMovieToCategory/{movieId}/{movieCategoryId}")
    public ResponseResult add(@PathVariable Long movieId, @PathVariable Long movieCategoryId){
        return getResult(sysMovieToCategoryService.add(new SysMovieToCategory(movieId, movieCategoryId)));
    }

    @DeleteMapping("/sysMovieToCategory/{movieId}/{movieCategoryId}")
    public ResponseResult delete(@PathVariable Long movieId, @PathVariable Long movieCategoryId){
        return getResult(sysMovieToCategoryService.delete(new SysMovieToCategory(movieId, movieCategoryId)));
    }

}
