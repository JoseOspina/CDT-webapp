<template lang="html">
  <div id="chart-container"></div>
</template>

<script>
import { RadarChart } from '@/ext/radar-chart.js'

export default {

  data () {
    return {
      chartData:
        [
          [
            {area: 'Central ', value: 80},
            {area: 'Kirkdale', value: 40},
            {area: 'Kensington ', value: 40},
            {area: 'Everton ', value: 90},
            {area: 'Picton ', value: 60},
            {area: 'Riverside ', value: 20}
          ]
        ]
    }
  },
  mounted () {
    var width = 300
    var height = 300

    // Config for the Radar chart
    var config = {
      w: width,
      h: height,
      maxValue: 100,
      levels: 5,
      ExtraWidthX: 300
    }

    RadarChart.draw('#chart-container', this.chartData, config)
  }
}
</script>

<style scoped>

.axis {
  font: 15px sans-serif;
}
.axis path,
.axis line {
  fill: none;
  stroke: #D4D8DA;
  stroke-width: 2px;
  shape-rendering: crispEdges;
}

#chart-container {
  position: relative;
  top: 50px;
  left: 100px;
}

.toolTip {
  pointer-events: none;
  position: absolute;
  display: none;
  min-width: 50px;
  height: auto;
  background: none repeat scroll 0 0 #ffffff;
  padding: 9px 14px 6px 14px;
  border-radius: 2px;
  text-align: center;
  line-height: 1.3;
  color: #5B6770;
  box-shadow: 0px 3px 9px rgba(0, 0, 0, .15);
}

.toolTip:after {
  content: "";
  width: 0;
  height: 0;
  border-left: 12px solid transparent;
  border-right: 12px solid transparent;
  border-top: 12px solid white;
  position: absolute;
  bottom: -10px;
  left: 50%;
  margin-left: -12px;
}

.toolTip span {
  font-weight: 500;
  color: #081F2C;
}

</style>
