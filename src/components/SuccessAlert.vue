<template>
  <div class="container">
    <div class="success">
      <div class="success-top">
        <font-awesome-icon class="success-icon" icon="fa-solid fa-circle-check" />
        <p>Success</p>
      </div>
      <div class="success-bottom">
        <div class="mesage">
          <p>{{ this.message }}</p>
        </div>
        <div class="btn">
          <button @click="this.$emit('closeAlert')">Continue</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { library } from "@fortawesome/fontawesome-svg-core";
import { faCircleCheck } from "@fortawesome/free-solid-svg-icons";
library.add(faCircleCheck);

export default {
  props: {
    message: {
      type: String,
      require: true,
      default: 'Congratulations!'
    }
  },
  data() {
    return {
    }
  }
}
</script>
<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #dadada;
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border-radius: 17px;
  background-color: rgba(0, 0, 0, 0.6);
}
.success {
  height: 20rem;
  width: 17rem;
  align-self: center;
}

.success-top {
  display: grid;
  justify-content: center;
  text-align: center;
  height: 50%;
  background-color: #8BC34A;
  color: #fff;
  border-radius: 12px 12px 0 0;
}

.success-icon {
  display: flex;
  justify-items: center;
  font-size: 4.5em;
  padding: 2rem;
  padding-bottom: 0;
}

.success-top p {
  font-size: 20px;
  text-transform: uppercase;
  font-family: source sans pro, sans-serif;
  margin: 0;
}

.success-bottom {
  text-align: center;
  justify-items: center;
  background-color: #fff;
  height: 50%;
  border-radius: 0 0 12px 12px;
}
.mesage {
  box-sizing: border-box;
  padding: 10px;
  height: 70%;
  padding-top: 3rem;
}
.mesage p{
  margin: 0;
  color: #757575;
  font-family: source sans pro, sans-serif;
  align-content: center;
}
.btn {
  height: 30%;
}
.btn button {
  cursor: pointer;
  background-color: #8BC34A;
  color: #fff;
  border: 0;
  border-radius: 18px;
  font-size: 1em;
  padding: 10px 2em;
  box-shadow: 2px 1px 5px 5px rgba(199, 198, 198, 0.5);
}
</style>

  