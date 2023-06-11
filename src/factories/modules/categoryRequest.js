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
  async createCategory(objCategory) {
    try {
      const res = await this.requestFormData().post(`${ENTITY_PATH}`, objCategory);
      return res;
    } catch (err) {
      if (err.response.status === 400) {
        alert("CategoryName is already exist")
      }
      return { err: err };
    }
  }
}
export default new categoryRequest();

