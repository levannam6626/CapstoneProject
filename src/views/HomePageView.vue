<template>
  <div class="homepage" ref="homepage">
    <header id="header">
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
      <router-view name="banner" v-if="this.loginRole !== 'SELLER'"></router-view>
      <div v-if="this.loginRole === 'SELLER' || this.checkProductName === true" style="background-color: #fff; width: 100%; height: 2px;"></div>
      <div id="navbar">
        <NavBar v-on:showMenuUpdated="showMenuUpdatedInParent" @searchProduct="searchProduct" />
      </div>
    </header>
    <div class="content" id="content">
      <router-view name="theMenu" class="content-left" id="content-left"></router-view>
      <div class="content-right">
        <router-view :productNameSearch="productNameSearch" :reloadProduct="reloadProduct" ></router-view>
      </div>
    </div>
    <footer id="footer-contact" v-if="this.$store.state.auth.userAccount.role !== 'SELLER'">
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

import NavBar from "@/components/NavBar.vue";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faLocationDot, faEnvelope, faUser, faPhone } from "@fortawesome/free-solid-svg-icons";
import { faFacebook, faInstagram, faGoogle, faTelegram, faTwitter } from '@fortawesome/free-brands-svg-icons'
import store from "@/store";
library.add(faLocationDot, faEnvelope, faUser, faPhone, faFacebook, faInstagram, faGoogle, faTelegram, faTwitter);

export default {
  data() {
    return {
      loginRole: store.state.auth.userAccount.role,
      productNameSearch: '',
      reloadProduct: false,
      showMenu: true,
      showForm: false,
      sticky: null,
      banner: null
    };
  },
  computed: {
    checkProductName() {
      if(this.$route.params.productName !== undefined) {
        return true;
      }else {
        return false;
      }
    }
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
    NavBar
  },
  methods: {
    focusTop() {
      this.$refs.homepage.scrollIntoView({behavior: 'smooth'});
    },
    handleScroll() {
      let header = document.getElementById("header");
      let contact = document.getElementById("contact");
      let navbar = document.getElementById("navbar");
      let content = document.getElementById("content");
      let newBanner = document.getElementById("banner");
      if(newBanner === null){
        navbar.classList.remove("sticky-navbar");
        contact.classList.remove("sticky-contact");
        content.style.paddingTop = '20px';
        if (window.scrollY > header.offsetTop) {
          header.classList.add("sticky-header")
        }else {
          header.classList.remove("sticky-header");
        }
      }else{
        if(this.banner === null) {
          this.banner = newBanner.offsetHeight;
        }
        if(this.sticky === null || (this.banner !== newBanner.offsetHeight && newBanner.offsetHeight !== 0)) {
          this.sticky = newBanner.offsetHeight-37;
          this.banner = newBanner.offsetHeight;
        }
        if (window.scrollY > this.sticky) {
          navbar.classList.add("sticky-navbar")
          if(window.innerWidth <= 920) {
            content.style.paddingTop ='122px';
          }else {
            content.style.paddingTop ='82px';
          }
        } else {
          navbar.classList.remove("sticky-navbar");
          content.style.paddingTop = '20px';
          newBanner.style.display = 'block';
        }
        if (window.scrollY > contact.offsetTop) {
          contact.classList.add("sticky-contact")
        } else {
          contact.classList.remove("sticky-contact");
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
.sticky-header {
  position: fixed;
  z-index: 1;
  width: 100%;
}
.sticky-header + .content {
  padding-top: 112px !important;
}
.contact {
  background-color: red;
  color: #fff;
  width: 100%;
  height: 30px;
  z-index: 1;
  box-sizing: border-box;
  padding: 6px 15px;
  overflow: hidden;
}
.sticky-contact {
  position: fixed;
  top: 0px;
  left: 0px;
  z-index: 1;
}
.sticky-contact + .banner {
  padding-top: 30px;
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
  min-height: 100%;
}
.banner {
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
.sticky-navbar {
  position: fixed;
  top: 30px;
  width: 100%;
  border-top: solid 2px #fff;
  z-index: 1;
}
.content {
  display: flex;
  gap: 20px;
  width: 100%;
  box-sizing: border-box;
  padding: 20px 25px;
}
.content-right {
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
  border: solid 2px red;
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
    transform: translateX(0);
  }
  to {
    transform: translateX(50%);
  }
}
@media screen and (max-width: 920px) {
  .sticky-header + .content {
    padding-top: 152px !important;
  }
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

