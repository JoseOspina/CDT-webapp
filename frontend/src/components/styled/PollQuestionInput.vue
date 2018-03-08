<template lang="html">
  <div class="w3-row">
    <div class="box-container">
      <div v-if="!showAsInput" class="box-content">
        <i v-if="restorable" @click="setCustomTitle()" class="w3-left fa fa-pencil" aria-hidden="true"></i>
        <h2 class="w3-left">{{ text }}</h2>
      </div>
      <div v-else class="box-content">
        <i v-if="restorable" @click="customTitleBack()" class="w3-left fa fa-undo" aria-hidden="true"></i>
        <input v-if="!useTextArea" @input="$emit('input', $event.target.value)" :placeholder="placeholder" class="w3-input w3-round" type="text" name="" value="">
        <textarea v-else @input="$emit('input', $event.target.value)" :placeholder="placeholder" class="w3-input w3-round"></textarea>
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
    showAsInput: {
      type: Boolean,
      default: true
    },
    restorable: {
      type: Boolean,
      default: false
    },
    placeholder: {
      type: String,
      default: ''
    },
    useTextArea: {
      type: Boolean,
      default: false
    }
  },

  methods: {
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

.box-content input, .box-content textarea {
  border-width: 0px;
  background-color: #2F2F2F;
  color: white;
}

</style>
