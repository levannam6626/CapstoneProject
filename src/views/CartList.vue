<template>
  <div class="container">
    <div class="header">
      <button @click="this.$router.push('/')"><font-awesome-icon icon="fa-solid fa-left-long" /></button>
      <h3>CART LIST</h3>
    </div>
    <div class="content">
      <div class="cart-item" v-for="(cartItem, index) in cart.cartItems" :key="index">
        <input type="checkbox" name="action" @change="changeSelected(cartItem)" :value="cartItem" v-model="selectedItems"/>
        <CartItem :cart-item="cartItem" :index="index" @quantity-update="quantityUpdateInParent" @delete-cartItem="deleteCartItem"/>
      </div>
      <div class="check-length" v-if="checkLength === false"><font-awesome-icon id="icon" icon="fa-solid fa-cart-shopping" />There are no products in the cart yet</div>
    </div>
    <div class="payment-transparent" @click="paymentClick($event)" v-show="this.showPayment">
      <PaymentView :cart-list="this.selectedItems" :total-cost="this.totalCost" @close-payment="closePayment"/>
    </div>
    <footer>
      <div class="select-all">
        <input type="checkbox" id="all" @change="changeSelectedAll()" v-model="allSelected"><label for="all">SelectAll</label>
      </div>
      <button @click="orderProducts()" class="order">Order ({{ this.selectedItems.length }})</button>
      <div class="total-price">
        <label for="total-price">Total:</label>
        <input type="text" name="total-price" id="total-price" :value="totalDiplay" readonly>
      </div>
    </footer>
    <button @click="orderProducts()" class="order">Order ({{ this.selectedItems.length }})</button>
  </div>
</template>
<script>
import { faLeftLong, faCartShopping } from "@fortawesome/free-solid-svg-icons";
import { library } from "@fortawesome/fontawesome-svg-core";
library.add(faLeftLong, faCartShopping);
import store from '@/store';
import { mapActions } from 'vuex';
import CartItem from '@/components/CartItem.vue';
import PaymentView from './PaymentView.vue';

