<template>
  <header>
    <h3>PRODUCT LIST</h3>
  </header>
  <div class="content">
    <ProductItem/>
    <ProductItem/>
    <ProductItem/>
    <ProductItem/>
    <ProductItem/>
    <ProductItem/>
  </div>
  <footer>

  </footer>
</template>
<script>
  import ProductItem from '@/components/ProductItem.vue';
  export default {
    data() {
      return {
        
      };
    },
    components: {
      ProductItem,
    },
  }
</script>
<style scoped>
h3 {
  background-color: red;
  height: 50px;
  color: white;
  font-weight: 400;
  box-sizing: border-box;
  padding: 15px 15px;
  box-shadow: black 0px 2px;
  margin: 0px;
  margin-bottom: 2px;
}
.content {
  display: grid;
  grid-template-columns: repeat(3,1fr);
  gap: 20px;
  box-sizing: border-box;
  padding-top: 20px;
}
@media (max-width: 1200px){
  .content {
    grid-template-columns: repeat(2,1fr);
  }
}
@media (max-width: 750px){
  .content {
    grid-template-columns: repeat(1,1fr);
  }
}
</style>
