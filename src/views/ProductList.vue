<template>
  <header>
    <h3 v-if="showList===true">PRODUCT LIST</h3>
    <h3 v-else>
      <ul class="router-menu">
        <li>
          <a href="/">Home</a>
        </li>
        <span style="margin: 0px 5px;">></span>
        <li>
          <a :href="'/' + this.$route.params.categoryName" >{{ this.$route.params.categoryName }}</a>
        </li>
        <span style="margin: 0px 5px;">></span>
        <span style="color: #fff;">{{ this.product.productName }}</span>
      </ul>
    </h3>
  </header>
  <div class="content" v-if="showList===true">
    <div v-for="(product, index) in this.products" :key="index">
      <ProductItem v-on:showDetail="showDetailInparend" :product="product" @actionForm="actionForm" @checkedProduct="checkedProductInparent"/>
    </div>
  </div>
  <div class="detail" v-else>
    <router-view :product="this.product"></router-view>
  </div>
  <footer>
    <button @click="deleteProducts" v-if="checkedProductIds.length > 0">Delete</button>
  </footer>
</template>
<script>
import { mapActions } from 'vuex';
import ProductItem from '@/components/ProductItem.vue';
import store from '@/store';
export default {
  props: {
    productNameSearch: {
      type: String,
    },
    reloadProduct: {
      type: Boolean,
    }
  },
  data() {
    return {
      products: [],
      product: {},
      showList: true,
      searchProductName: '',
      checkedProductIds:[],
    };
  },
  watch: {
    productNameSearch() {
      if(this.productNameSearch === '') {
        this.searchProduct('all');
        this.showList = true;
      }
      else{
        this.searchProduct(this.productNameSearch);
        this.showList = true;
      }
    },
    reloadProduct() {
      this.searchProduct('all');
      this.showList===true
    }
  },
  components: {
    ProductItem,
  },
  methods: {
    ...mapActions('product',['searchProductAction','searchProductByCategoryNameAction','deleteProductsAction']),
    checkedProductInparent(checkedProduct) {
      if(checkedProduct.status) {
        this.checkedProductIds.push(checkedProduct.id)
      }else {
        this.checkedProductIds = this.checkedProductIds.filter(item => item !== checkedProduct.id);
      }
    },
    actionForm(status) {
      this.$emit('actionForm',status);
    },
    async searchProductByCategoryName(name) {
      await this.searchProductByCategoryNameAction(name);
      this.products = store.state.product.products;
    },
    showDetailInparend(selectedProduct) {
      this.product = selectedProduct;
      this.showList = false;
    },
    async searchProduct(name) {
      await this.searchProductAction(name);
      this.products = store.state.product.products;
    },
    async deleteProducts() {
      console.log(this.checkedProductIds)
      await this.deleteProductsAction(this.checkedProductIds);
      if(store.state.product.messages.delete === "Success") {
        alert('Delete Success');
        await this.searchProduct('all');
        this.products = store.state.product.products;
      }
    }
  },
  mounted() {
    if(this.$route.params.categoryName !== undefined && this.$route.params.categoryName !== "search-product")
    {
      this.searchProductByCategoryName(this.$route.params.categoryName);
    }else{
      this.searchProduct('all');
    }
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
  grid-template-columns:repeat(auto-fit, minmax(286px,1fr));
  gap: 20px;
  box-sizing: border-box;
  padding-top: 20px;
}
footer button {
  position: fixed;
  bottom: 40px;
  right: 45px;
  z-index: 1;
  background-color: red;
  color: #fff;
  font-size: 15px;
  box-sizing: border-box;
  padding: 8px 12px;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
  border: solid 1px black ;
}
footer button:hover {
  border: solid 2px black ;
}
footer button:active {
  border: solid 2px red ;
}
/* @media (max-width: 1200px){
  .content {
    grid-template-columns: repeat(2,1fr);
  }
}
@media (max-width: 500px){
  .content {
    grid-template-columns: repeat(1,1fr);
  }
} */
</style>

