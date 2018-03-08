<template lang="html">
  <div class="w3-row">
    <div class="box-container">
      <div class="box-content">
        <select :value="value" @input="$emit('input', $event.target.value)" class="w3-input">
          <option v-for="el in elements" :key="el.value" :value="el.value">{{ el.text }}</option>
        </select>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    value: {
      type: String
    },
    elements: {
      type: Array
    }
  }
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
}

.box-content select {
  border-width: 0px;
  background-color: #2F2F2F;
  color: white;
}

</style>
