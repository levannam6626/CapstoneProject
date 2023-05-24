import request from "..";

const ENTITY_PATH = "/api/v1/";

class accountRequest extends request {
  async login(obj) {
    try {
      const res = await this.requestLogin().post(`${ENTITY_PATH}auth`, { ...obj });
      return res;
    } catch (err) {
      if (err.response.status === 401) {
        localStorage.clear();
        this.$router.push("/login");
      }
      return { err: err };
    }
  }
  async register(objRegister) {
    try {
      const res = await this.requestRegister().post(`${ENTITY_PATH}account/register`, { ...objRegister });
      return res;
    } catch (err) {
      if (err.response.status === 401) {
        localStorage.clear();
        this.$router.push("/register");
      }
      return { err: err };
    }
  }
  async getUserByID(id) {
    try {
      console.log(id);
      const res = await this.requestLogin().get(`${ENTITY_PATH}account/${id}`);
      console.log(res);
      return res;
    } catch (err) {
      if (err.response.status === 401) {
        localStorage.clear();
        this.$router.push("/login");
      }
      return { err: err };
    }
  }
}
export default new accountRequest();

