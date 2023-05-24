import request from "..";

const ENTITY_PATH = "/api/v1/products";

class loadProductList extends request {
    async loadProduct(id) {
        try {
          const res = await this.requestProduct().get(`${ENTITY_PATH}${id}`);
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