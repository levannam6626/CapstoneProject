<template>
  <div class="product">
    <figure>
      <img src="../assets/signin-image.jpg" />
    </figure>
    <div class="product-name">
      <a href="">{{ this.productName }}</a>
    </div>
    <div class="product-description">
      <p>{{ this.productDescription }}</p>
    </div>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        productName:"Test",
        productDescription: "Test",
        product: [],
        url: "https://res.cloudinary.com/dx5ykjdqy/image/upload/",
      };
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
    margin: 0px;
    padding: 0px;
  }
</style>

