<template>
  <div>
    <!--面包屑导航区域-->
    <div class="board">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
         <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item>RPC调用日志</el-breadcrumb-item>
      </el-breadcrumb>
    </div>


    <!--卡片视图-->
    <!-- <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="28">
            <el-select v-model="queryInfo.codec" @change="getList()" placeholder="请选择通信方式" clearable >
            <el-option
                v-for="item in tranList"
                :key="item.value"
                :label="item.lable"
                :value="item.value">
            </el-option>
          </el-select>
        </el-col>
      </el-row> -->

      <!--影院区域列表-->
      <el-table :data="loglist" style="width: 100%" border stripe >
        <el-table-column prop="serviceName" label="服务名称" ></el-table-column>
         <el-table-column prop="codec" label="通信服务" ></el-table-column>
        <el-table-column prop="receiveMsg" label="请求信息" ></el-table-column>
        <el-table-column prop="responMsg" label="返回信息" ></el-table-column>
        <el-table-column prop="costTime" label="花费时间/mS" ></el-table-column>
      </el-table>

      <!--分页区域-->
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.pageNum"
          :page-sizes="[5, 10, 15]"
          :page-size="queryInfo.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>

    </el-card>

  </div>
</template>

<script>
import global from "@/assets/css/global.css"
export default {
  name: "SysLog",
  data() {
    return {
      queryInfo: {
        codec: '',
        pageNum: 1,
        pageSize: 10
      },
      loglist: [],
      total: 0,
      tranList: [{lable:"netty",value:"netty"},{lable:"socket",value:"socket"}]
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      const _this = this;
      axios.get('/Log/syslog',{params: _this.queryInfo}).then(resp => {
        console.log(resp)
        _this.loglist = resp.data.data;
        _this.total = resp.data.total;
        _this.queryInfo.pageSize = resp.data.pageSize;
        _this.queryInfo.pageNum = resp.data.pageNum;
      })
    },
     handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize
      this.getList()
    },
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage
      this.getList()
    },
  }
}
</script>

<style scoped>

</style>