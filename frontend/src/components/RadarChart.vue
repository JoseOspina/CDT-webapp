<template lang="html">
  <div id="chart-container"></div>
</template>

<script>
import { RadarChart } from '@/ext/radar-chart.js'

export default {

  props: {
    chartData: {
      type: Array
    }
  },

  data () {
    return {
      width: 300,
      height: 300
    }
  },

  methods: {
    draw () {
      var config = {
        w: this.width,
        h: this.height,
        levels: 5,
        TranslateX: 55,
        TranslateY: 25,
        ExtraWidthX: 140,
        ExtraWidthY: 100
      }

      RadarChart.draw('#chart-container', this.chartData, config)
    },
    checkWindowSize () {
      var windowWidth = window.innerWidth

      if (windowWidth < 800) {
        this.width = windowWidth / 2
        this.height = windowWidth / 2
      } else {
        this.width = 400
        this.height = 400
      }
      console.log('width:' + this.width)
      console.log('height:' + this.height)

      this.draw()
    }
  },

  mounted () {
    this.draw()
    this.checkWindowSize()
    window.addEventListener('resize', this.checkWindowSize)
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
  text-align: center;
  padding-top: 25px;
  padding-bottom: 25px;
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
