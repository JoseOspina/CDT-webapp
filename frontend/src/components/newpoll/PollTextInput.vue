<template lang="html">
  <div class="w3-row">
    <div class="box-container">
      <div v-if="!showAsInput" class="box-content dark-1">
        <div class="value-content">
          <div @click="$emit('set-custom-value')"
            class="w3-left cursor-pointer app-color-white-1">
            <i v-if="restorable" class="fa fa-pencil" aria-hidden="true"></i>
          </div>
          <div class="w3-left w3-margin-left">
            <span>{{ value }}</span>
          </div>
        </div>
      </div>
      <div v-else class="box-content dark-2">
        <div v-if="restorable" @click="$emit('custom-value-back')"
          class="custom-value-back app-color-white-1 cursor-pointer">
          <i class="fa fa-undo" aria-hidden="true"></i>
        </div>
        <input v-if="!useTextArea" :value="value" @input="$emit('input', $event.target.value)" :placeholder="placeholder" class="w3-input w3-round" type="text" name="" >
        <textarea v-else ref="textarea" :value="value" @input="$emit('input', $event.target.value)" :placeholder="placeholder" class="w3-input w3-round"></textarea>
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

  watch: {
    value () {
      this.checkHeight()
    }
  },

  methods: {
    checkHeight () {
      if (this.$refs.textarea && this.useTextArea) {
        if (this.value !== '') {
          /* resize text area */
          this.$refs.textarea.style.height = (this.$refs.textarea.scrollHeight) + 'px'
        } else {
          this.$refs.textarea.style.height = '90px'
        }
      }
    }
  },

  mounted () {
    this.$nextTick(() => {
      this.checkHeight()
    })
  }
}
</script>

<style scoped>

textarea {
  resize: vertical;
  min-height: 35px;
}

.box-container {
  background-color: #339470;
  padding: 2px;
  border-radius: 16px;
}

.box-content {
  font-size: 18px;
  padding: 3px 18px;
  height: 100%;
  border-radius: 16px;
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
