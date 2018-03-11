<template lang="html">
  <div @click="$emit('click', $event)" class="this-button-container cursor-pointer w3-center noselect">
    <div class="this-button-content">
      <slot></slot>
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
  border-radius: 16px;
  padding: 3px;
  color: white;
  width: 200px;
}

.this-button-content {
  background-color: #2F2F2F;
  border-radius: 16px;
  font-size: 24px;
  padding: 14px 20px;
}

.this-button-content:hover {
  background-color: #544800;
}

</style>
