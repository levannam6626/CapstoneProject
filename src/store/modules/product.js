import productRequest from "@/factories/modules/productRequest";

export default {
  namespaced: true,
  state: {
    products:[],
    messages: {
      edit: '',
      add: '',
      delete: '',
    },
    url: "https://res.cloudinary.com/dp5nmrbwu/image/upload/",
    product: {},
    productDetail: {}
  },
  actions: {
    async loadProductByIdAction(content, productId) {
      let res =  await productRequest.loadProductById(productId);
      if(res.status === 200) {
        content.commit('loadProductByIdMutation', res.data);
      }
    },
    async createProductAction(content, product) {
      let res = productRequest.createProduct(product);
      await res.then(arr => {
        if(arr.status === 200) {
          content.commit('createProductMutation', "Success");
        }
      })
    },
    async searchProductAction(content, name) {
      let res = productRequest.searchProduct(name);
      await res.then(array => {
        if(array.status === 200) {
          content.commit('searchProductMutation', array.data);
        }
      });
    },
    async editProductAction(content, product) {
      let res = productRequest.editProduct(product);
      await res.then(array => {
        if(array.status === 200) {
          content.commit('editProductMutation', "Success");
        }
      });
    },
    async searchProductByCategoryNameAction(content, name) {
      let res =productRequest.searchProductByCategoryName(name);
      await res.then(array => {
        if(array.status === 200) {
          content.commit('searchProductByCategoryNameMutation',array.data);
        }
      });
    },
    async deleteProductsAction(content, ids) {
      let res = productRequest.deleteProductsById(ids);
      console.log(res);
      await res.then(arr => {
        if(arr.status === 200) {
          content.commit('deleteProductsMutation', "Success");
        }
      })
    },
    productDetailAction(content, productDetail) {
      content.commit('productDetailMutation',productDetail);
    }
  },
  mutations: {
    loadProductByIdMutation(state, product) {
      state.product = product;
    },
    createProductMutation(state, message) {
      state.messages.add = message;
    },
    editProductMutation(state, message) {
      state.messages.edit = message;
    },
    loadProductMutation(state, data) {
      state.products = data;
    },
    searchProductMutation(state, products) {
      state.products = products;
    },
    searchProductByCategoryNameMutation(state, data) {
      state.products = data;
    },
    deleteProductsMutation(state, message) {
      state.messages.delete = message;
    },
    productDetailMutation(state, productDetail){
      state.productDetail = productDetail;
    }
  },
};

