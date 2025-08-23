<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <!-- 左侧信息 -->
      <el-col :span="8">
        <el-card>
          <h2>{{ projectInfo.title }}</h2>
          <p>{{ projectInfo.desc }}</p>
          <p>版本：{{ projectInfo.version }}</p>
          <el-button type="primary" @click="goTarget(projectInfo.github)">访问仓库</el-button>
        </el-card>
      </el-col>

      <!-- 右侧图表 -->
      <el-col :span="16">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-card>
              <div ref="lineChart" style="height:300px;"></div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card>
              <div
                style="height:300px; display: flex; justify-content: center; align-items: center;"
              >
                <img
                  src="../assets/logo/logo.png"
                  alt="Logo"
                  style="max-width: 100%; max-height: 100%;"
                />
              </div>
            </el-card>
          </el-col>

          <el-col :span="12">
            <el-card>
              <div ref="barChart" style="height:300px;"></div>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "Dashboard",
  data() {
    return {
      projectInfo: {
        title: "后台管理系统",
        desc: "基于 Spring Boot + Vue 的后台管理模板",
        version: "v1.0.0",
        github: "https://github.com/your-repo"
      },
      chartInstances: {}
    };
  },
  mounted() {
    this.initLineChart();
    // this.initPieChart();
    this.initBarChart();
    window.addEventListener("resize", this.resizeCharts);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.resizeCharts);
  },
  methods: {
    goTarget(url) {
      window.open(url);
    },
    initLineChart() {
      const chart = echarts.init(this.$refs.lineChart);
      chart.setOption({
        title: { text: "" },
        tooltip: { trigger: "axis" },
        xAxis: {
          type: "category",
          data: []
        },
        yAxis: { type: "value" },
        series: [{ data: [120, 200, 150, 80, 70, 110, 130], type: "line" }]
      });
      this.chartInstances.line = chart;
    },
    initPieChart() {
      const chart = echarts.init(this.$refs.pieChart);
      chart.setOption({
        title: { text: "", left: "center" },
        tooltip: { trigger: "item" },
        series: [
          {
            type: "pie",
            radius: "50%",
            itemStyle: {
              borderWidth: 0,
              borderColor: "#fff"
            },
            data: [
              { value: 1048 },
              { value: 735 },
              { value: 580 },
              { value: 484 },
              { value: 300 }
            ]
          }
        ]
      });
      this.chartInstances.pie = chart;
    },
    initBarChart() {
      const chart = echarts.init(this.$refs.barChart);
      chart.setOption({
        title: { text: "" },
        tooltip: {},
        xAxis: {
          type: "category",
          data: []
        },
        yAxis: { type: "value" },
        series: [{ data: [5, 20, 36, 10, 10], type: "bar" }]
      });
      this.chartInstances.bar = chart;
    },
    resizeCharts() {
      Object.values(this.chartInstances).forEach(chart => chart.resize());
    }
  }
};
</script>

<style scoped>
.dashboard {
  padding: 20px;
}
</style>
