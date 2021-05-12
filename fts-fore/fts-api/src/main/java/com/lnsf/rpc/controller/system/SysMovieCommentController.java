package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysMovie;
import com.lnsf.rpc.domain.SysMovieComment;
import com.lnsf.rpc.exception.DataNotFoundException;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysMovieCommentService;
import com.lnsf.rpc.service.SysMovieService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/Movie")
public class SysMovieCommentController extends BaseController {

    @RpcReference(group = "Movie",version = "V1.0")
    private SysMovieCommentService sysMovieCommentService;

    @RpcReference(group = "Movie",version = "V1.0")
    private SysMovieService sysMovieService;

    @GetMapping("/sysMovieComment")
    public ResponseResult findAll(@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        List<SysMovieComment> data = sysMovieCommentService.findAll();
        return getResult(data,pageSize,pageNum);
    }

    @GetMapping("/sysMovieComment/{movieId}")
    public ResponseResult findByMovieId(@PathVariable Long movieId){
        return getResult(sysMovieCommentService.findByMovieId(movieId));
    }

    @GetMapping("/sysUserComment/{userId}")
    public ResponseResult findByUserId(@PathVariable Long userId){
        return getResult(sysMovieCommentService.findByUserId(userId));
    }

    @PostMapping("/sysMovieComment")
    public ResponseResult add(@Validated @RequestBody SysMovieComment sysMovieComment){
        int rows = sysMovieCommentService.add(sysMovieComment);
        if(rows > 0){
            //添加成功 修改电影评分和评论人数
            SysMovie movie = sysMovieService.findById(sysMovieComment.getMovieId());
            Integer movieRateNum = movie.getMovieRateNum();
            Double movieScore = movie.getMovieScore();
            movieScore = (movieScore * movieRateNum + sysMovieComment.getScore()) / (++movieRateNum);

            movie.setMovieScore(movieScore);
            movie.setMovieRateNum(movieRateNum);
            sysMovieService.update(movie);
        }
        return getResult(rows);
    }

    @PutMapping("/sysMovieComment")
    public ResponseResult update(@Validated @RequestBody SysMovieComment sysMovieComment){
        //查询原评论信息
        SysMovieComment originalComment = sysMovieCommentService.findOne(sysMovieComment);
        if(originalComment == null){
            throw new DataNotFoundException("原始数据查询失败，请确保输入有效");
        }

        int rows = sysMovieCommentService.update(sysMovieComment);
        if(rows > 0){
            //修改成功，修改电影评分
            SysMovie movie = sysMovieService.findById(sysMovieComment.getMovieId());
            Integer movieRateNum = movie.getMovieRateNum();
            Double movieScore = movie.getMovieScore();
            movieScore = (movieScore * movieRateNum + sysMovieComment.getScore() - originalComment.getScore()) / movieRateNum;
            //更新评分
            movie.setMovieScore(movieScore);
            sysMovieService.update(movie);
        }
        return getResult(rows);
    }

    @PostMapping("/sysMovieComment/delete")
    public ResponseResult delete(@RequestBody SysMovieComment[] pks){
        int rows = sysMovieCommentService.delete(pks);
        System.out.println(Arrays.toString(pks));
        if(rows > 0){
            //删除成功，修改删除评论后的评分
            System.out.println("111");
            for(SysMovieComment comment : pks){
                System.out.println(comment);
                SysMovie movie = sysMovieService.findById(comment.getMovieId());
                Integer movieRateNum = movie.getMovieRateNum();
                Double movieScore = movie.getMovieScore();
                if(movieRateNum <= 1){
                    movieRateNum = 0;
                    movieScore = 0.0;
                }else{
                    movieScore = (movieScore * movieRateNum - comment.getScore()) / (--movieRateNum);
                }

                movie.setMovieRateNum(movieRateNum);
                movie.setMovieScore(movieScore);
                sysMovieService.update(movie);
            }
        }
        return getResult(rows);
    }

}
