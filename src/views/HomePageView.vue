<template>
  <div class="homepage" ref="homepage">
    <div class="contact" id="contact">
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
    <header class="banner" id="banner">
      <figure>
        <img src="../assets/banner-e-commerce1.png" alt="" />
      </figure>
    </header>
    <div id="navbar" style="display: flex">
      <NavBar v-on:showMenuUpdated="showMenuUpdatedInParent" @searchProduct="searchProduct" @actionForm="actionForm"/>
    </div>
    <div class="content" id="content">
      <div class="product" @click="closeForm($event)" v-show="showForm">
        <router-view class="product-action" name="productAction" @reloadProductList="reloadProductList" @actionForm="actionForm"></router-view>
      </div>
      <div class="content-left" v-show="showMenu" id="content-left">
        <TheMenu />
      </div>
      <div class="content-right">
        <router-view :productNameSearch="productNameSearch" :reloadProduct="reloadProduct" @actionForm="actionForm"></router-view>
      </div>
    </div>
    <footer id="footer-contact">
      <div class="footer-connect">
        <h2>Connect</h2>
        <hr>
        <div class="connect-icon">
          <font-awesome-icon class="icon" icon="fa-brands fa-facebook" />
          <font-awesome-icon class="icon" icon="fa-brands fa-instagram" />
          <font-awesome-icon class="icon" icon="fa-brands fa-google" />
          <font-awesome-icon class="icon" icon="fa-brands fa-telegram" />
          <font-awesome-icon class="icon" icon="fa-brands fa-twitter" />
        </div>
      </div>
      <div class="footer-information">
        <h2>Contact</h2>
        <hr>
        <div class="information">
          <div class="information-thanks">
            <p>We are always welcome and ready to advise and assist you!</p>
          </div>
          <div class="information-address">
            <font-awesome-icon class="information-address-icon" icon="fa-solid fa-location-dot" />
            <span>85 NguyenLuongBang - HoaKhanhBac - LienChieu - DaNang </span>
          </div>
          <div class="information-phone">
            <font-awesome-icon class="information-phone-icon" icon="fa-solid fa-phone" />
            <span>0763.096.798</span>
          </div>
          <div class="information-email">
            <font-awesome-icon class="information-email-icon" icon="fa-solid fa-envelope" />
            <span>vannamka6626@gmail.com</span>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>
<script>
import TheMenu from '../components/TheMenu.vue';
import NavBar from "@/components/NavBar.vue";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faLocationDot, faEnvelope, faUser, faPhone } from "@fortawesome/free-solid-svg-icons";
import { faFacebook, faInstagram, faGoogle, faTelegram, faTwitter } from '@fortawesome/free-brands-svg-icons'
library.add(faLocationDot, faEnvelope, faUser, faPhone, faFacebook, faInstagram, faGoogle, faTelegram, faTwitter);

export default {
  data() {
    return {
      productNameSearch: '',
      reloadProduct: false,
      showMenu: true,
      showForm: false,
      sticky: null,
      banner: null,
    };
  },
  watch: {
    productNameSearch() {
      if(this.productNameSearch === '')
      {
        this.$router.push('/');
      }else {
        this.$router.push('/search-product/' + this.productNameSearch);
      }
    },
    reloadProduct() {
      this.$router.push('/');
      console.log(this.reloadProduct);
    }
  },
  created () {
    window.addEventListener('scroll', this.handleScroll);
  },
  mounted() {
    this.focusTop();
  },
  unmounted () {
    window.removeEventListener('scroll', this.handleScroll);
  },
  components: {
    TheMenu,
    NavBar,
  },
  methods: {
    focusTop() {
      this.$refs.homepage.scrollIntoView({behavior: 'smooth'});
    },
    handleScroll() {
      var navbar = document.getElementById("navbar");
      var newBanner = document.getElementById("banner");
      if(this.showForm === true) {
        navbar.classList.remove("sticky");
      }else{
        if(this.banner === null) {
          this.banner = newBanner.offsetHeight;
        }
        if(this.sticky === null || this.banner !== newBanner.offsetHeight) {
          this.sticky = newBanner.offsetHeight-10;
          this.banner = newBanner.offsetHeight;
        }
        if (window.scrollY >= this.sticky) {
          navbar.classList.add("sticky")
        } else {
          navbar.classList.remove("sticky");
        }
      }
    },
    showMenuUpdatedInParent: function () {
      const contentLeft = document.getElementById('content-left');
      console.log(contentLeft.style.display)
      if(contentLeft.style.display === 'none') {
        this.showMenu = true;
      }else {
        this.showMenu = false;
      }
    },
    searchProduct(productName) {
      this.productNameSearch = productName;
    },
    reloadProductList() {
      this.reloadProduct = !this.reloadProduct;
    },
    actionForm(status) {
      this.showForm = status;
      var navbar = document.getElementById("navbar");
      navbar.classList.remove("sticky");
      // if(!status) {
      //   this.$router.push(this.$router.options.history.state.back);
      // }
    },
    closeForm(event) {
      if (event.target.className === "product") {
        this.$router.push('/');
        this.showForm = false;
      }
    },
  }
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
  position: relative;
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
.sticky {
  position: fixed;
  top: 35px;
  width: 100%;
  border-top: solid 2px #fff;
  z-index: 1;
}
#navbar {
  margin-top: -5px;
}
.sticky + .content {
  padding-top: 76px;
}
.content {
  display: flex;
  gap: 20px;
  width: 100%;
  box-sizing: border-box;
  padding: 20px 40px 0px;
}
.content-right {
  width: 100%;
  height: 100%;
}
.product {
  position: absolute;
  background-color: rgba(0,0,0,0.5);
  left: 0;
  top: 0px;
  z-index: 0;
  width: 100%;
  height: 100%;
}
.product-action {
  position: absolute;
  top: 10%;
  left: 15%;
  width: 70%;
  z-index: 1;
}
footer {
  width: 100%;
  background-color: #232F3E;
  display: flex;
  justify-content: space-between;
  box-sizing: border-box;
  height: auto;
  padding: 10px;
  color: white;
  margin-top: 15px;
}
footer #contact h2{
  box-sizing: border-box;
  padding-left: 10px;
}
footer hr {
  border:solid 2px red;
}
.footer-connect {
  width: 30%;
}
.footer-information {
  width: 65%;
}
.connect-icon {
  display: flex;
  justify-content: space-between;
  margin-top: 40px;
  cursor: pointer;
  box-sizing: border-box;
  padding: 0px 10px;
  font-size: 24px;
  transition: 1.5s;
  flex-wrap: wrap;
}
.connect-icon .icon:hover {
  color: red;
  transform: scale(1.5);
}
.information {
  display: grid;
  gap: 5px;
  margin-top: 20px;
  box-sizing: border-box;
  padding-left: 20px;
}
.information-address-icon, .information-email-icon, .information-phone-icon {
  margin-right: 5px;
}
@keyframes addressmove {
  from {
    left: 0px;
  }
  to {
    left: 50%;
  }
}
@media screen and (max-width: 950px) {
  .content-left {
    display: none;
  }
  .content-right {
    margin: 0px;
  }
  .content {
    gap: 0px;
  }
}
@media screen and (max-width: 775px) {
  .connect-icon {
    gap: 10px;
  }
  .information {
    padding-left: 0px;
  }
}
</style>

