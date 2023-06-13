<template>
  <div class="container">
    <header>
      <h3>CART LIST</h3>
    </header>
    <div class="content">
      <div class="cart-item" v-for="(product, index) in products" :key="index">
        <input type="checkbox" name="action" :value="product" v-model="selectedItems"/>
        <figure >
          <img :src= "this.url + product.productImage" />
        </figure>
        <div class="cart-item-content">
          <a >{{ product.productName }}</a>
          <label>${{ product.productPrice }}</label>
        </div>
      </div>
    </div>
    <footer>
      <div class="select-all">
        <input type="checkbox" id="all" v-model="allSelected"><label for="all">SelectAll</label>
      </div>
      <div class="total-price">
        <label for="total-price">Total:</label>
        <input type="text" name="total-price" id="total-price" :value="totalDiplay" readonly>
      </div>
      <button @click="orderProducts()" class="order">Order ({{ this.selectedItems.length }})</button>
    </footer>
    {{ this.selectedItems }}
  </div>
</template>
<script>
import store from '@/store';
import { mapActions } from 'vuex';

export default {
  data() {
    return {
      carts:[],
      products: [],
      url: store.state.product.url,
      selectedItems: [],
      totalPrice: 0,
      selectAll: false
    }
  },
  computed: {
    totalDiplay() {
      const dollar = this.totalPrice.toString().split(".")[0];
      const cents = this.totalPrice.toString().split(".")[1];
      let res = "";
      const arrDollar = dollar.split("");
      arrDollar.reverse().forEach(function(element, index) {
        if(index % 3 === 0 && index > 0) {
          res = "," + res;
        }
        res = element + res;
      });
      if(cents !== undefined) {
        res = res + '.' + cents;
      }
      return "$" + res;
    },
    allSelected: {
      get() {
        return this.selectedItems.length === this.products.length;
      },
      set(value) {
        if (value) {
          this.selectedItems = [...this.products];
        } else {
          this.selectedItems = [];
        }
      }
    }
  },
  watch: {
    selectedItems() {
      this.selectAll = this.allSelected;
      this.totalPriceCalculator();
    }
  },
  methods: {
    ...mapActions('product',['searchProductAction','']),
    async searchProduct(name) {
      await this.searchProductAction(name);
      this.products = store.state.product.products;
    },
    orderProducts() {
      
    },
    totalPriceCalculator() {
      this.totalPrice = 0;
      this.selectedItems.forEach(item => {
        this.totalPrice = Math.round((this.totalPrice + item.productPrice) * 100) / 100;
      });
    },
  },
  mounted() {
    this.searchProduct('all');
  },
}
</script>
<style scoped>
.container {
  display: grid;
  width: 335px;
  height: auto;
  gap: 20px;
}
header h3 {
  text-align: center;
}
.content {
  display: grid;
  gap: 10px;
  width: 100%;
}
.cart-item {
  display: flex;
  align-items: center;
  box-sizing: border-box;
  padding: 3px 10px;
  gap: 10px;
  width: 100%;
  height: 80px;
  border-bottom: solid 2px rgb(190, 188, 188);
}
.cart-item figure {
  display: flex;
  align-items: center;
  box-sizing: border-box;
  overflow: hidden;
  width: 45%;
  max-width: 100px;
  margin: 0px;
  padding: 0px;
  height: 100%;
}
.cart-item img {
  transition: .5s;
  width: 100%;
  cursor: pointer;
}
.cart-item img:hover {
  transform: scale(1.2);
}
.cart-item-content {
  display: grid;
  box-sizing: border-box;
  padding-top: 5px;
  text-align: left;
}
.cart-item-content a {
  height: 50px;
}
.cart-item-content label {
  height: 25px;
}
footer {
  display: flex;
  height: 50px;
  gap: 1px;
  align-items: center;
  box-sizing: border-box;
  padding: 0px 10px;
}
.select-all, .total-price {
  display: flex;
}
.select-all input, .select-all label {
  cursor: pointer;
}
.select-all label {
  box-sizing: border-box;
  padding: 0px 10px;
}
.total-price label{
  color: red;
}
.total-price input{
  box-sizing: border-box;
  padding: 0px;
  padding-left: 5px;
  border: 0px;
  width: 105px;
  text-align: left;
  font-size: 15px;
  font-weight: bold;
  color: red;
  cursor: context-menu;
}
.total-price input:focus {
  outline: none;
}
.order {
  border: 1px solid red;
  border-radius: 5px;
  width: 70px;
  padding: 8px 0px;
  background-color: #fff;
  color: red;
  font-weight: bold;
  cursor: pointer;
}
.order:hover {
  background-color: red;
  color: #fff;
}
</style>

