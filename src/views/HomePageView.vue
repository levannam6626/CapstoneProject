<template>
  <div class="homepage">
    <header class="banner">
      <p>BANNER</p>
    </header>
    <div>
      <MenuDetail />
    </div>
    <div class="navbar" style="display:flex;">
      <NavBar v-on:isShowUpdated="isShowUpdatedInParent" />
    </div>
    <div class="content" style="display:flex;">
      <div class="the-menu" style="width: 20%;" v-show="isShow">
        <TheMenu />
      </div>
      <div class="right" style="width: 100%;">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>
<script>
import TheMenu from '../components/TheMenu.vue';
import NavBar from '@/components/NavBar.vue';
import MenuDetail from '@/components/MenuDetail.vue';

export default {
  data() {
    return {
      isShow: true
    }
  },
  components: {
    TheMenu,
    NavBar,
    MenuDetail,
  },
  methods: {
    isShowUpdatedInParent: function (isShow) {
      this.isShow = isShow;
    }
  },
};
</script>
<style scoped>
.homepage{
  min-width: 500px;
  background-color: #DADADA;
}
.banner {
  text-align: center;
}

.banner p {
  margin: 0px;
  padding: 0px;
  font-size: 40px;
  font-weight: bold;
}

.content {
  margin-top: 1px;
}

.the-menu {
  margin-right: 10px;
  height: 100%;
}
</style>

