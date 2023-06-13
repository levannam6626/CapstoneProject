<template>
  <div class="create-product" ref="createProduct">
    <form @submit.prevent="saveProduct">
      <div class="component-close">
        <span>Create Product</span>
        <a style="text-decoration: none" @click="$emit('actionForm',false);this.$router.push('/');">X</a>
      </div>
      <div class="content">
        <div class="create-product-item">
          <p>Product</p>
          <input type="button" value="&#8744;" v-on:click="this.showE.product = !this.showE.product" />
        </div>
        <div class="product" v-show="this.showE.product">
          <div class="name">
            <label for="name">Name:</label>
            <input type="text" id="name" style=" width: 65%; border-radius: 5px; border: solid 1px black; height: 30px; "
              v-model="this.product.productName" maxlength="80" required />
          </div>
          <div class="product-category">
            <label for="product-category">Category:</label>
            <select name="product-category" id="product-category" v-model="this.product.category" :require="true">
              <option v-for="(category, index) in categories" :key="index" :value="category">{{category.categoryName }}</option>
            </select>
          </div>
        </div>
        <div class="create-product-item">
          <p>Price</p>
          <input type="button" value="&#8744;" v-on:click="this.showE.price = !this.showE.price" />
        </div>
        <div class="price" v-show="this.showE.price">
          <label for="price">Price:</label>
          <input type="number" id="price" min="0" max="1000000" step=".01" v-model="this.product.productPrice" required/>
        </div>
        <div class="create-product-item">
          <p>Creative</p>
          <input type="button" value="&#8744;" v-on:click="this.showE.creative = !this.showE.creative" />
        </div>
        <div class="creative" v-show="this.showE.creative">
          <div class="description" style="margin-top: 25px;">
            <label for="description">Description:</label>
            <textarea id="description" v-model="this.product.productDescription"  maxlength="255" required ></textarea>
          </div>
          <div class="creative-preview" style="margin-top: 25px;margin-bottom: 5px;">
            <label >Creative preview:</label>
            <div class="creative-preview-image" >
              <img :src="newImage" required/>
            </div>
          </div>
          <span class="message" v-show="!this.image.status">{{ this.image.message }}</span>
          <div class="final-url" style="margin-top: 25px;">
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
        category: store.state.category.categories[0],
        updateDate: new Date().toJSON().slice(0,16),
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
  mounted: function(){
    this.focusTop();
  },
  methods: {
    ...mapActions("product", ["createProductAction"]),
    focusTop() {
      this.$refs.createProduct.scrollIntoView({behavior: 'smooth'});
    },
    async saveProduct() {
      if(this.image.status) {
        const objproduct = {
          productName: this.product.productName,
          categoryId: this.product.category.id,
          updateDate: this.product.updateDate,
          productPrice: this.product.productPrice,
          productDescription: this.product.productDescription,
        };
        const objproductDB = new FormData();
        console.log(objproduct)
        objproductDB.append(
          "productRequest",
          new Blob([JSON.stringify(objproduct)], { type: "application/json" })
        );
        objproductDB.append('file', this.product.productImage);
        this.$el.querySelector('#submit').style.cursor = 'progress';
        await this.createProductAction(objproductDB);
        this.$el.querySelector('#submit').style.cursor = 'pointer';
        if(store.state.product.messages.add === "Success") {
          this.$emit('actionForm', false);
          this.$emit('reloadProductList');
        }
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
  background-color: white;
  border-radius: 8px;
  border: solid 1px rgb(189, 186, 186);
}

.content {
  box-sizing: border-box;
  padding: 20px;
}

.component-close {
  height: 40px;
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

.component-close a:hover {
  color: red;
}

input[type="button"] {
  background-color: #468faf;
  border: none;
  box-sizing: border-box;
  font-size: 20px;
  color: #fff;
  padding-top: 8px;
}

input[type="button"]:hover {
  color: red;
}

input[type="datetime-local"] {
  height: 26px;
  border-top: 0px;
  border-right: 0px;
  border-left: 0px;
  border-bottom: solid 2px rgb(180, 171, 171);
  font-size: 15px;
  box-sizing: border-box;
  width: 100%;
  margin-left: 5px;
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
}

.product {
  float: left;
  width: 100%;
}

.creative {
  height: 300px;
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
.price,
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
.price>label,
.description>label,
.creative-preview>label,
.final-url>label {
  padding-right: 5%;
  text-align: right;
  width: 30%;
  height: 30px;
}
label[for='final-url'] {
  padding-right: 50px;
}
label {
  padding-top: 5px;
  box-sizing: border-box;
  font-weight: 555;
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
.creative-preview,
.final-url {
  width: 100%;
  height: 100px;
  display: flex;
}
.description textarea {
  width: 65%;
  font-size: 15px;
  resize: none;
}
.final-url label[for='upload'] {
  background-color: #468FAF;
  border-radius: 5px;
  width: 65%;
  text-align: center;
  color: #fff;
  cursor: pointer;
}
.final-url label[for='upload']:hover {
  background-color: red;
}
.creative input[type="text"] {
  width: 65%;
  height: 30px;
  border-radius: 5px;
  border: solid 1px black;
}
.creative-preview-image {
  width: 65%;
  height: 100px;
  margin-bottom: 20px;
  border: solid 2px black;
  text-align: center;
  border-radius: 5px;
}
.creative-preview img {
  height: 100px;
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
  background-color: red;  
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

