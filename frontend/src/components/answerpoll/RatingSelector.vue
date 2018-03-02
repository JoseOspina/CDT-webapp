<template lang="html">
  <div class="">
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
  }
}
</script>

<style scoped>

.rating-space {
  float: left;
  padding-right: 5px;
  padding-left: 5px;
  text-align: center;
  cursor: pointer;
}

.rating-space-hover {
  background-color: rgb(159, 124, 167);
}

.rating-space-selected {
  background-color: rgb(66, 3, 156);
}

.rating-dot {
}

</style>
