<template lang="html">
  <div class="w3-container">
    <h3>Poll Configuration</h3>
    <div class="w3-row">
      <datepicker v-model="openDate" placeholder="open date"></datepicker>
    </div>
    <div class="w3-row w3-margin-top">
      <datepicker v-model="closeDate" placeholder="close date"></datepicker>
    </div>
    <div class="w3-row w3-margin-top">
      <select class="w3-input">
        <option value="ANYONE_WITH_LINK">Anyone with the link</option>
        <option value="ANYONE_MEMBER">Any member in the organization</option>
        <option value="SPECIFIC_MEMBERS">Select specific members</option>
      </select>
    </div>
    <hr>
    <div class="w3-row w3-center">
      <button type="button" name="button">create</button>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      openDate: '',
      closeDate: ''
    }
  }
}
</script>

<style lang="css">
</style>
