<template lang="html">
  <div class="">
    <div class="w3-row header-container w3-padding">
      <select class="" name="">
        <option value="id1">My Organization</option>
        <option value="id1">My other Organization</option>
      </select>
      <button type="button" name="button">+</button>
      Contribution Design Toolkit
    </div>
    <div class="w3-cell-row middle-container">
      <div class="w3-cell nav-container">
        NAV PANE
      </div>
      <div class="w3-cell content-container">
        CONTENT
      </div>
    </div>
  </div>
</template>

<script>
import loggedUser from '@/mixins/loggedUser'

export default {
  mixins: [ loggedUser ]
}
</script>

<style scoped>

.header-container {
  background-color: rgb(125, 100, 164);
}

.nav-container {
  background-color: red;
  width: 400px;
}

.content-container {
  background-color: cyan;
}

</style>
