<template>
  <div class="menu" style="margin:0px;">
    <h3>PRODUCT CATEGORY</h3>
    <ul style="margin: 0px; padding: 0px;">
      <li class="menu-item" v-for="(category,index) in this.categories" :key="index">
        <a :href="'/' + category.categoryName ">{{ category.categoryName }}</a>
      </li>
    </ul>
    
  </div>
</template>
<script>
import store from '@/store';
import { mapActions } from 'vuex';

export default {
  data() {
    return {
      categories: [],
    }
  },
  methods: {
    ...mapActions('product', ['changeCategory']),
    ...mapActions('product', ['loadCategoriesAction']),

    async loadCategories(id) {
      await this.loadCategoriesAction(id);
      this.categories = store.state.product.categories;
    }
  },
  created() {
    this.loadCategories('all');
  }
}
</script>
<style scoped>

h3 {
  font-weight: 500;
  background-color: red;
  color: white;
  text-align: center;
  box-sizing: border-box;
  padding: 15px 10px;
  box-shadow: black 0px 2px;
  margin: 0px;
  margin-bottom: 2px;
}
.menu {
  height: 100%;
  overflow: auto;
  text-align: center;
  width: 100%;
  display: block;
  padding: 0px;
  text-align: left;
  border: none;
  white-space: normal;
  float: none;
  outline: 0;
  min-width: 220px;
}
.menu label {
  background-color: red;
  height: 80px;
}
.menu-item {
  list-style-type: none;
}
.menu-item a {
  display: block;
  box-sizing: border-box;
  text-transform: uppercase;
  color: #444;
  font-size: 18px;
  text-decoration: none;
  background-color: #fff;
  margin-bottom: 1px;
  padding: 10px 0px 10px 15px;
  cursor: pointer;
}
.menu-item a:hover {
  color: brown;
}
</style>

