<template lang="html">
  <div class="slider-container ">
    <transition name="slideDownUp">
      <div v-if="show" class="w3-row w3-tag panel-container" :class="{'error-panel': isError, 'success-panel': isSuccess}">
        {{ message }}
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  props: {
    show: Boolean,
    message: String,
    panelType: {
      type: String,
      default: 'error'
    }
  },

  computed: {
    isError () {
      return this.panelType === 'error'
    },
    isSuccess () {
      return this.panelType === 'success'
    }
  }
}
</script>

<style scoped>

.panel-container {
  margin-top: 6px;
  width: 100%;
  background-color: #E93E70;
  border-radius: 12px;
}

</style>
