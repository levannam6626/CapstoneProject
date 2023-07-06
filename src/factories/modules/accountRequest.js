import request from "..";

const ENTITY_PATH = "/api/v1/";

class accountRequest extends request {
  async login(obj) {
    try {
      const res = await this.requestLogin().post(`${ENTITY_PATH}auth/login`, { ...obj });
      return res;
    } catch (err) {
      return { err: err };
    }
  }
  async register(objRegister) {
    try {
      await this.requestRegister().post(`${ENTITY_PATH}account/register`, { ...objRegister });
      return true;
    } catch (err) {
      if(err.response.status === 400) {
        return err.response.data;
      }
      return { err: err };
    }
  }
  async refreshToken(refreshToken) {
    try {
      const res = await this.requestLogin().post(`${ENTITY_PATH}auth/refreshToken`, refreshToken);
      return res;
    } catch (err) {
      return { err: err };
    }
  }
  async getUserByID(id) {
    try {
      const res = await this.requestJsonAuth().get(`${ENTITY_PATH}account/view/${id}`);
      return res;
    } catch (err) {
      if (err.response.status === 401) {
        localStorage.clear();
        this.$router.push("/login");
      }
      return { err: err };
    }
  }
  async getUsersSearch(email) {
    try {
      const res = await this.requestJsonAuth().get(`${ENTITY_PATH}account/view/get-by-search/${email}`);
      return res;
    } catch (err) {
      if (err.response.status === 401) {
        localStorage.clear();
        this.$router.push("/login");
      }
      return { err: err };
    }
  }
  async deleteAccount(accounts) {
    try {
      var res;
      accounts.forEach((account) =>{
        res = this.requestJsonAuth().delete(`${ENTITY_PATH}account/delete/${account.id}`);
      });
      return res;
    } catch (err) {
      if (err.response.status === 401) {
        localStorage.clear();
        this.$router.push("/admin");
      }
      return { err: err };
    }
  }
  async editUser(objUser) {
    try {
      const res = await this.requestFormData().patch(`${ENTITY_PATH}account/edit`, objUser);
      return res;
    } catch (err) {
      if (err.response.status === 401) {
        localStorage.clear();
        this.$router.push("/admin");
      }
      return { err: err };
    }
  }
}
export default new accountRequest();

