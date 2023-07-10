<template>
  <div class="product-list">
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
    <div v-for="(product, index) in this.productsDisplay" :key="index">
      <ProductItem :product="product" @checkedProduct="checkedProductInparent"/>
    </div>
  </div>
  <div class="pagination-product">
    <el-pagination
      :page-size="pagination.pageSize"
      :pager-count="pagination.pagerCount"
      :page-count="totalPage"
      layout="prev, pager, next"
      v-model:current-page="pagination.page"
      @current-change="getPageProducts()"
    />
  </div>
  <footer>
    <button @click="deleteProducts" v-if="checkedProductIds.length > 0">Delete</button>
  </footer>
  </div>
</template>
<script>
import { mapActions, mapMutations } from 'vuex';
import ProductItem from '@/components/ProductItem.vue';
import store from '@/store';
export default {
  props: {
    productNameSearch: {
      type: String,
    }
  },
  data() {
    return {
      product: {},
      showList: true,
      searchProductName: '',
      checkedProductIds:[],
      pagination: {
        pageSize: 8,
        pagerCount: 2,
        page: 1
      },
    };
  },
  computed: {
    products() {
      return store.state.product.products;
    },
    productsDisplay() {
      const startIndex = (this.pagination.page - 1) * this.pagination.pageSize;
      const endIndex = startIndex + this.pagination.pageSize;
      return this.products.slice(startIndex, endIndex);
    },
    totalPage() {
      return Math.max(Math.ceil(this.products.length / this.pagination.pageSize), 1);
    }
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
    }
  },
  components: {
    ProductItem
  },
  methods: {
    ...mapActions('product',['searchProductAction','searchProductByCategoryNameAction','deleteProductsAction']),
    ...mapMutations('product',['createProductMutation']),
    checkedProductInparent(checkedProduct) {
      if(checkedProduct.status) {
        this.checkedProductIds.push(checkedProduct.id)
      }else {
        this.checkedProductIds = this.checkedProductIds.filter(item => item !== checkedProduct.id);
      }
    },
    getPageProducts() {
      console.log()
    },
    async searchProductByCategoryName(name) {
      await this.searchProductByCategoryNameAction(name);
    },
    async searchProduct(name) {
      await this.searchProductAction(name);
    },
    async deleteProducts() {
      await this.deleteProductsAction(this.checkedProductIds);
      if(store.state.product.messages.delete === "Success") {
        alert('Delete Success');
        await this.searchProduct('all');
        this.createProductMutation('');
      }
    }
  },
  mounted() {
    if(this.$route.params.categoryName !== undefined)
    {
      this.searchProductByCategoryName(this.$route.params.categoryName);
    }else if(this.$route.params.productName === undefined){
      this.searchProduct('all');
    }
  },
}
</script>
<style scoped>
.product-list {
  width: 100%;
  height: 100%;
}
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
  grid-template-columns:repeat(auto-fill, minmax(200px,1fr));
  gap: 20px;
  box-sizing: border-box;
  padding-top: 20px;
}
.pagination-product {
  margin-top: 10px;
  display: flex;
  justify-content: center;
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
</style>

