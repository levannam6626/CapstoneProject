import request from "..";

const ENTITY_PATH = "/api/v1/products";

class loadProductList extends request {
  async createProduct(product) {
    try {
      const res = await this.requestFormData().post(`${ENTITY_PATH}/add`, product);
      return res;
    } catch (err) {
      return { err: err };
    }
  }
  async loadProductById(productId) {
    try {
      const res = await this.requestProduct().get(`${ENTITY_PATH}/view/${productId}`);
      return res;
    } catch (err) {
      if (err.response.status === 400) {
        localStorage.clear();
        this.$router.push("/");
      }
      return { err: err };
    }
  }
  async searchProduct(name) {
    try {
      const res = await this.requestProduct().get(`${ENTITY_PATH}/view/get-by-search/${name}`);
      return res;
    } catch (err) {
      if (err.response.status === 401) {
        localStorage.clear();
        this.$router.push("/");
      }
      return { err: err };
    }
  }
  async searchProductByCategoryName(name) {
    try {
      const res = await this.requestProduct().get(`${ENTITY_PATH}/view/get-by-categoryname/${name}`);
      return res;
    } catch (err) {
      if (err.response.status === 401) {
        localStorage.clear();
        this.$router.push("/");
      }
      return { err: err };
    }
  }
  async editProduct(product) {
    try {
      const res = await this.requestFormData().post(`${ENTITY_PATH}/edit`, product );
      return res;
    } catch (err) {
      if (err.status === 401) {
        localStorage.clear();
        this.$router.push("/");
      }
      return { err: err };
    }
  }
  async deleteProductsById(ids) {
    try {
      const res = await this.requestProduct().delete(`${ENTITY_PATH}/delete`, ids );
      return res;
    } catch (err) {
      if (err.response.status === 401) {
        localStorage.clear();
        this.$router.push("/");
      }
      return { err: err };
    }
  }
}
export default new loadProductList();

