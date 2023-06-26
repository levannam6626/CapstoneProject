import request from "..";

const ENTITY_PATH = "/api/v1/cart";

class orderRequest extends request {
  async addProductToCart(objCart) {
    try {
      const res = await this.requestFormData().post(`${ENTITY_PATH}/add`, objCart );
      return res;
    } catch (err) {
      if (err.response.status === 401) {
        localStorage.clear();
        this.$router.push("/");
      }
      return { err: err };
    }
  }
  async getCart() {
    try {
      const res = await this.requestJsonAuth().get(`${ENTITY_PATH}/get`);
      return res;
    } catch (err) {
      if (err.response.status === 401) {
        localStorage.clear();
        this.$router.push("/");
      }
      return { err: err };
    }
  }
  async changeSelected(cartItem) {
    try {
      const res = await this.requestFormData().post(`${ENTITY_PATH}/update`, cartItem);
      return res;
    } catch (err) {
      if (err.response.status === 401) {
        localStorage.clear();
        this.$router.push("/");
      }
      return { err: err };
    }
  }
  async delCartItem(cartItem) {
    try {
      const res = await this.requestJsonAuth().delete(`${ENTITY_PATH}/delete/` + cartItem.id);
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
