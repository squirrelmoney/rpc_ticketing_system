package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysCinema;
import com.lnsf.rpc.domain.SysSession;
import com.lnsf.rpc.domain.vo.SysCinemaVo;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysCinemaService;
import com.lnsf.rpc.service.SysSessionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Cinema")
public class SysCinemaController extends BaseController {

    @RpcReference(group = "Cinema",version = "V1.0")
    private SysCinemaService sysCinemaService;

    @RpcReference(group = "Session",version = "V1.0")
    private SysSessionService sysSessionService;

    @GetMapping("/sysCinema")
    public ResponseResult findAll(SysCinemaVo sysCinemaVo,@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        return getResult(sysCinemaService.findAll(sysCinemaVo),pageSize,pageNum);
    }

    @GetMapping("/sysCinema/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysCinemaService.findById(id));
    }

    @PostMapping("/sysCinema")
    public ResponseResult add(@Validated @RequestBody SysCinema sysCinema){
        return getResult(sysCinemaService.add(sysCinema));
    }

    @PutMapping("/sysCinema")
    public ResponseResult update(@Validated @RequestBody SysCinema sysCinema){
        return getResult(sysCinemaService.update(sysCinema));
    }

    @DeleteMapping("/sysCinema/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysCinemaService.delete(ids));
    }

    @GetMapping(value = {"/sysCinema/find/{cinemaId}/{movieId}", "/sysCinema/find/{cinemaId}"})
    public ResponseResult findCinemaById(@PathVariable Long cinemaId, @PathVariable(required = false) Long movieId){
        SysCinema cinema = sysCinemaService.findCinemaById(cinemaId);
        if(movieId == null || movieId == 0){
            movieId = cinema.getSysMovieList() != null ? cinema.getSysMovieList().get(0).getMovieId() : 0;
        }
        List<SysSession> sessions = null;
        if(movieId != null && movieId != 0){
            sessions = sysSessionService.findByCinemaAndMovie(cinemaId, movieId);
        }

        HashMap<String, Object> response = new HashMap<>();
        response.put("cinema", cinema);
        response.put("sessions", sessions);
        return getResult(response);

    }

}
