<template lang="html">
  <div class="">
    <div class="w3-col m8">
      {{ question.text }}
    </div>
    <div class="w3-col m4">
      answer
    </div>
  </div>
</template>

<script>
export default {
  props: {
    question: {
      type: Object
    }
  }
}
</script>

<style lang="css">
</style>
