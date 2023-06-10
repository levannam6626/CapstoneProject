<template>
  <div class="container">
    <RegisterForm/>
  </div>
</template>
<script>
import RegisterForm from '@/components/RegisterForm.vue';

export default {
    data() {
        return {
            
        };
    },
    components: { RegisterForm }
};
</script>
<style scoped>
.container {
  width: 100%;
  background-color: #DBDBDB;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}
</style>

