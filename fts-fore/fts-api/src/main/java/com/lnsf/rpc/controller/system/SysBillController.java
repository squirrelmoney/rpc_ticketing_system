package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysBill;
import com.lnsf.rpc.domain.SysMovie;
import com.lnsf.rpc.domain.SysSession;
import com.lnsf.rpc.domain.vo.SysBillVo;
import com.lnsf.rpc.exception.DataNotFoundException;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysBillService;
import com.lnsf.rpc.service.SysMovieService;
import com.lnsf.rpc.service.SysSessionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Bill")
public class SysBillController extends BaseController {

    @RpcReference(group = "Bill",version = "V1.0")
    private SysBillService sysBillService;

    @RpcReference(group = "Session",version = "V1.0")
    private SysSessionService sysSessionService;

    @RpcReference(group = "Movie",version = "V1.0")
    private SysMovieService sysMovieService;

    @GetMapping("/sysBill")
    public ResponseResult findAll(SysBill sysBill,@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        List<SysBill> data = sysBillService.findAll(sysBill);
        return getResult(data,pageSize,pageNum);
    }

    @GetMapping("/sysBill/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysBillService.findById(id));
    }

    @PostMapping("/sysBill")
    public ResponseResult add(@Validated @RequestBody SysBill sysBill){
        System.out.println(sysBill);
        Object obj = sysBillService.add(sysBill);
        if(obj instanceof Integer){
            return getResult((Integer) obj);
        }
        return getResult(obj);
    }

    @PutMapping("/refund")
    public ResponseResult refund(@RequestBody SysBill sysBill){
        int rows = sysBillService.update(sysBill);
        if(rows > 0){
            //更新场次的座位状态
            SysSession curSession = sysSessionService.findOne(sysBill.getSessionId());
            if(curSession == null){
                throw new DataNotFoundException("添加订单的场次没找到");
            }
            curSession.setSessionSeats(sysBill.getSysSession().getSessionSeats());
            sysSessionService.update(curSession);

            //更新电影票房
            SysMovie curMovie = sysMovieService.findOne(curSession.getMovieId());
            if(curMovie == null){
                throw new DataNotFoundException("添加订单的电影没找到");
            }
            int seatNum = sysBill.getSeats().split(",").length;//订单的座位数
            double price = curSession.getSessionPrice();
            curMovie.setMovieBoxOffice(curMovie.getMovieBoxOffice() - seatNum * price);
            sysMovieService.update(curMovie);
        }
        return getResult(rows);
    }

    @PutMapping("/sysBill")
    public ResponseResult update(@RequestBody SysBillVo sysBillVo){
        int rows = sysBillService.update(sysBillVo.getSysBill());
        if(rows > 0 && sysBillVo.getSysBill().getBillState()){
            //更新场次的座位状态
            SysSession curSession = sysSessionService.findOne(sysBillVo.getSysBill().getSessionId());
            if(curSession == null){
                throw new DataNotFoundException("添加订单的场次没找到");
            }
            curSession.setSessionSeats(sysBillVo.getSessionSeats());
            sysSessionService.update(curSession);

            //更新电影票房
            SysMovie curMovie = sysMovieService.findOne(curSession.getMovieId());
            if(curMovie == null){
                throw new DataNotFoundException("添加订单的电影没找到");
            }
            int seatNum = sysBillVo.getSysBill().getSeats().split(",").length;//订单的座位数
            double price = curSession.getSessionPrice();
            curMovie.setMovieBoxOffice(curMovie.getMovieBoxOffice() + seatNum * price);
            sysMovieService.update(curMovie);

        }
        return getResult(rows);
    }

    @DeleteMapping("/sysBill/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysBillService.delete(ids));
    }

    @GetMapping("todayBoxOffice")
    public ResponseResult todayBoxOffice(){
        return getResult(sysBillService.todayBoxOffice());
    }
    
}
