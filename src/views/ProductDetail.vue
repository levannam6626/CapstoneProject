<template>
  <div class="product-detail">
    <h3>
    <ul class="router-menu">
      <li>
        <a href="/">Home</a>
      </li>
      <span style="margin: 0px 5px;">></span>
      <li>
        <a :href="'/' + this.$route.params.categoryName" >{{ this.$route.params.categoryName }}</a>
      </li>
      <span style="margin: 0px 5px;">></span>
      <span style="width: auto;">{{ this.product.productName }}</span>
    </ul>
  </h3>
  <div class="product">
    <div class="product-img">
      <figure>
        <img @mousemove="scaleElement" @mouseenter="this.statusDiplay = 'block'" @mouseleave="this.statusDiplay = 'none'" :src="this.url + this.product.productImage" alt="product-img">
      </figure>
    </div>
    <div class="product-detail-show">
      <figure class="zoom-img" :style="{display: statusDiplay}">
        <img :src="this.url + this.product.productImage" :style="{transform: 'scale(' + scale + ')', transformOrigin: scaleOrigin }" alt="product-img">
      </figure>
      <div class="product-name">
        {{ this.product.productName }}
      </div>
      <div class="product-price">
        <p>${{ this.product.productPrice }}</p>
      </div>
      <label style="color: black; font-size: 18px; font-weight: bold;">Product Description:</label>
      <div class="product-description">
        {{ this.product.productDescription }}
      </div>
      <div class="product-category">
        <label>Product Category: </label>{{categoryName }}
      </div>
      <div class="product-left">
        <p>Products left: {{ this.product.productQuantity }}</p>
      </div>
      <div class="product-action" v-if="this.$store.state.auth.userAccount.role !== 'SELLER'">
        <div class="product-quantity">
          <button @click="this.quantity = Math.max((this.quantity - 1), 0)">-</button>
          <input type="text" maxlength="3" v-model="this.quantityString"/>
          <button @click="this.quantity = Math.min((this.quantity + 1), this.product.productQuantity)">+</button>
        </div>
        <button @click="addProductToCart()"><font-awesome-icon icon="fa-solid fa-cart-shopping" /> ADD TO CART</button>
      </div>
      <button v-if="this.$store.state.auth.userAccount.role === 'SELLER'" @click="editProduct()"> Edit</button>
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
    data() {
      return {
        product: store.state.product.productDetail,
        categoryName: this.$route.params.categoryName,
        url: store.state.product.url,
        quantity: 1,
        scale: 1.5,
        scaleOrigin: '0 0',
        statusDiplay: 'none'
      }
    },
    computed: {
      quantityString: {
        get() {
          return this.quantity.toString();
        },
        set(newQuantity) {
          this.quantity = parseInt(newQuantity);
        }
      }
    },
    methods: {
      ...mapActions('cart',['addProductToCartAction']),
      async addProductToCart() {
        const loggedIn = store.state.auth.token;
        if (loggedIn) {
          const objOrder = {
            productID: this.product.productId,
            quantity: this.quantity
          }
          await this.addProductToCartAction(objOrder);
        }else{
          return this.$router.push("/login");
        }
      },
      editProduct() {
        this.$router.push({name: 'editProduct', params:{productId: this.product.productId}})
      },
      scaleElement(event) {
        const { top, left } = event.target.getBoundingClientRect()
        const mouseX = event.clientX - left
        const mouseY = event.clientY - top
        const scale = 2
        const scaleOriginX = mouseX / event.target.offsetWidth * 100 + '%'
        const scaleOriginY = mouseY / event.target.offsetHeight * 100 + '%'
        this.scale = scale
        this.scaleOrigin = scaleOriginX + ' ' + scaleOriginY
      }
    },
  })
