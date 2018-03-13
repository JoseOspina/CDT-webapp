<template lang="html">
  <div @click="$emit('click', $event)" class="this-button-container cursor-pointer w3-center noselect">
    <slot></slot>
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>

.this-button-container {
  background: url('./../../assets/btn-text.png');
  background-size: 100% 100%;
  min-width: 200px;
  height: 68.38px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  font-size: 20px;
}

.this-button-container:hover {
  background: url('./../../assets/btn-text-hover.png');
  background-size: 100% 100%;
}

</style>
