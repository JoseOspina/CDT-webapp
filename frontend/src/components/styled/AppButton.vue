<template lang="html">
  <div @click="$emit('click')" class="button-container no-select">
    <slot></slot>
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>

.button-container {
  text-align: center;
  background-color: #339470;
  padding: 2px;
  padding: 4px 16px;
  border-radius: 16px;
  cursor: pointer;
}

.button-container:hover {
  background-color: #009B9C;
}

</style>
