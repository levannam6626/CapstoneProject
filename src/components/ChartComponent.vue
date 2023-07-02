<template>
    <div>
      <canvas ref="lineChart" height="300"></canvas>
    </div>
  </template>
  
  <script>
  import { Chart } from 'chart.js'
  
  export default {
    props: ['data', 'type', 'options'],
    mounted() {
      const canvas = this.$refs.lineChart
      const ctx = canvas.getContext('2d')
  
      this.chart = new Chart(ctx, {
        type: this.type,
        data: this.data,
        options: this.options
      })
    }
  }
  </script>