<template>
  <div class="create-product" ref="createProduct">
    <form @submit.prevent="saveProduct" class="create-product-form">
      <div class="content">
        <div class="create-product-item">
          <p>Product</p>
          <!-- <input type="button" value="&#8744;" v-on:click="this.showE.product = !this.showE.product" /> -->
        </div>
        <div class="product" v-show="this.showE.product">
          <div class="name">
            <label for="name">Name:</label>
            <input type="text" id="name" style=" width: 65%; border-radius: 5px; border: solid 1px black; "
              v-model="this.product.productName" maxlength="80" required />
          </div>
          <div class="product-price">
            <label for="price">Price:</label>
            <input type="number" id="price" min="0" max="1000000" step=".01" v-model="this.product.productPrice" required/>
          </div>
          <div class="quantity">
            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" min="0" max="1000" v-model="this.product.productQuantity" required/>
          </div>
          <div class="product-category">
            <label for="product-category">Category:</label>
            <select name="product-category" id="product-category" v-model="this.product.category" :require="true">
              <option v-for="(category, index) in categories" :key="index" :value="category">{{category.categoryName }}</option>
            </select>
          </div>
        </div>
        <div class="create-product-item">
          <p>Creative Image</p>
          <!-- <input type="button" value="&#8744;" v-on:click="this.showE.creative = !this.showE.creative" /> -->
        </div>
        <div class="creative" v-show="this.showE.creative">
          <div class="description">
            <label for="description">Description:</label>
            <textarea id="description" v-model="this.product.productDescription"  maxlength="255" required ></textarea>
          </div>
          <div class="creative-preview">
            <label >Creative preview:</label>
            <div class="creative-preview-image" >
              <img :src="newImage" required/>
            </div>
          </div>
          <span class="message" v-show="!this.image.status">{{ this.image.message }}</span>
          <div class="final-url">
            <label>Image:</label>
            <label for="upload">Import image
              <input type="file" accept="image/*" id="upload" style="display: none;" @change="upload($event)" />
            </label>
          </div>
        </div>
      </div>
      <div class="component-cancel-save">
        <a @click="$emit('actionForm',false);this.$router.push('/');">Cancel</a>
        <button type="submit" id="submit">
          Save
        </button>
      </div>
    </form>
  </div>
</template>
<script>
import { mapActions } from "vuex";
import store from "../store";
export default {
  data() {
    return {
      product: {
        productName: "",
        productDescription: "",
        productPrice: 0,
        productQuantity: 1,
        category: store.state.category.categories[0],
        productImage: "",
      },
      categories: store.state.category.categories,
      newImage: "",
      showE: {
        product: true,
        price: true,
        creative: true,
      },
      image:{
        status: false,
        message: '',
      },
    };
  },
  methods: {
    ...mapActions("product", ["createProductAction"]),
    async saveProduct() {
      if(this.image.status) {
        const objproduct = {
          productName: this.product.productName,
          categoryId: this.product.category.id,
          productPrice: this.product.productPrice,
          productQuantity: this.product.productQuantity,
          productDescription: this.product.productDescription,
        };
        const objproductDB = new FormData();
        objproductDB.append(
          "productRequest",
          new Blob([JSON.stringify(objproduct)], { type: "application/json" })
        );
        objproductDB.append('file', this.product.productImage);
        this.$el.querySelector('#submit').style.cursor = 'progress';
        await this.createProductAction(objproductDB);
        this.$el.querySelector('#submit').style.cursor = 'pointer';
        this.$router.push('/'+this.product.category.categoryName)
      }else {
        this.image.message = 'Please import product image';
      }
    },
    upload(event) {
      if(event.target.files.length > 0) {
        const file = event.target.files[0];
        const theReader = new FileReader();
        theReader.onloadend = async () => {
          this.newImage = await theReader.result;
        };
        theReader.readAsDataURL(file);
        this.product.productImage = event.target.files[0];
        this.image.status = true;
      }
    },
  },
};
</script>
<style scoped>
.create-product {
  display: flex;
  justify-content: center;
  width: 100%;
  height: 100%;
}