</script>
<style scoped>
.product-detail {
  width: 100%;
  height: 100%;
}
h3 {
  background-color: red;
  height: auto;
  color: white;
  font-weight: 400;
  height: 50px;
  box-sizing: border-box;
  padding: 15px 15px;
  box-shadow: black 0px 2px;
  margin: 0px;
  margin-bottom: 2px;
  display: flex;
}
p {
  margin: 0;
}
.router-menu {
  display: flex;
  padding: 0px;
  margin: 0px;
  list-style-type: none;
  color: #fff; overflow: hidden;
}
.router-menu li a{
  text-decoration: none;
  color: #fff;
  text-transform: capitalize;
}
.router-menu li a:hover{
  color: blue;
}
.product {
  margin-top: 15px;
  padding: 30px;
  box-sizing: border-box;
  display: flex;
  gap: 20px;
  background-color: #fff;
}
.product-img {
  border: solid 1px rgb(150, 149, 149);
  display: grid;
  align-items: center;
}
.product-img figure {
  width: 100%;
  min-width: 200px;
  max-width: 330px;
  overflow: hidden;
  margin: 0px;
  padding: 0px;
}
.product-img figure img {
  transition: .5s;
  width: 100%;
  cursor: pointer;
}
.product-detail-show {
  position: relative;
  display: grid;
  width: 100%;
  min-width: 200px;
  border: solid 1px rgb(150, 149, 149);
  box-sizing: border-box;
  padding: 10px 15px;
  height: auto;
}
.zoom-img {
  box-sizing: border-box;
  overflow: hidden;
  position: absolute;
  top: -10;
  left: 0;
  margin: 0;
  padding: 0;
  height: 100%;
  border: groove 3px;
  background-color: #fff;
}
.zoom-img img {
  transition: transform 0.3s ease;
}
.product-name {
  font-size: 2em;
  color: black;
  font-weight: bold;
  height: auto;
  margin-bottom: 5px;
}
.product-price {
  font-size: 25px;
  color: red;
  font-weight: bold;
  margin-bottom: 15px;
}
.product-description {
  height: auto;
  min-height: 80px;
  font-size: 18px;
  padding-left: 10px;
  margin-bottom: 15px;
  text-align: justify;
  text-justify: newspaper;
}
.product-category {
  margin-bottom: 10px;
}
.product-category label{
  font-weight: bold;
  font-size: 18px;
}
.product-left {
  color: red;
  font-size: 17px;
  font-weight: bold;
  margin-bottom: 10px;
}

.product-quantity {
  display: flex;
  color: red;
  justify-content: center;
  align-items: center;
  height: 42px;
}
.product-quantity button {
  font-size: 22px;
  padding: 8px 18px;
  background-color: #fff;
  border: solid 1px rgb(192, 188, 188);
  cursor: pointer;
}
.product-quantity button:hover {
  background-color: rgb(226, 222, 222);
}
.product-quantity input{
  text-align: center;
  height: 40px;
  width: 40px;
  border: 0;
  border-top: solid 1px rgb(192, 188, 188);
  border-bottom: solid 1px rgb(192, 188, 188);
  background-color: rgb(226, 222, 222);
}
.product-quantity input:focus {
  outline: none;
}
.product-action {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 10px;
  color: red;
  font-weight: bold;
  box-sizing: border-box;
  height: auto;
}
.product-action > button {
  height: 40px;
  border-color: red;
  font-weight: bold;
  color: red;
}
.product-action > button:hover {
  background-color: red;
  color: #fff;
}
.product-detail-show > button {
  box-sizing: border-box;
  border-radius: 5px;
  padding: 8px 17px 8px 12px;
  border: none;
  color: red;
  transition: 0.2s;
  font-size: 15px;
  font-weight: bold;
  border: solid 2px red;
  background-color: white;
  cursor: pointer;
  width: 180px;
  justify-self: center;
}
.product-detail-show > button:hover {
  background-color: red;
  color: white;
}
@media screen and (max-width: 660px) {
  .product {
    display: grid !important;
    padding: 20px;
  }
  .product-img {
    display: flex;
    justify-content: center;
  }
}
@media screen and (max-width: 444px) {
  .product {
    padding: 10px;
  }
  .product-action {
    display: grid !important;
    justify-content: center;
    gap: 10px;
  }
}
</style>

