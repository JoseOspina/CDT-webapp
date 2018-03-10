<template lang="html">
  <div @click="$emit('click', $event)" class="back-button-container cursor-pointer">
    <div class="back-button-content">
      <i class="fa fa-chevron-left" aria-hidden="true"></i>
    </div>
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>

.back-button-container {
  background: linear-gradient(-45deg, #FFDE17, #009B9C, #009B9C);
  width: 42px;
  height: 42px;
  border-radius: 21px;
  padding: 3px;
}

.back-button-content {
  background-color: #2F2F2F;
  color: #009B9C;
  height: 100%;
  width: 100%;
  border-radius: 21px;
  font-size: 24px;
  padding: 0px 0px 0px 8px;
}

.back-button-content:hover {
  background-color: #FFDE17;
}

</style>
