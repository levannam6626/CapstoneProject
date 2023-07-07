<template>
  <div class="payment">
    <form @submit.prevent="payment">
      <div class="payment-header">
        <span>Confirm Order</span>
        <a @click="this.$emit('closePayment', false)"><font-awesome-icon icon="fa-solid fa-x" /></a>
      </div>
      <div class="payment-content">
        <div class="user-infor">
          <h3>{{ this.account.firstname }} {{ this.account.lastname }}</h3>
          <label for="address-delivery">Delivery address:</label>
          <input type="text" placeholder="Delivery address" maxlength="150" id="address-delivery" v-model="this.account.address" required>
          <label for="phone-delivery">Phone:</label><span style="margin-left: 2rem;" class="message">{{ this.messages.phone }}</span>
          <input type="text" placeholder="Phone number" id="phone-delivery" minlength="10" maxlength="13" v-model="this.account.phone" required>
          
          <label for="notes">Additional notes if yes:</label>
          <input type="text" placeholder="Additional notes" id="notes" maxlength="150" v-model="this.notes">
          <div class="total-cost">
            <span>Total Cost:</span>
            <span>${{ this.totalCost }}</span>
          </div>
        </div>
        <div class="items">
          <div class="items-cart" v-for="(cart, index) in cartList" :key="index">
            <figure>
              <img :src="this.url + cart.products.productImage" />
            </figure>
            <div class="items-cart-content">
              <a>{{ cart.products.productName }}</a>
              <label>${{ cart.products.productPrice }}<span style="float: right;">x{{ cart.quantity }}</span></label>
            </div>
          </div>
        </div>
      </div>
      <div class="payment-footer">
        <button type="submit">PAYMENT</button>
      </div>
    </form>
  </div>
    <SuccessAlert message="Congratulations, your products has been successfully ordered." @closeAlert="closeAlert" v-show="this.alert"/>
</template>
<script>
import store from "@/store";

import SuccessAlert from "@/components/SuccessAlert.vue"

import { library } from "@fortawesome/fontawesome-svg-core";
import { faX } from "@fortawesome/free-solid-svg-icons";
import { mapActions, mapMutations } from "vuex";
library.add(faX);

