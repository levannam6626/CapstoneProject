<template>
  <div class="product">
    <figure class="product-img">
        <img :src="this.url + this.product.productImage" alt="">
    </figure>
    <div class="product-detail">
      <div class="product-name">
        {{ this.product.productName }}
      </div>
      <div class="product-description">
        {{ this.product.productDescription }}
      </div>
      <div class="product-category">
        {{ this.product.productCategory }}
      </div>
      <div class="product-price">
        <p>${{ this.product.productPrice }}</p>
        <button @click="addProductToCart()"><font-awesome-icon icon="fa-solid fa-cart-shopping" /> ADD TO CART</button>
      </div>
    </div>
</div>
</template>
<script>
  import { library } from '@fortawesome/fontawesome-svg-core'
  import { faCartShopping } from '@fortawesome/free-solid-svg-icons'
  library.add( faCartShopping)

  import { mapActions } from 'vuex';
  import store from '@/store';

  export default ({
    props: {
      product: {
        type: Object,
        required: true
      }
    },
    data() {
      return {
        url: store.state.product.url,
      }
    },
    methods: {
      ...mapActions('product',['addProductToCartAction']),
      async addProductToCart() {
        const loggedIn = store.state.auth.token;
        if (loggedIn) {
          const objOrder = {
            customerId: store.state.auth.userAccount.id,
            product: this.product
          }
          await this.addProductToCartAction(objOrder);
        }else{
          return this.$router.push("/login");
        }
      },
    },
    mounted() {
    },
  })
</script>
<style scoped>
.product {
  margin-top: 15px;
  padding: 30px;
  box-sizing: border-box;
  display: flex;
  gap: 20px;
  background-color: #fff;
}
figure {
  width: 50%;
  overflow: hidden;
  margin: 0px;
  padding: 0px;
}
figure img {
  transition: .5s;
  width: 100%;
  cursor: pointer;
}
figure img:hover {
  transform: scale(1.2);
}
.product-detail {
  display: grid;
  width: 50%;
  min-width: 200px;
  border: solid 2px rgb(150, 149, 149);
  box-sizing: border-box;
  padding: 10px 15px 0px;
  height: auto;
}
.product-name {
  font-size: 1.5em;
  color: black;
  font-weight: bold;
  height: auto;
  margin-bottom: 10px;
}
.product-description {
  height: auto;
  min-height: 80px;
  margin-bottom: 10px;

}
.product-price {
  display: flex;
  justify-content: space-between;
  height: 50px;
  color: red;
  font-weight: bold;
}
button {
  margin-left: 5px;
  height: 40px;
  border-color: red;
  font-weight: bold;
  color: red;
}
button:hover {
  background-color: red;
  color: #fff;
}
</style>

