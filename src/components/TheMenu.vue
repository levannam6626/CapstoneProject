<template>
  <div class="menu" style="margin:0px;">
    <h3 v-show="showh3">PRODUCT CATEGORY</h3>
    <ul style="margin: 0px; padding: 0px;">
      <li class="menu-item" v-for="(category, index) in this.categories" :key="index" >
        <a :href="'/' + category.categoryName " :class="category.categoryName">{{ category.categoryName }}</a>
        <button :class="category.categoryName" @click="deleteCategoryById(category.id)" style="float: right;" v-if="userAccount.role === 'SELLER'"><font-awesome-icon icon="fa-solid fa-minus" /></button>
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
import { faMinus, faPlus} from '@fortawesome/free-solid-svg-icons';
library.add(faMinus, faPlus);
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
      showAddCategory: false,
      inputString: "",
      userAccount: store.state.auth.userAccount,
      categoryName: this.$route.params.categoryName
    }
  },
  watch: {
    url() {
      this.setBackgroundMenuItem();
    }
  },
  computed: {
    url() {
      return this.$route;
    },
    categories() {
      return store.state.category.categories;
    }
  },
  methods: {
    ...mapActions('category', ['addCategoryAction','loadCategoriesAction','deleteCategoryByIdAction']),

    async loadCategories(id) {
      await this.loadCategoriesAction(id);
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
        this.inputString = "";
      }
      this.showAddCategory = false;
    },
    async deleteCategoryById(id) {
      if(confirm("Do you really want to delete?")){
        await this.deleteCategoryByIdAction(id)
      }
    },
    // Capitalize the first letter of each word in a string
    capitalizeString(string) {
      const arr = string.split(" ");
      for (var i = 0; i < arr.length; i++) {
        arr[i] = arr[i].charAt(0).toUpperCase() + arr[i].slice(1).toLowerCase();
      }
      return arr.join(" ");
    },
    setBackgroundMenuItem() {
      const elements = document.getElementsByClassName('menu-item');
      if(elements.length > 0) {
        for (let index = 0; index < elements.length; index++) {
          elements[index].querySelector('a').style.backgroundColor = "#fff";
          elements[index].querySelector('a').style.color = "black";
          elements[index].querySelector('button').style.backgroundColor = "#fff";
          elements[index].querySelector('button').style.color = "black";
        }
      }
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
  display: flex;
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
  width: 100%;
}
.menu-item a:hover {
  color: red !important;
  background-color: #90AA00 !important;
}
.menu-item a:hover + button{
  color: red !important;
  background-color: #90AA00 !important;
}
.menu-item button {
  border: 0;
  background-color: #fff;
  border-bottom: 1px solid #DDDDDD;
  font-weight: bold;
  padding: 0px 10px;
  cursor: pointer;
}
.menu-item button:hover {
  color: red;
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

