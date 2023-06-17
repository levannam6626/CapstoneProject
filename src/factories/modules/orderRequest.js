import request from "..";

const ENTITY_PATH = "/api/v1/order";

class orderRequest extends request {
  
    async addProductToCart(objOrder) {
        try {
            console.log(objOrder)
          const res = await this.requestProduct().post(`${ENTITY_PATH}/add-to-cart`, objOrder );
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
export default new orderRequest();

