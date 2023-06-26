<template>
  <div class="bills">
    <div v-for="(productOrder, index) in this.productOrders" :key="index">
      <BillItem :product-order="productOrder" />
    </div>
  </div>
</template>
<script>
import BillItem from "@/components/BillItem";
import store from "@/store";
import { mapActions } from "vuex";
export default {
  data() {
    return {
      productOrders: [
        {
          user: {
            firstname: 'Thanh',
            lastname: 'Duc',
            email: 'customer@gmail.com',
            phone: '0123456789',
            address: 'Quang Binh'
          },
          totalPrice: 0,
          createDate: Date.now(),
          orderItem: [
            {
              quantity: 0,
              price: 0,
              products: {
                productName: 'test test 1',
                productImage: 'rspdag5her5duxvtrpcs',
                productPrice: 0
              }
            },
            {
              quantity: 0,
              price: 0,
              products: {
                productName: 'test test 2',
                productImage: 'rspdag5her5duxvtrpcs',
                productPrice: 0
              }
            },
            {
              quantity: 0,
              price: 0,
              products: {
                productName: 'test test 3',
                productImage: 'rspdag5her5duxvtrpcs',
                productPrice: 0
              }
            }
          ]
        },
        {
          user: {
            firstname: 'Thanh',
            lastname: 'Duc',
            email: 'customer@gmail.com',
            phone: '0123456789',
            address: 'Quang Binh'
          },
          totalPrice: 0,
          createDate: Date.now(),
          orderItem: [
            {
              quantity: 0,
              price: 0,
              products: {
                productName: 'test test 4',
                productImage: 'rspdag5her5duxvtrpcs',
                productPrice: 0
              }
            },
            {
              quantity: 0,
              price: 0,
              products: {
                productName: 'test test 5',
                productImage: 'rspdag5her5duxvtrpcs',
                productPrice: 0
              }
            },
            {
              quantity: 0,
              price: 0,
              products: {
                productName: 'test test 6',
                productImage: 'rspdag5her5duxvtrpcs',
                productPrice: 0
              }
            }
          ]
        }
      ]
    }
  },
  computed: {
    productOrderss() {
      return store.state.order.productOrders;
    }
  },
  components: {
   BillItem
  },
  methods: {
    ...mapActions('order',['loadAllBillAction','loadBillByUserAction']),
    loadAllBill() {
      this.loadAllBillAction();
    },
    async loadBillByUser() {
      await this.loadBillByUserAction();
      console.log(this.productOrderss);
    },
  },
  mounted() {
    if(store.state.auth.userAccount.role === 'CUSTOMER') {
      this.loadBillByUser();
    }else {
      this.loadAllBill();
    }
  }
}
</script>
<style scoped>
.bills {
  height: auto;
  min-height: 21.2rem;
  width: 90%;
  display: grid;
  gap: 4em;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  box-sizing: border-box;
  padding: 5px 10px;
}
</style>

