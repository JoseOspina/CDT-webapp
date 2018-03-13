<template lang="html">
  <div @click="$emit('click', $event)" class="this-button-container cursor-pointer">
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>

.this-button-container {
  background: url('./../../assets/btn-plus.png');
  background-size: 100% 100%;
  width: 36px;
  height: 36px;
}

.this-button-container:hover {
  background: url('./../../assets/btn-plus-hover.png');
  background-size: 100% 100%;
}

</style>
