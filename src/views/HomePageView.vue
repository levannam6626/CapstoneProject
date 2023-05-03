<template>
  <div class="homepage">
    <div class="contact">
      <div class="move">
        <div class="address">
          <font-awesome-icon
            class="address-icon"
            icon="fa-solid fa-location-dot"
          />
          <span>85 NguyenLuongBang - HoaKhanhBac - LienChieu - DaNang </span>
        </div>
        <div class="email">
          <font-awesome-icon class="email-icon" icon="fa-solid fa-envelope" />
          <span>vannamka6626@gmail.com</span>
        </div>
      </div>
    </div>
    <header class="banner">
      <figure>
        <img src="../assets/banner-e-commerce1.png" alt="" />
      </figure>
    </header>
    <div class="navbar" style="display: flex">
      <NavBar v-on:showMenuUpdated="showMenuUpdatedInParent" />
    </div>
    <div class="content" style="display: flex">
      <div class="content-left" v-show="showMenu">
        <TheMenu />
      </div>
      <div class="content-right">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>
<script>
import TheMenu from '../components/TheMenu.vue';
import NavBar from "@/components/NavBar.vue";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faLocationDot, faEnvelope, faUser } from "@fortawesome/free-solid-svg-icons";
library.add(faLocationDot, faEnvelope, faUser);

export default {
  data() {
    return {
  
      showMenu: true,
    };
  },
  components: {
    TheMenu,
    NavBar,
  },
  methods: {
    showMenuUpdatedInParent: function (showMenu) {
      this.showMenu = showMenu;
    },
  },
};
</script>
<style scoped>
.contact {
  background-color: red;
  color: #fff;
  width: 100%;
  height: 30px;
  position: fixed;
  z-index: 1;
  top: 0px;
  left: 0px;
  box-sizing: border-box;
  padding: 6px 15px;
}
.move {
  display: flex;
  position: relative;
  animation: addressmove 5s infinite;
}
.address {
  height: 100%;
  box-sizing: border-box;
  min-width: 420px;
}
.email {
  margin-left: 20px;
  min-width: 280px;
}
.contact span {
  margin-left: 5px;
}
.homepage {
  width: 100%;
  background-color: #dadada;
}
.banner {
  margin-top: 27px;
  text-align: center;
}
.banner figure {
  padding: 0px;
  margin: 0px;
  width: 100%;
  box-sizing: border-box;
  max-height: 96%;
}
.banner figure img {
  width: 100%;
}
.navbar {
  margin-top: -5px;
}
.content {
  width: 100%;
  box-sizing: border-box;
  padding: 20px 40px 0px;
}
.content-right {
  width: 100%;
  height: 100%;
}
.content-left {
  width: 22%;
  margin-right: 20px;
  min-width: 211px;
}
@keyframes addressmove {
  from {
    left: 0px;
  }
  to {
    left: 50%;
  }
}
@media screen and (max-width: 500px) {
  .content-left {
    display: none;
  }
  .content-right {
    margin: 0px;
  }
}
</style>
