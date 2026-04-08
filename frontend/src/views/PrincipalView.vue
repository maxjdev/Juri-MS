<script lang="ts">
import { defineComponent } from 'vue'
import Principal from '../components/principal/Principal.vue'

export default defineComponent({
  name: 'PrincipalView',
  components: {
    Principal,
  },
})
</script>

<template>
  <Principal />
</template>