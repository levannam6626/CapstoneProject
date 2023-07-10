<template>
  <figure>
    <img :src="this.url + cartItem.products.productImage" />
  </figure>
  <div class="cart-item-content" >
    <a>{{ cartItem.products.productName }}</a>
    <div class="product-quantity">
      <button @mousedown="initFastDecreaseQuantity()" @mouseup="destroyFastDecreaseQuantity()" @click="decreaseQuantity()" :disabled="this.quantity === 1 || this.quantity === 0">-</button>
      <input type="text" maxlength="3" v-model="this.quantityString" readonly/>
      <button @mousedown="initFastIncreaseQuantity()" @mouseup="destroyFastIncreaseQuantity()" @click="increaseQuantity()" :disabled="this.quantity === cartItem.products.productQuantity">+</button>
    </div>
    <label>${{ this.totalPrice }}</label>
  </div>
  <button class="btn-del" @click="delCartItem()">X</button>
</template>
<script>
import store from '@/store';
import { mapActions } from 'vuex';

export default {
  props: {
    cartItem: {
      type: Object,
      require: true
    },
    index: {
      require: true
    },
  },
  emits :{
    quantityUpdate: null,
    deleteCartItem: null
  },
  data() {
    return {
      url: store.state.product.url,
      intervalId: null
    }
  },
  computed: {
    quantity() {
      return this.cartItem.quantity;
    },
    quantityString: {
      get() {
          return this.quantity.toString();
      },
      set(newQuantity) {
          this.quantity = parseInt(newQuantity);
      }
    },
    totalPrice() {
      return Math.round((this.quantity * this.cartItem.products.productPrice) *100) /100;
    }
  },
  methods: {
    ...mapActions('cart',['delCartItemAction']),
    decreaseQuantity() {
      this.$emit('quantityUpdate', this.index, Math.min(Math.max((this.quantity - 1), 1), this.cartItem.products.productQuantity));
    },
    initFastDecreaseQuantity() {
      this.intervalId = setInterval(() => {
        if(this.quantity === 1 || this.quantity === 0) {
          clearInterval(this.intervalId)
        }else {
          this.$emit('quantityUpdate', this.index, Math.min(Math.max((this.quantity - 1), 1), this.cartItem.products.productQuantity));
        }
      }, 100);
    },
    destroyFastDecreaseQuantity() {
      clearInterval(this.intervalId)
    },
    increaseQuantity() {
      this.$emit('quantityUpdate', this.index, Math.min((this.quantity + 1), this.cartItem.products.productQuantity));
    },
    initFastIncreaseQuantity() {
      this.intervalId = setInterval(() => {
        if(this.quantity === this.cartItem.products.productQuantity) {
          clearInterval(this.intervalId)
        }else {
          this.$emit('quantityUpdate', this.index, Math.min((this.quantity + 1), this.cartItem.products.productQuantity));
        }
      }, 100);
    },
    destroyFastIncreaseQuantity() {
      clearInterval(this.intervalId)
    },
    async delCartItem() {
      await this.delCartItemAction(this.cartItem);
      this.$emit('deleteCartItem')
    }
  }
}
</script>
<style scoped>
figure {
  display: flex;
  align-items: center;
  box-sizing: border-box;
  overflow: hidden;
  width: 55px;
  margin: 0px;
  padding: 0px;
  height: 100%;
}
img {
  transition: .5s;
  width: 100%;
  cursor: pointer;
}
img:hover {
  transform: scale(1.2);
}
.cart-item-content {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  box-sizing: border-box;
  padding-top: 5px;
  width: 100%;
  align-items: center;
}
.cart-item-content a {
 width: 60%;
 max-width: 200px;
 text-align: left;
}
.cart-item-content label {
 width: 100px;
 text-align: left;
 color: red;
 font-weight: bold;
}
.product-quantity {
  display: flex;
  color: red;
  justify-content: center;
  align-items: center;
  width: 82px;
  height: 30px;
}
.product-quantity button {
  font-size: 21px;
  padding: 0px 8px;
  background-color: #fff;
  border: solid 1px rgb(192, 188, 188);
  cursor: pointer;
}
.product-quantity button[disabled] {
  background-color: rgb(226, 222, 222);
}
.product-quantity button:hover {
  background-color: rgb(226, 222, 222);
}
.product-quantity input{
  text-align: center;
  height: 22px;
  width: 22px;
  border: 0;
  border-top: solid 1px rgb(192, 188, 188);
  border-bottom: solid 1px rgb(192, 188, 188);
  background-color: rgb(226, 222, 222);
}
.product-quantity input:focus {
  outline: none;
}
.btn-del {
  border-radius: 3px;
  border: 0;
  background-color: #ccc;
  box-shadow: 1px 1px;
}
.btn-del:hover {
  background-color: red;
  color: #fff;
}
@media screen and (max-width: 530px) {
  .cart-item-content {
    display: grid;
    gap: 0;
    margin-left: 20px;
  }
  .cart-item-content a {
    width: 100%;
  }
  .product-quantity {
    justify-content: left;
  }
  .product-quantity button {
    font-size: 15px;
    padding: 0px 5px;
  }
  .product-quantity input{
    height: 15px;
    width: 15px;
  }
}
</style>

