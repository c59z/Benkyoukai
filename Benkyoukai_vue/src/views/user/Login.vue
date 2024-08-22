<template>
  <div class="login-container">
    <div class="login-card">
      <div class="icon">
        <img src="../../assets/login.png" alt="icon">
      </div>
      <form @submit.prevent="login">
        <div class="input-group">
          <label for="username">Username</label>
          <input type="text" id="username" v-model="user.username" required/>
        </div>
        <div class="input-group">
          <label for="password">Password</label>
          <input type="password" id="password" v-model="user.password" required/>
        </div>
<!--        <div class="options">-->
<!--          <label>-->
<!--            <div class="form-check form-switch">-->
<!--              <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault">-->
<!--              <label class="form-check-label" for="flexSwitchCheckDefault">记住密码</label>-->
<!--            </div>-->
<!--          </label>-->
<!--          <a href="#" class="forgot-password">忘记密码</a>-->
<!--        </div>-->
        <button type="submit" class="login-btn">Log In</button>
        <button @click="this.$router.push('/register')" type="button" class="signup-btn">Register </button>
      </form>
    </div>
  </div>
</template>

<script>

import {ElMessage} from "element-plus";
import request from "../../js/request.js";

export default {
  name: "Login",
  data() {
    return {
      user: {
        username: '',
        password: '',
        rememberMe: false,
      },
    }
  },
  methods: {
    login() {
      request.post("/user/login",this.user).then(res => {
        console.log(res)
        if(res.code === 200){
          this.$store.commit('setToken',res.data.token)
          ElMessage({
            message: res.msg,
            type: 'success',
            plain: true,
          })
          request.get("/account/getUserInfo").then(res => {
            if(res.code === 200){
              this.$store.commit('setUserId',res.data.id)
              console.log(res.data)
            }else{
              ElMessage({
                message: res.msg,
                type: 'error',
                plain: true,
              })
            }
          })
          this.$router.push('/')
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
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #c892f5, #e8a1bd);
}

.login-card {
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  width: 100%;
  text-align: center;
}

.icon img {
  width: 155px;
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

.login-btn {
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