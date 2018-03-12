<template lang="html">
  <div class="rating-container">
    <div v-for="ix in [1, 2, 3, 4, 5]"
      @click="clicked(ix)" :key="ix" class="rating-space"
      @mouseenter="hoverEnter(ix)"
      @mouseleave="hoverLeave(ix)"
      :class="{'rating-space-hover': hoveringOver >= ix, 'rating-space-selected': selected >= ix && hoveringOver === 0}">
      <i class="fa fa-circle rating-dot" aria-hidden="true"></i>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    valueInit: {
      type: Number
    }
  },
  data () {
    return {
      hoveringOver: 0,
      selected: 0
    }
  },

  methods: {
    clicked (n) {
      this.selected = n
      this.$emit('input', n)
    },
    hoverEnter (n) {
      this.hoveringOver = n
    },
    hoverLeave (n) {
      this.hoveringOver = 0
    }
  },

  created () {
    if (this.valueInit) {
      this.selected = this.valueInit
    }
  }
}
</script>

<style scoped>

.rating-container {
  padding-top: 12px;
  overflow: auto;
  font-size: 18px;
}

.rating-space {
  float: left;
  padding-right: 8px;
  padding-left: 8px;
  text-align: center;
  cursor: pointer;
  color: #3F3E3E;
}

.rating-space-hover {
  color: #009B9C;
}

.rating-space-selected {
  color: #009B9C;
}

.rating-dot {
}

</style>
