<template lang="html">
  <div class="box-container">
    <div class="box-content">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>

.box-container {
  background-color: #339470;
  padding: 2px;
  border-radius: 16px;
}

.box-content {
  font-size: 18px;
  padding: 3px 18px;
  height: 100%;
  background-color: #2F2F2F;
  border-radius: 16px;
  color: white;
}

</style>
