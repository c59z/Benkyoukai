<template>
  <div class="register-container">
    <div class="register-card">
      <div class="icon">
        <img src="../../assets/register.png" alt="icon">
      </div>
      <form @submit.prevent="register">
        <div class="input-group">
          <label for="username">Username</label>
          <input type="text" id="username" v-model="user.username" required/>
        </div>
        <div class="input-group">
          <label for="password">Password</label>
          <input type="password" id="password" v-model="user.password" required/>
        </div>
        <div class="input-group">
          <label for="password">Password</label>
          <input type="password" id="password" v-model="user.confirm" required/>
        </div>
        <button type="submit" class="register-btn">Register</button>
        <button @click="this.$router.push('/login')" type="button" class="signup-btn">Log In</button>
      </form>
    </div>
  </div>
</template>

<script>
import request from "../../js/request.js";
import {ElMessage} from "element-plus";

export default {
  name: "Register",
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirm: '',
      },
    }
  },
  methods: {
    register() {
      request.post("/user/register",this.user).then(res => {
        console.log(res)
        if(res.code === 200){
          ElMessage({
            message: "注册用户成功！快去登录吧！",
            type: 'success',
            plain: true,
          })
          this.$router.push('/login')
        }else{
          ElMessage({
            message: res.msg,
            type: 'error',
            plain: true,
          })
        }
      })
    }
  },
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #c892f5, #e8a1bd);
}

.register-card {
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  width: 100%;
  text-align: center;
}

.icon img {
  width: 218px;
  height: 94px;
  margin-bottom: 20px;
}

h2 {
  margin: 0 0 10px;
}

p {
  color: #666;
  font-size: 14px;
  margin-bottom: 20px;
}

.input-group {
  color: #1a1a1a;
  margin-bottom: 15px;
  text-align: left;
}

.input-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.input-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.options {
  color: #1a1a1a;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.forgot-password {
  color: #f46b45;
}

.register-btn {
  width: 100%;
  padding: 10px;
  background: #6a11cb;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-bottom: 10px;
}

.signup-btn {
  width: 100%;
  padding: 10px;
  background: #f46b45;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
</style>