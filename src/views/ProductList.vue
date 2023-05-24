<template>
  <header>
    <h3 v-if="showList===true">PRODUCT LIST</h3>
    <h3 v-else>
      <ul class="router-menu">
        <li v-for="(path,index) in this.$router.currentRoute.value.matched" :key="index">
          <span v-if="index >0" style="margin: 0px 5px;">></span>
          <a :href="path.name" v-if="this.$router.currentRoute.value.matched.length !==index+1">{{ path.name }}</a>
          <span v-else style="color: #fff;">{{ this.product.productName }}</span>
        </li>
      </ul>
    </h3>
  </header>
  <div class="content" v-if="showList===true">
    <div v-for="(product, index) in this.products" :key="index">
      <ProductItem v-on:showDetail="showDetailInparend" :product="product" />
    </div>
  </div>
  <div class="detail" v-else>
    <router-view :product="this.product"></router-view>
  </div>
  <footer>

  </footer>
</template>
<script>
import { mapActions } from 'vuex';
import ProductItem from '@/components/ProductItem.vue';
import store from '@/store';
export default {
  data() {
    return {
      products: 
      [{
        productName: "Raspberry pi 3",
        productDescription: "Raspberry pi 3",
        productPrice: "50000",
        productCategory: "productCategory",
        productUrl: '../assets/raspberrypi3.jpg',
      },
      {
        productName: "Raspberry pi 4",
        productDescription: "Raspberry pi 4",
        productPrice: "50000",
        productCategory: "productCategory",
        productUrl: '../assets/signin-image.jpg',
      },
      {
        productName: "Raspberry pi 2",
        productDescription: "Raspberry pi 2",
        productPrice: "50000",
        productCategory: "productCategory",
        productUrl: '../assets/signin-image.jpg',
      }],
      product: {},
      categoryId: store.state.product.categoryId,
      showList: true,
      showDetail: false,
    };
  },
  components: {
    ProductItem,
  },
  methods: {
    ...mapActions('product',['loadProductListAction']),
    showDetailInparend(selectedProduct) {
      this.product = selectedProduct;
      this.showList = false;
      this.showDetail = true;
      console.log(this.$router.currentRoute.value);
      console.log(this.$router.currentRoute.value.matched);
    },
    loadProductList(categoryId) {
      this.loadProductListAction(categoryId)
    },
  },
  mounted() {
    console.log(this.categoryId);
    this.loadProductList(this.categoryId);
  },
}
</script>
<style scoped>
h3 {
  background-color: red;
  height: 50px;
  color: white;
  font-weight: 400;
  box-sizing: border-box;
  padding: 15px 15px;
  box-shadow: black 0px 2px;
  margin: 0px;
  margin-bottom: 2px;
}
.router-menu {
  display: flex;
  padding: 0px;
  margin: 0px;
}
.router-menu li{
  list-style-type: none;
}
.router-menu li a{
  text-decoration: none;
  color: #fff;
  text-transform: capitalize;
}
.router-menu li a:hover{
  color: blue;
}
.content {
  display: grid;
  grid-template-columns: repeat(3,1fr);
  gap: 20px;
  box-sizing: border-box;
  padding-top: 20px;
}
@media (max-width: 1200px){
  .content {
    grid-template-columns: repeat(2,1fr);
  }
}
@media (max-width: 500px){
  .content {
    grid-template-columns: repeat(1,1fr);
  }
}
</style>
