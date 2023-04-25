<template>
  <div class="navbar">
    <div class="btn-change-show">
      <button @click="changeIsShow()">☰</button>
    </div>
    <div class="logo">
      <span>LOGO</span>
    </div>
    <div class="avatar">
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      isShow: true
    }
  },
  methods: {
    changeIsShow() {
      this.isShow = !this.isShow;
      this.$emit('isShowUpdated', this.isShow);
    }
  },
}
</script>
<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  background-color: #468FAF;
  width: 100%;
  height: 70px;
  align-items: center;
}

.btn-change-show {
  margin-left: 20px;
}

.logo {
  color: white;
}

.avatar {
  margin-right: 20px;
  background-color: white;
  border-radius: 50%;
  height: 50px;
  width: 50px;
}
</style>

