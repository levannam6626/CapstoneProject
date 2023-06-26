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
    <div class="product-quantity" v-if="this.account.role !== 'SELLER'">
      <button @click="this.quantity = Math.max((this.quantity - 1), 1)">-</button>
      <input type="text" maxlength="3" v-model="this.quantityString" readonly/>
      <button @click="this.quantity = Math.min((this.quantity + 1), this.product.productQuantity)">+</button>
    </div>
    <div class="product-price">
      <label>${{ this.product.productPrice }}</label>
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
        quantity: 1,
        isOrderedByThisUser: false,
      };
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
      ...mapActions('category',['getCategoryByIdAction']),
      ...mapActions('product',['productDetailAction']),
      ...mapActions('cart',['addProductToCartAction']),
      checkbox(event) {
        this.checkedProduct.id = event.target.value;
        this.checkedProduct.status = this.checked;
        this.$emit('checkedProduct', this.checkedProduct)
      },
      checkOrdered() {
        // const orderedProducts = store.state.product.productsCart;
        // const person = orderedProducts.find(element => {
        //   if (element.product.productId === this.product.productId) {
        //     return true;
        //   }
        //   return false;
        //   });
        // if(person !== undefined) {
        //   this.isOrderedByThisUser = true;
        // }
      },
      async showDetail() {
        this.$emit('showDetail',this.product);
        this.$router.push({ 
          name: 'productDetail',
          params: {
            categoryName: this.categoryName,
            productName: this.product.productName,
          },
        });
        this.productDetailAction(this.product);
      },
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
p {
  padding: 0;
  margin: 0;
}
.product {
  display: grid;
  text-align: center;
  background-color: white;
  box-sizing: border-box;
  padding: 20px;
  gap: 5px;
  height: 430px;
}
.product figure {
  width: 100%;
  overflow: hidden;
  margin: 0px;
  padding: 0px;
  height: 130px;
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
  height: 108px;
}
.product-description p{
  display: -webkit-box;
  -webkit-line-clamp: 6;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-align: justify;
  text-justify: newspaper;

  height: 108px;
}
.product-quantity {
  display: flex;
  color: red;
  justify-content: center;
  align-items: center;
  height: 30px;
}
.product-quantity button {
  font-size: 21px;
  padding: 0px 8px;
  background-color: #fff;
  border: solid 1px rgb(192, 188, 188);
  cursor: pointer;
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
.product-price {
  display: flex;
  justify-content: space-between;
  box-sizing: border-box;
  align-items: center;
}
.product-name {
  margin-top: 10px;
}
.product-name a {
  color: red;
  font-size: 19px;
  height: 22px;
  cursor: pointer;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
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

