<template lang="html">
  <div class="box-container">
    <div class="box-content">
      <slot>
      </slot>
    </div>
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>

.box-container {
  background: linear-gradient(to right, #FFDE17, #009B9C, #009B9C);
  padding: 2px;
  border-bottom-left-radius: 16px;
  border-bottom-right-radius: 16px;
}

.box-content {
  font-size: 22px;
  padding: 8px 26px;
  height: 100%;
  background-color: #2F2F2F;
  border-bottom-left-radius: 16px;
  border-bottom-right-radius: 16px;
}

</style>
