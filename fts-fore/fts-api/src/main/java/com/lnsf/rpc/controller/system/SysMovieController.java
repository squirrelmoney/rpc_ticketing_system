package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.constant.MovieRankingList;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysMovie;
import com.lnsf.rpc.domain.vo.SysMovieVo;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysMovieService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/Movie")
public class SysMovieController extends BaseController {

    @RpcReference(group = "Movie",version = "V1.0")
    private SysMovieService sysMovieService;

    @GetMapping("/sysMovie")
    public ResponseResult findAll(SysMovieVo sysMovieVo,@RequestParam(required = false)String orderByColumn,@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        List<SysMovie> data = sysMovieService.findAll(sysMovieVo);
        try{
            if ("movieScore".equals(orderByColumn) ){
                data.sort(Comparator.comparing(SysMovie::getMovieScore).reversed());
            }else{
                data.sort(Comparator.comparing(SysMovie::getReleaseDate).reversed());
            }
        }catch (Exception e){
            return getResult(data,pageSize,pageNum);
        }
        return getResult(data,pageSize,pageNum);


    }

    @GetMapping("/sysMovie/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysMovieService.findById(id));
    }

    @PostMapping("/sysMovie")
    public ResponseResult add(@Validated @RequestBody SysMovie sysMovie){
        return getResult(sysMovieService.add(sysMovie));
    }

    @PutMapping("/sysMovie")
    public ResponseResult update(@Validated @RequestBody SysMovie sysMovie){
        return getResult(sysMovieService.update(sysMovie));
    }

    @DeleteMapping("/sysMovie/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysMovieService.delete(ids));
    }

    @GetMapping("/sysMovie/find/{id}")
    public ResponseResult findMovieById(@PathVariable Long id){
        return getResult(sysMovieService.findMovieById(id));
    }

    @GetMapping("/sysMovie/rankingList/{listId}")
    public ResponseResult findRankingList(@PathVariable Integer listId) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(listId <= 0 || listId > 4){
            //暂时只支持4种榜单
            return ResponseResult.error("抱歉，暂时只支持4种榜单，id为[1,4]");
        }
        Method getList = sysMovieService.getClass().getMethod(MovieRankingList.listNames[listId - 1]);
        startPage();
        List<SysMovie> data = (List<SysMovie>)getList.invoke(sysMovieService);
        return getResult(data);
    }

}
