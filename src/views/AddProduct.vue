<template>
  <div class="create-product">
    <div class="component-close">
      <span>Create Product</span>
      <a style="text-decoration: none" href="/">X</a>
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
            v-model="this.product.productName" required />
        </div>
        <span class="message">{{ this.message.name }}</span>
        <div class="product-category">
          <label for="product-category">Category:</label>
          <select name="product-category" id="product-category"
            style=" border-radius: 5px; width: 65%; text-align: left; padding-left: 15px; box-sizing: border-box;"
            v-model="this.product.category">
            <option value="true">ACTIVE</option>
            <option value="false">PASSIVE</option>
          </select>
        </div>
      </div>
      <div class="create-product-item">
        <p>Update Date</p>
        <input type="button" value="&#8744;" v-on:click="this.showE.updateDate = !this.showE.updateDate" />
      </div>
      <div class="update-date" v-show="this.showE.updateDate">
        <label for="time">Update Date:</label>
        <div class="update-time">
          <input type="datetime-local" v-model="this.product.updateDate" required />
        </div>
      </div>
      <span class="message">{{ this.message.date }}</span>
      <div class="create-product-item">
        <p>Price</p>
        <input type="button" value="&#8744;" v-on:click="this.showE.price = !this.showE.price" />
      </div>
      <div class="price" v-show="this.showE.price">
        <label for="price">Price:</label>
        <input type="number" id="price" min="0" v-model="this.product.productPrice" />
      </div>
      <span class="message">{{ this.message.price }}</span>
      <div class="create-product-item">
        <p>Creative</p>
        <input type="button" value="&#8744;" v-on:click="this.showE.creative = !this.showE.creative" />
      </div>
      <div class="creative" v-show="this.showE.creative">
        <div class="description" style="margin-top: 25px;">
          <label for="description">Description:</label>
          <input type="text" id="description" v-model="this.product.productDescription" required />
        </div>
        <span class="message">{{ this.message.description }}</span>
        <div class="creative-preview" style="margin-top: 25px;margin-bottom: 5px;">
          <label for="creative-preview">Creative preview:</label>
          <div class="creative-preview-image">
            <img :src="newImage" />
          </div>
        </div>
        <span class="message">{{ this.message.image }}</span>
        <div class="final-url" style="margin-top: 25px;">
          <label for="final-url">Final URL:</label>
          <label for="upload">Import image
            <input type="file" accept="image/*" id="upload" style="display: none;" @change="upload($event)" required />
          </label>
        </div>
      </div>
    </div>
    <div class="component-cancel-save">
      <a href="/product">Cancel</a>
      <button type="submit" style="background-color: #468faf; border: 0px; color: white" @click="saveProduct()">
        Save
      </button>
    </div>
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
        category: "",
        updateDate: new Date().toJSON().slice(0,16),
        productImage: "",
      },
      newImage: "",
      showE: {
        product: true,
        updateDate: true,
        price: true,
        creative: true,
      },
      message: {
        name: "",
        date: "",
        price: "",
        description: "",
        image: "",
      }
    };
  },
  mounted: function(){
    
  },
  methods: {
    ...mapActions("product", ["createProductAction"]),
    
    async saveProduct() {
      const objproduct = {
        productName: this.product.productName,
        category: this.product.category,
        updateDate: this.product.updateDate,
        price: this.product.price,
        productDescription: this.product.productDescription,
      };
      const objproductDB = new FormData();
      objproductDB.append(
        "productRequest",
        new Blob([JSON.stringify(objproduct)], { type: "application/json" })
      );
      objproductDB.append("file", this.product.productImage);
      await this.createProductAction(objproductDB);
      await store.state.product.message.then((res) => {
        alert(res.message);
      })
      await this.$router.push("/product");
      this.$router.go(0);
    },
    upload(event) {
      const file = event.target.files[0];
      const theReader = new FileReader();
      theReader.onloadend = async () => {
        this.newImage = await theReader.result;
      };
      theReader.readAsDataURL(file);
      this.product.productImage = event.target.files[0];
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
  width: 50%;
  float: right;
  padding-right: 39px;
}

.name,
.update-date,
.price,
.bidding,
.product-category {
  display: flex;
  width: 100%;
  margin-bottom: 5px;
}
.update-date #time{
  width: 70%;
}
.update-date .start-time, .end-time{
  width: 50%;
}
.product label,
.update-date>label,
.price>label,
.bidding>label,
.title>label,
.description>label,
.creative-preview>label,
.final-url>label {
  padding-right: 50px;
  text-align: right;
  width: 30%;
  height: 30px;
}

label {
  padding-top: 5px;
  box-sizing: border-box;
  font-weight: 555;
}

#time {
  width: 70%;
  display: flex;
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

.title,
.description,
.creative-preview,
.final-url {
  width: 100%;
  height: 30px;
  display: flex;
}

.final-url label[for='upload'] {
  background-color: #468FAF;
  border-radius: 5px;
  width: 65%;
  text-align: center;
  color: #fff;
}

.creative-preview {
  height: 100px;
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
}

.component-cancel-save button {
  height: 30px;
  width: 100px;
  margin-left: 20px;
  border-radius: 5px;
}
</style>

