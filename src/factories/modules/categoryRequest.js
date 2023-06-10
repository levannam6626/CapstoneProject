import request from "..";

const ENTITY_PATH = "/api/v1/category";

class categoryRequest extends request {
  
  async loadCategories(id) {
    try {
      const res = await this.requestCategory().get(`${ENTITY_PATH}/${id}`);
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
export default new categoryRequest();