.create-product-form {
  border-radius: 8px;
  border: solid 1px rgb(189, 186, 186);
  width: 75%;
  background-color: white;
}

.content {
  box-sizing: border-box;
  padding: 20px;
}

.component-close {
  height: 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-sizing: border-box;
  padding: 0px 20px;
  border-radius: 8px;
  border: solid 1px rgb(189, 186, 186);
}

.component-close span {
  font-weight: bold;
  font-size: 18px;
}

.component-close a {
  border: 0;
  background-color: #fff;
  font-size: 18px;
  font-weight: bold;
  color: rgb(180, 171, 171);
  cursor: pointer;
}

input[type="button"] {
  background-color: #468faf;
  border: none;
  box-sizing: border-box;
  font-size: 20px;
  color: #fff;
}
.create-product-item {
  display: flex;
  justify-content: space-between;
  background-color: #468faf;
  margin-bottom: 10px;
  width: 100%;
  border-radius: 5px;
  padding: 0px 15px;
  box-sizing: border-box;
  color: white;
  align-items: center;
}

.product {
  float: left;
  width: 100%;
}

.message {
  color: red;
  width: 65%;
  margin-right: 5%;
  text-align: center;
  float: right;
  font-size: 12px;
}

.name,
.product-price,
.quantity,
.product-category {
  display: flex;
  width: 100%;
  margin-bottom: 5px;
}
.product-category option {
  text-transform: uppercase;
}
#product-category {
  border-radius: 5px;
  width: 65%;
  text-align: left;
  padding-left: 10px;
  box-sizing: border-box;
  text-transform: uppercase;
}
.product label,
.product-price>label,
.quantity >label,
.description>label,
.creative-preview>label,
.final-url>label {
  padding-right: 5%;
  text-align: right;
  width: 30%;
}
label[for='final-url'] {
  padding-right: 50px;
}
label {
  box-sizing: border-box;
  font-weight: 555;
  height: 30px;
  padding-top: 3px;
}
input[type="text"] {
  padding-left: 15px;
  box-sizing: border-box;
}
input[type="number"] {
  width: 65%;
  height: 30px;
  border-radius: 5px;
  border: solid 1px black;
  padding-left: 15px;
  box-sizing: border-box;
}
.description,
.creative-preview {
  height: 100px;
}
.description,
.creative-preview,
.final-url {
  width: 100%;
  display: flex;
  margin-top: 15px;
}
.description textarea {
  width: 65%;
  font-size: 15px;
  resize: none;
}
.final-url label[for='upload'] {
  background-color: #468faf;
  border-radius: 5px;
  width: 65%;
  text-align: center;
  color: #fff;
  cursor: pointer;
}
.final-url label[for='upload']:hover {
  background-color: blue;
}
.creative input[type="text"] {
  width: 65%;
  height: 26px;
  border-radius: 5px;
  border: solid 1px black;
}
.creative-preview-image {
  width: 65%;
  height: 90px;
  margin-bottom: 20px;
  border: solid 2px black;
  text-align: center;
  border-radius: 5px;
}
.creative-preview img {
  height: 100%;
  width: 65%;
  border-radius: 5px;
}
.component-cancel-save {
  height: 40px;
  width: 100%;
  align-items: center;
  box-sizing: border-box;
  padding: 5px 20px;
  border-radius: 8px;
  border: solid 1px rgb(189, 186, 186);
  display: flex;
  justify-content: right;
}
.component-cancel-save a {
  text-decoration: none;
  height: 30px;
  border-radius: 5px;
  background-color: #468faf;
  color: #fff;
  width: 100px;
  text-align: center;
  padding-top: 5px;
  box-sizing: border-box;
  cursor: pointer;
}
.component-cancel-save a:hover, .component-cancel-save button:hover {
  background-color: blue;  
}
.component-cancel-save button {
  height: 30px;
  width: 100px;
  margin-left: 20px;
  border-radius: 5px;
  background-color: #468faf; border: 0px; color: white;
  cursor: pointer;
}
</style>

