<template>
  <div class="menu" style="margin:0px;">
    <h3 v-show="showh3">PRODUCT CATEGORY</h3>
    <ul style="margin: 0px; padding: 0px;">
      <li class="menu-item" v-for="(category, index) in this.categories" :key="index" >
        <a :href="'/' + category.categoryName " :class="category.categoryName">{{ category.categoryName }}</a>
      </li>
      <li class="menu-item add" v-if="userAccount.role === 'SELLER'">
        <a @click="this.showAddCategory = true" id="add">
          <input type="text" placeholder="Category Name" class="categoryName" v-on:keyup.enter="addCategory()" v-model="this.inputString" v-show="this.showAddCategory">
          <font-awesome-icon icon="fa-solid fa-plus" class="add-icon" v-show="!this.showAddCategory" />
        </a>
      </li>
    </ul>
    
  </div>
</template>
<script>
import { library } from '@fortawesome/fontawesome-svg-core';
import { faPlus} from '@fortawesome/free-solid-svg-icons';
library.add(faPlus);
import store from '@/store';
import { mapActions } from 'vuex';

export default {
  props: {
    showh3: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      categories: [],
      showAddCategory: false,
      inputString: "",
      userAccount: store.state.auth.userAccount,
      categoryName: this.$route.params.categoryName
    }
  },
  methods: {
    ...mapActions('category', ['addCategoryAction','loadCategoriesAction']),

    async loadCategories(id) {
      await this.loadCategoriesAction(id);
      this.categories = store.state.category.categories;
    },
    async addCategory() {
      if(this.inputString !== "") {
        const categoryName = this.capitalizeString(this.inputString)
        const objCategory = {
          categoryName: categoryName,
        }
        this.$el.querySelector('#add').style.cursor = 'progress';
        await this.addCategoryAction(objCategory);
        this.$el.querySelector('#add').style.cursor = 'pointer';
        this.categories = store.state.category.categories;
        this.inputString = "";
      }
      this.showAddCategory = false;
    },
    // Capitalize the first letter of each word in a string
    capitalizeString(string) {
      const arr = string.split(" ");
      for (var i = 0; i < arr.length; i++) {
        arr[i] = arr[i].charAt(0).toUpperCase() + arr[i].slice(1).toLowerCase();
      }
      return arr.join(" ");
    }
  },
  created() {
    this.loadCategories('all');
  },
  mounted() {
    if(this.categoryName !== undefined) {
      window.addEventListener('load', () => {
        const elements = document.getElementsByClassName(this.categoryName);
        if(elements.length > 0) {
          for (let index = 0; index < elements.length; index++) {
            elements[index].style.backgroundColor = "#90AA00";
            elements[index].style.color = "red";
          }
        }
      });
    }
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
  padding: 0px;
  text-align: left;
  border: none;
  white-space: normal;
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
  border-bottom: 1px solid #DDDDDD;
  padding: 10px;
  cursor: pointer;
}
.menu-item a:hover {
  color: red;
  background-color: #90AA00;
}
.add {
  text-align: center;
}
.add-icon {
  font-size: 26px !important;
}
.add:hover .add-icon{
  color: red;
  transform: scale(1.2);
}
.categoryName {
  height: 30px;
  box-sizing: border-box;
  border: 0px;
  border-bottom: solid 1px red;
  padding-left: 5px;
  width: 100%;
  font-size: 18px;
  color: #444;
  text-transform: capitalize;
}
.categoryName:focus {
  outline: none;
}
</style>

