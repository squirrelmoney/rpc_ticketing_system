package com.lnsf.rpc.constant;

/**
 * 电影排行榜
 */
public class MovieRankingList {

    public static final String[] listNames = new String[4];

    static {
        //热映口碑榜 昨日热映的电影里，按评分取前10
        listNames[0] = "hotMovieList";
        //国内票房榜 已上映的国内电影里，按票房取前10 国内电影 areaid = 1、5、6
        listNames[1] = "domesticBoxOfficeList";
        //欧美票房榜 已上映的欧美电影里，按票房取前10 欧美电影 areaid = 2、9、10、11、12、13、14
        listNames[2] = "europeanAndAmericanBoxOfficeList";
        //top100榜 所有已上映影片按评分、评分人数取前100
        listNames[3] = "top100List";
    }

}
