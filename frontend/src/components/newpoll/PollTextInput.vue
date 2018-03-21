<template lang="html">
  <div class="w3-row">
    <div class="box-container">
      <div class="box-content app-color-2-br dark-2">
        <input v-if="!useTextArea" :value="value"
          @input="$emit('input', $event.target.value)" :placeholder="placeholder"
          class="w3-input w3-round" type="text" name="" >

        <app-markdown-editor v-else :value="value"  @input="$emit('input', $event)"
          :placeholder="placeholder">
        </app-markdown-editor>

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
  }
}
</script>

<style scoped>

.box-container {
  background-color: rgba(0, 0, 0, 0.05);
  padding: 2px;
  border-radius: 3px;
}

.box-container .fa {
  color: rgba(255, 255, 255, 0.23);
}

.box-container .fa:hover {
  color: white;
}

.box-content {
  font-size: 18px;
  padding: 3px 18px;
  height: 100%;
  border-radius: 3px;
  border-style: solid;
  border-width: 2px;
}

.box-content input, .box-content textarea {
  border-width: 0px;
  background-color: #2F2F2F;
  color: white;
}

</style>