export default {
  props: {
    cartList: {
      type: Object,
      requuire: true
    },
    totalCost: {
      require: true
    }
  },
  data() {
    return {
      url: store.state.product.url,
      account: store.state.auth.userAccount,
      notes: '',
      alert: false,
      reg: {
        regPhone: /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/
      },
      messages: {
        phone: ''
      }
    }
  },
  emits :{
    closePayment: null
  },
  components: {
    SuccessAlert
  },
  methods: {
    ...mapActions('order',['paymentAction']),
    ...mapActions('cart',['getCartAction']),
    ...mapMutations('order',['paymentMutation']),
    resetMessages() {
      this.messages.phone = '';
    },
    validated() {
      this.resetMessages();
      if (!this.reg.regPhone.test(this.account.phone)) {
        this.messages.phone = "Please enter a valid phone number";
      }else {
        return true;
      }
      return false;
    },
    async payment() {
      if (this.validated()) {
        console.log(this.account)
        const userInfor = {
          fullName: this.account.firstname + ' ' + this.account.lastname,
          deliveryAddress: this.account.address,
          phone: this.account.phone,
          additionalNotes: (this.notes === '')? 'There are no notes!': this.notes
        }
        await this.paymentAction(userInfor);
        if(store.state.order.message.payment === "Add Order Success") {
          this.paymentMutation(null);
          await this.getCartAction();
          this.alert = true;
        }
      }
    },
    closeAlert() {
      this.alert = false;
      this.$emit('closePayment', true);
    },
    totalPrice(cart) {
      return Math.round((cart.quantity * cart.products.productPrice) *100) /100;
    }
  }
}
</script>
<style scoped>
.payment {
  background-color: #fff;
  width: 30em;
  height: 21.9em;
  display: grid;
  border-radius: 8px;
  box-shadow: 4px 4px rgb(148, 143, 143);
}
.payment-header {
  display: flex;
  justify-content: flex-end;
  box-sizing: border-box;
  padding: 5px;
  width: 100%;
  height: 40px;
  border-bottom: solid 1px rgb(192, 188, 188);
}
.payment-header span {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: inherit;
  font-weight: bold;
  font-size: 1em;
}
.payment-header a {
  float: right;
  text-decoration: none;
  box-sizing: border-box;
  border-radius: 50%;
  padding: 0px 11px;
  height: 100%;
  display: flex;
  align-items: center;
  background-color: #e4e4e7;
  color: #050505;
  font-size: 12px;
}
.payment-header a:hover {
  background-color: #d8dadf;
}
.payment-content {
  display: flex;
  gap: 5px;
  height: 265px;
}
.user-infor {
  width: 50%;
  box-sizing: border-box;
  padding: 0px 10px;
}
.user-infor h3 {
  margin: 8px 0;
  height: 30px;
  width: 100%;
  box-sizing: border-box;
  display: flex;
  justify-content: center;
}
.user-infor label {
  width: 30%;
}
.user-infor input {
  height: 25px;
  box-sizing: border-box;
  padding-left: 8px;
  width: 100%;
  margin-bottom: 12px;
  border-color: #ccc;
}
.user-infor input:focus {
  outline: none;
}
.message {
  color: red;
  font-size: 10px;
}
.total-cost {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
  color: red;
  font-weight: bold;
}
input[type='number'] {
  width: 50%;
  border: 0;
  margin: 0;
  color: red;
  font-weight: bold;
}
.items {
  overflow: hidden;
  height: 100%;
  width: 50%;
  border-left: solid 1px rgb(192, 188, 188);
  background-color: #f0f0f0;
}
.items:hover {
  overflow-x: hidden;
  overflow-y: auto;
}
.items::-webkit-scrollbar {
  width: 8px;
}
.items::-webkit-scrollbar-thumb {
  background-color: #ccc;
  border-radius: 5px;
}
.items::-webkit-scrollbar-thumb:hover {
  background-color: #999;
}
.items-cart {
  display: flex;
  align-items: center;
  gap: 10px;
  width: 100%;
  height: 60px;
  box-sizing: border-box;
  padding: 2px 10px;
  border-bottom: solid 1px rgb(192, 188, 188);
}
figure {
  display: flex;
  align-items: center;
  box-sizing: border-box;
  overflow: hidden;
  width: 45px;
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
.items-cart-content {
  display: grid;
  gap: 4px;
  width: 65%;
}
.items-cart-content label {
  display: flex;
  justify-content: space-between;
  width: 9em;
}
.payment-footer {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 45px;
  width: 100%;
  border-top: solid 1px rgb(192, 188, 188);
}
.payment-footer button{
  box-sizing: border-box;
  background-color: #fff;
  border-radius: 5px;
  border: solid 2px red;
  color: red;
  font-weight: bold;
  height: 32px;
}
.payment-footer button:hover {
  background-color: red;
  color: #fff;
}
.alert {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border-radius: 17px;
  background-color: rgba(0, 0, 0, 0.6);
}
@media screen and (max-width: 550px) {
  .payment {
    width: 16em;
  }
  .payment-content {
    display: block ;
    overflow-x: hidden;
    overflow-y: auto;
  }
  .payment-content::-webkit-scrollbar {
    width: 7px;
  }
  .payment-content::-webkit-scrollbar-thumb {
    background-color: #ccc;
    border-radius: 5px;
  }
  .payment-content::-webkit-scrollbar-thumb:hover {
    background-color: #999;
  }
  .user-infor, .items {
    width: 100%;
  }
  .items {
    margin-top: 10px;
    height: auto;
  }
  .items:hover {
    overflow: hidden;
  }
}
</style>

