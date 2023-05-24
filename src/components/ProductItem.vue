<template>
  <div class="product">
    <input type="checkbox" name="action" :value="product.productName" v-if="this.account.role === 'SELLER'" @change="checkbox($event)" v-model="checkedProduct">
    <figure >
      <img @click="showDetail()" src= "../assets/raspberrypi3.jpg" />
    </figure>
    <div class="product-name">
      <a @click="showDetail()">{{ this.product.productName }}</a>
    </div>
    <div class="product-description">
      <p>{{ this.product.productDescription }}</p>
    </div>
    <div class="product-price">
      <label>{{ this.product.productPrice }}</label>
      <button v-if="this.account.role === 'SELLER'" @click="editProduct()"><font-awesome-icon icon="fa-solid fa-cart-shopping" /> Edit</button>
      <button v-else @click="orderProduct()"><font-awesome-icon icon="fa-solid fa-cart-shopping" /> Order</button>
    </div>
  </div>
</template>
<script>
  import store from '@/store';
  import { library } from '@fortawesome/fontawesome-svg-core'
  import { faCartShopping } from '@fortawesome/free-solid-svg-icons'
  library.add( faCartShopping)
  export default {
    props: {
      product: {
        type: Object,
        required: true
      }
    },
    data() {
      return {
        account: store.state.account.userAccount,
        url: "https://res.cloudinary.com/dx5ykjdqy/image/upload/",
        checkedProduct:[],
      };
    },
    methods: {
      checkbox() {
        console.log(this.checkedProduct);
      },
      showDetail() {
        this.$emit('showDetail',this.product);
        this.$router.push("productDetail");
      },
      orderProduct() {
        const loggedIn = localStorage.getItem('token');
        if (!loggedIn) {
            return this.$router.push("login");
        }else{
          alert("LoggedIn")
        }
      },
      editProduct() {
        this.$router.push({
          name: 'editProduct',
          params: {
            productId: this.product.productName,
          }
        });
      }
    },
  }
</script>
<style scoped>
.product {
  display: grid;
  text-align: center;
  background-color: white;
  box-sizing: border-box;
  padding: 20px;
  gap: 5px;
}
.product figure {
  width: 100%;
  overflow: hidden;
  margin: 0px;
  padding: 0px;
}
.product img {
  transition: .5s;
  width: 100%;
  cursor: pointer;
}
.product img:hover {
  transform: scale(1.2);
}
.product-price {
  display: flex;
  justify-content: space-between;
  box-sizing: border-box;
  padding: 0px 10px;
  align-items: center;
}
.product-name a {
  color: red;
  font-size: 19px;
  cursor: pointer;
}
.product-name a:hover {
  font-weight: bold;
}
.product-price label {
  color: red;
  font-weight: 600;
}
.product-price button {
  box-sizing: border-box;
  border-radius: 5px;
  padding: 8px 17px 8px 12px;
  border: none;
  color: red;
  transition: 0.2s;
  font-weight: bold;
  border: solid 1px red;
  background-color: white;
  cursor: pointer;
}
.product-price button:hover {
  background-color: red;
  color: white;
}
</style>

