package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysActorMovie;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysActorMovieService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Actor")
public class SysActorMovieController extends BaseController {

    @RpcReference(group = "Actor",version = "V1.0")
    private SysActorMovieService sysActorMovieService;

    @GetMapping("/sysActorMovie")
    public ResponseResult findAll(SysActorMovie sysActorMovie,@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        return getResult(sysActorMovieService.findAll(sysActorMovie),pageSize,pageNum);
    }

    @PostMapping("/sysActorMovie")
    public ResponseResult add(@Validated @RequestBody SysActorMovie sysActorMovie){
        return getResult(sysActorMovieService.add(sysActorMovie));
    }

    @DeleteMapping("/sysActorMovie/{movieId}/{actorId}/{actorRoleId}")
    public ResponseResult delete(@PathVariable Long movieId, @PathVariable Long actorId, @PathVariable Long actorRoleId){
        return getResult(sysActorMovieService.delete(new SysActorMovie(movieId, actorId, actorRoleId)));
    }

}
