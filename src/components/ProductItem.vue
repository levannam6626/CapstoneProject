<template>
  <div class="product">
    <input type="checkbox" name="action" :value="product.productId" style="height: 15px;" v-if="this.account.role === 'SELLER'" @change="checkbox($event)" v-model="checked">
    <figure >
      <img @click="showDetail()" :src= "this.url + this.product.productImage" />
    </figure>
    <div class="product-name">
      <a @click="showDetail()">{{ this.product.productName }}</a>
    </div>
    <div class="product-description">
      <p>{{ this.product.productDescription }}</p>
    </div>
    <div class="product-price">
      <label>{{ this.product.productPrice }}</label>
      <button v-if="this.account.role === 'SELLER'" @click="editProduct()"> Edit</button>
      <button v-else @click="addProductToCart()"><font-awesome-icon icon="fa-solid fa-cart-shopping" /> Add<span v-show="isOrderedByThisUser">ed</span> To Cart</button>
    </div>
  </div>
</template>
<script>
  import store from '@/store';
  import { library } from '@fortawesome/fontawesome-svg-core'
  import { faCartShopping } from '@fortawesome/free-solid-svg-icons'
  library.add( faCartShopping)

  import { mapActions } from 'vuex';

  export default {
    props: {
      product: {
        type: Object,
        required: true
      },
    },
    data() {
      return {
        account: store.state.auth.userAccount,
        url: store.state.product.url,
        categoryName: '',
        checked: false,
        checkedProduct: {
          id:'',
          status: false
        },
        isOrderedByThisUser: false,
      };
    },
    methods: {
      ...mapActions('category',['getCategoryByIdAction']),
      ...mapActions('product',['addProductToCartAction']),
      checkbox(event) {
        this.checkedProduct.id = event.target.value;
        this.checkedProduct.status = this.checked;
        this.$emit('checkedProduct', this.checkedProduct)
      },
      checkOrdered() {
        const orderedProducts = store.state.product.productsCart;
        const person = orderedProducts.find(element => {
          if (element.product.productId === this.product.productId) {
            return true;
          }
          return false;
          });
        if(person !== undefined) {
          this.isOrderedByThisUser = true;
        }
      },
      showDetail() {
        this.$emit('showDetail',this.product);
        this.$router.push({ name: 'productDetail', params: { categoryName: this.categoryName, productName: this.product.productName } });
      },
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
      editProduct() {
        this.$emit('actionForm',true);
        this.$router.push({name: 'editProduct', params:{productId: this.product.productId}, hash:"product"})
      },
      async getCategoryById(id) {
        await this.getCategoryByIdAction(id);
        this.categoryName = store.state.category.category.categoryName;
      }
    },
    mounted() {
      this.getCategoryById(this.product.categoryId);
      this.checkOrdered();
    }
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
  height: 500px;
}
.product figure {
  width: 100%;
  overflow: hidden;
  margin: 0px;
  padding: 0px;
  height: 200px;
}
.product img {
  transition: .5s;
  width: 100%;
  cursor: pointer;
}
.product img:hover {
  transform: scale(1.2);
}
.product-description {
  height: 160px;
  text-align: left;
}
.product-price {
  display: flex;
  justify-content: space-between;
  box-sizing: border-box;
  padding: 0px 10px;
  align-items: center;
}
.product-name {
  margin-top: 10px;
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

