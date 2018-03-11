<template lang="html">
  <div @click="$emit('click', $event)" class="this-button-container cursor-pointer">
    <div class="this-button-content">
      <i class="fa fa-plus" aria-hidden="true"></i>
    </div>
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>

.this-button-container {
  background: linear-gradient(135deg, #FFDE17, #009B9C);
  width: 42px;
  height: 42px;
  border-radius: 21px;
  padding: 3px;
}

.this-button-content {
  background-color: #2F2F2F;
  color: #009B9C;
  height: 100%;
  width: 100%;
  border-radius: 21px;
  font-size: 24px;
  padding: 0px 0px 0px 8px;
}

.this-button-content:hover {
  background-color: #544800;
}

</style>
