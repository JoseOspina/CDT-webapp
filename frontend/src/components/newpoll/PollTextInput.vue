<template lang="html">
  <div class="w3-row">
    <div class="box-container">
      <div v-if="!showAsInput" class="box-content app-color-2-br dark-1">
        <div class="value-content">
          <div @click="$emit('set-custom-value')"
            class="w3-left cursor-pointer app-color-white-1">
            <i v-if="restorable" class="fa fa-pencil" aria-hidden="true"></i>
          </div>
          <div class="w3-left w3-margin-left">
            <vue-markdown v-if="useTextArea" class="marked-text" :source="value"></vue-markdown>
            <span v-else>{{ value }}</span>
          </div>
        </div>
      </div>
      <div v-else class="box-content app-color-2-br dark-2">
        <div v-if="restorable" @click="$emit('custom-value-back')"
          class="custom-value-back app-color-white-1 cursor-pointer">
          <i class="fa fa-undo" aria-hidden="true"></i>
        </div>

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

.value-content {
  padding: 3px 0px;
  overflow: auto;
}

.custom-value-back {
  position: absolute;
  margin-left: -12px;
  margin-top: -6px;
}

</style>
