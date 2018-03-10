<template lang="html">
  <div class="w3-row">
    <div class="box-container">
      <div v-if="!showAsInput" class="box-content">
        <i v-if="restorable" @click="setCustomTitle()" class="fa fa-pencil" aria-hidden="true"></i>
        {{ value }}
      </div>
      <div v-else class="box-content">
        <i v-if="restorable" @click="customTitleBack()" class="w3-left fa fa-undo" aria-hidden="true"></i>
        <input v-if="!useTextArea" @input="$emit('input', $event.target.value)" :placeholder="placeholder" class="w3-input w3-round" type="text" name="" value="">
        <textarea v-else ref="textarea" @input="$emit('input', $event.target.value)" :placeholder="placeholder" class="w3-input w3-round"></textarea>
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
  background-color: #2F2F2F;
  border-radius: 16px;
}

.box-content input, .box-content textarea {
  border-width: 0px;
  background-color: #2F2F2F;
  color: white;
}

</style>
