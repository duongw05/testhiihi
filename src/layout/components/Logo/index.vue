<template>
  <div class="logo-container">
<!--    <img src="@/assets/logo/logo.png" alt="">-->
    <h1 style="margin-left: 5px;margin-bottom: 0px;" v-if="!isCollapse" @click="go">{{ $t(systemTitle) }}</h1>
  </div>
</template>

<script lang="ts">
import {defineComponent, computed} from 'vue'
import {useStore} from 'vuex'
import {systemTitle} from '@/config'

export default defineComponent({
  setup() {
    const store = useStore()
    const isCollapse = computed(() => store.state.app.isCollapse)
    return {
      isCollapse,
      systemTitle
    }
  },
  methods: {
    go() {
      this.$router.push('/')
    }
  }
})
</script>

<style lang="scss" scoped>
.logo-container {
  height: 60px;
  display: flex;
  padding-left: 20px;
  align-items: center;
  //justify-content: center;
  //width: 100%;
  cursor: pointer;
  background-color: var(--system-logo-background);

  h1 {
    font-size: 18px;
    white-space: nowrap;
    color: var(--system-logo-color);
  }
}
</style>
