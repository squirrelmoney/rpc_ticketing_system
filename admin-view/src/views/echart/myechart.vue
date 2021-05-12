<template>
 <div>
    <!--面包屑导航区域-->
    <div class="board">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item>RPC图表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
  <div>
    <div class="Echarts" style="float:left">
      <div id="main" style="width: 600px; height: 600px"></div>
    </div>
   <div class="Echarts" style="float:left">
      <div id="main1" style="width: 600px; height: 600px"></div>
    </div>
  </div>
 </div>
</template>

<script>
export default {
  name: "Echarts",
  data() {
    return {
      rpcData: {
        avg: [],
        max: [],
        num: [],
        sum: [],
      },
    };
  },
  methods: {
    myEcharts() {
      // 基于准备好的dom，初始化echarts实例
      var myChart = this.$echarts.init(document.getElementById("main"));
      var myChart1 = this.$echarts.init(document.getElementById("main1"));
      // 指定图表的配置项和数据
      var option = {
          title: {
          text: "RPC调用时间分析",
          subtext: "数据来自接口调用时间统计",
        },
        legend: {},
        tooltip: {},
        dataset: {
          source: [
            ["rpc", "平均调用时间/ms", "调用总次数/次"],
            ["rpc框架平均调用时间/调用次数", this.rpcData.avg[1], this.rpcData.num[1]],
          ],
        },
        xAxis: { type: "category" },
        yAxis: {},
        // Declare several bar series, each will be mapped
        // to a column of dataset.source by default.
        series: [
          {
            type: "bar",
            label: {
              show: true,
              position: "top",
            },
          },
          {
            type: "bar",
            label: {
              show: true,
              position: "top",
            },
          },
        ],
        legend: {
          show: true,
        },
        animationDuration: 0,
        animationDurationUpdate: 3000,
        animationEasing: "linear",
        animationEasingUpdate: "linear",
      };

      var option2 = {
        title: {
          text: "RPC调用时间分析",
          subtext: "数据来自接口调用时间统计",
        },
        legend: {},
        tooltip: {},
        dataset: {
          source: [
            ["rpc", "调用总时间/ms", "调用最长时间/ms"],
            ["rpc框架总调用时间/最长调用时间", this.rpcData.sum[1], this.rpcData.max[1]],
          ],
        },
        xAxis: { type: "category" },
        yAxis: {},
        // Declare several bar series, each will be mapped
        // to a column of dataset.source by default.
        series: [
          {
            type: "bar",
            label: {
              show: true,
              position: "top",
            },
             itemStyle: {
                color: 'orange'
            }
          },
          {
            type: "bar",
            label: {
              show: true,
              position: "top",
            },
             itemStyle: {
                color: '#a90000'
            }
          },
        ],
        legend: {
          show: true,
        },
        animationDuration: 0,
        animationDurationUpdate: 3000,
        animationEasing: "linear",
        animationEasingUpdate: "linear",
      };
      let that = this;
      function run() {
        axios.get("/Log/getrpctime").then((resp) => {
          that.rpcData = resp.data.data;
        });
        myChart.setOption(option);
        myChart1.setOption(option1);
      }
      function getdata() {
        axios.get("/Log/getrpctime").then((resp) => {
          resp.data.data.avg;
        });
      }
      setTimeout(function () {
        run();
      }, 0);
      setInterval(function () {
        run();
      }, 3000);

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
      myChart1.setOption(option2);
    },

    getdata() {
      axios.get("/Log/getrpctime").then((resp) => {
        this.rpcData = resp.data.data;
        this.myEcharts();
      });
    },
  },

  mounted() {
    this.getdata();
  },
};
</script>

<style>
</style>
