<template lang="html">
  <div class="btns-container app-color-2-co">
    <div @click="$emit('move-up')" class="w3-left cursor-pointer">
      <i class="fa fa-caret-up" aria-hidden="true"></i>
    </div>
    <div @click="$emit('remove')" class="w3-left cursor-pointer">
      <i class="fa fa-times-circle" aria-hidden="true"></i>
    </div>
    <div @click="$emit('move-down')" class="w3-left cursor-pointer">
      <i class="fa fa-caret-down" aria-hidden="true"></i>
    </div>
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>

.btns-container {
  font-size: 18px;
  width: 90px;
  margin: 0 auto;
  overflow: auto;
}

.btns-container > div {
  width: 30px;
  text-align: center;
}

.btns-container > div:hover {
  color: #FFDE17;
}

</style>