export default {
  data() {
    return {
      selectedItems: [],
      totalCost: 0,
      quantity: store.state.cart.cartList.productQuantity,
      showPayment: false
    };
  },
  components: {
    CartItem,
    PaymentView
  },
  computed: {
    cart() {
      return store.state.cart.cartList;
    },
    quantityString: {
      get() {
        return this.quantity.toString();
      },
      set(newQuantity) {
        this.quantity = parseInt(newQuantity);
      }
    },
    totalDiplay() {
      const dollar = this.totalCost.toString().split(".")[0];
      const cents = this.totalCost.toString().split(".")[1];
      let res = "";
      const arrDollar = dollar.split("");
      arrDollar.reverse().forEach(function (element, index) {
        if (index % 3 === 0 && index > 0) {
          res = "," + res;
        }
        res = element + res;
      });
      if (cents !== undefined) {
        res = res + "." + cents;
      }
      return "$" + res;
    },
    allSelected: {
      get() {
        return (this.cart.cartItems === undefined || this.cart.cartItems === null || this.selectedItems.length === 0)? false : this.selectedItems.length === this.cart.cartItems.length;
      },
      set(value) {
        if (value) {
          this.selectedItems = [...this.cart.cartItems];
        }
        else {
          this.selectedItems = [];
        }
      }
    },
    checkLength() {
      if(this.cart.cartItems !== undefined) {
        if(this.cart.cartItems.length > 0) {
          return true;
        }
      }return false
    }
  },
  watch: {
    selectedItems() {
      this.totalCostCalculator();
    }
  },
  methods: {
    ...mapActions("cart", ["getCartAction", "changeSelectedAction", "changeSelectedAllAction"]),
    async getCart() {
      await this.getCartAction();
    },
    orderProducts() {
      if(this.selectedItems.length > 0 ) {
        this.showPayment = true;
      }
    },
    quantityUpdateInParent(index, quantity) {
      this.cart.cartItems[index].quantity = quantity;
      this.changeSelectedAction(this.cart.cartItems[index]);
      this.totalCostCalculator();
    },
    deleteCartItem() {
      this.selectedItems = [];
      this.setSelected()
      console.log(this.selectedItems)
    },
    setSelected() {
      if(this.cart.cartItems !== undefined) {
        for (let index = 0; index < this.cart.cartItems.length; index++) {
          let cartItem = this.cart.cartItems[index];
          if(cartItem.selected === true) {
            this.selectedItems.push(cartItem);
          }
        }
      }
      this.totalCostCalculator();
    },
    changeSelected(cartItem) {
      cartItem.selected = this.selectedItems.some(selectedItem => selectedItem.id === cartItem.id)
      this.changeSelectedAction(cartItem);
    },
    async changeSelectedAll() {
      const cartItems = this.cart.cartItems;
      for (let index = 0; index < cartItems.length; index++) {
        if(this.selectedItems.length > 0) {
          cartItems[index].selected = true;
        } else {
          cartItems[index].selected = false;
        }
      }
      await this.changeSelectedAllAction(cartItems);
    },
    totalCostCalculator() {
      this.totalCost = 0;
      this.selectedItems.forEach(item => {
        this.totalCost = Math.round((this.totalCost + item.products.productPrice * item.quantity) * 100) / 100;
      });
    },
    paymentClick(event) {
      if(event.target.className === 'payment-transparent') {
        this.showPayment = false;
      }
    },
    closePayment(status) {
      this.showPayment = false;
      this.$router.push('/bills')
      if(status === true) {
        this.selectedItems = [];
      }
    }
  },
  async mounted() {
    await this.getCart();
    this.setSelected();
  },
}
</script>
<style scoped>
.container {
  align-content: center;
  width: 100%;
  max-width: 700px;
  height: auto;
  background-color: #fff;
  min-height: 21.2rem;
}
.header {
  display: flex;
  background-color: red;
  box-sizing: border-box;
  width: 100%;
  padding: 10px 0px;
  border-bottom: 2px solid #000;
  margin-bottom: 10px;
}
.header button {
  border: 0;
  background-color: red;
  cursor: pointer;
  font-size: 16px;
}
.header button:hover {
  color: #fff;
}
.header h3 {
  box-sizing: border-box;
  text-align: center;
  color: #fff;
  margin: 0px;
  width: 100%;
  padding-right: 20px;
}

.content {
  width: 100%;
  box-sizing: border-box;
  padding: 0px 15px;
  min-height: 14.7rem;
}

.cart-item:first-child {
  border-top: solid 1px rgb(190, 188, 188);
}
.cart-item {
  display: flex;
  align-items: center;
  box-sizing: border-box;
  padding: 3px 10px;
  gap: 20px;
  width: 100%;
  height: 3.7em;
  border-bottom: solid 1px rgb(190, 188, 188);
}
.check-length {
  box-sizing: border-box;
  align-self: center;
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center ;
  min-height: 14.7rem;
  font-size: 20px;
  color: red
}
.check-length #icon {
  transform: rotate(-30deg);
}
.payment-transparent {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.6);
  z-index: 3;
  display: flex;
  justify-content: center;
  align-items: center;
}
footer {
  display: flex;
  justify-content: space-between;
  height: 50px;
  gap: 1px;
  align-items: center;
  box-sizing: border-box;
  padding: 0px 25px;
  border-top: 1px solid rgb(190, 188, 188);
}

.select-all,
.total-price {
  display: flex;
}

.select-all input,
.select-all label {
  cursor: pointer;
}

.select-all label {
  box-sizing: border-box;
  padding: 0px 10px;
}

.total-price label {
  color: red;
}

.total-price input {
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
  box-shadow: 1px 1px;
}

.order:hover {
  background-color: red;
  color: #fff;
}
.container > button{
  width: 100%;
  display: none;
}
@media screen and (max-width: 500px) {
  .container {
    min-width: 255px;
  }
  .order {
    display: none;
  }
  .container > button{
    display: block;
    width: 50%;
    margin-left: 25%;
    margin-bottom: 5px;
  }
}
@media screen and (max-width: 340px) {
  .total-price label {
    display: none;
  }
}
@media screen and (max-width: 380px) {
  .cart-item {
    gap: 0 !important;
  }
}
</style>

