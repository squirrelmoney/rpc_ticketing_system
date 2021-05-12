package com.lnsf.rpc.controller.system;

import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysHall;
import com.lnsf.rpc.domain.SysSession;
import com.lnsf.rpc.domain.vo.SysSessionVo;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysHallService;
import com.lnsf.rpc.service.SysSessionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Hall")
public class SysHallController extends BaseController {

    @RpcReference(group = "Hall",version = "V1.0")
    private SysHallService sysHallService;

    @RpcReference(group = "Session",version = "V1.0")
    private SysSessionService sysSessionService;

    @GetMapping("/sysHall")
    public ResponseResult findAll(@RequestBody(required = false) SysHall sysHall,@RequestParam(required = false,defaultValue = "") String cinemaId,@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        SysHall sysHall1 = new SysHall();
        if (sysHall == null && cinemaId != ""){
            try{
                sysHall1.setCinemaId(Long.valueOf(cinemaId));
                return getResult(sysHallService.findAll(sysHall1),pageSize,pageNum);
            }catch (Exception e){
                return getResult(sysHallService.findAll(sysHall),pageSize,pageNum);
            }

        }else{
            return getResult(sysHallService.findAll(sysHall),pageSize,pageNum);
        }

    }

    @GetMapping("/sysHall/{cinemaId}/{hallId}")
    public ResponseResult findByPrimaryKey(@PathVariable Long cinemaId, @PathVariable Long hallId){
        SysHall sysHall = new SysHall();
        sysHall.setCinemaId(cinemaId);
        sysHall.setHallId(hallId);
        return getResult(sysHallService.findByCinemaIdAndHallId(sysHall));
    }

    @PostMapping("/sysHall")
    public ResponseResult add(@Validated @RequestBody SysHall sysHall){
        return getResult(sysHallService.add(sysHall));
    }

    @PutMapping("/sysHall")
    public ResponseResult update(@Validated @RequestBody SysHall sysHall){
        //查出原有影厅信息
        SysHall orgHall = sysHallService.findByCinemaIdAndHallId(sysHall);
        int rows = sysHallService.update(sysHall);
        if(rows > 0){
            //修改成功
            if(sysHall.getRowNums() != orgHall.getRowNums() || sysHall.getSeatNumsRow() != orgHall.getSeatNumsRow() || sysHall.getSeatNums() != orgHall.getSeatNums()) {
                //同步更新对应场次的座位
                SysSessionVo sysSessionVo = new SysSessionVo();
                sysSessionVo.setCinemaId(sysHall.getCinemaId());
                sysSessionVo.setHallId(sysHall.getHallId());
                //查出该影厅的所有场次
                List<SysSession> sessions = sysSessionService.findByVo(sysSessionVo);
                if (!CollectionUtils.isEmpty(sessions)) {
                    //存在场次则更新座位信息
                    for (SysSession session : sessions) {
                        session.setSessionSeats(sysHall.getSeatState());
                        sysSessionService.update(session);
                    }
                }
            }
        }
        return getResult(rows);
    }

    @PostMapping("/sysHall/delete")
    public ResponseResult delete(@RequestBody SysHall[] sysHalls){
        return getResult(sysHallService.delete(sysHalls));
    }

}
