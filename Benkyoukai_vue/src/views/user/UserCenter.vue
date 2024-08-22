<template>
  <div id="user">
    <div class="user-details">
      <img class="userBackImg" src="https://s2.loli.net/2024/06/18/JAzLy5R2oFMNcTd.jpg" alt="bg">
      <div class="userInfo">
        <div class="userIcon col-2">
          <img :src="userInfo.avatar" alt="">
        </div>
        <div style="display: inline-block;text-align: left" class="col-6">
          <div class="username">{{userInfo.nickname}}</div>
          <div class="nickname">{{userInfo.username}}</div>
          <span class="badge rounded-pill text-bg-secondary">{{userInfo.email}}</span>
          <span class="btn-changeUserInfo" v-show="userId === this.currentUserId">
            <button class="btn btn-primary btn-sm"
                    data-bs-toggle="modal"
                    data-bs-target="#changeUserInfo"
                    data-bs-whatever="@getbootstrap"
                    @click="setUserInfoDto"
            >{{$t('userCenter_changeInfo')}}</button>
          </span>
          <span class="btn-changePwd" v-show="userId === this.currentUserId">
            <button class="btn btn-primary btn-sm"
                    data-bs-toggle="modal"
                    data-bs-target="#changePwd"
                    data-bs-whatever="@getbootstrap"
            >{{$t('userCenter_changePwd')}}</button>
          </span>

          <span class="btn-logout" v-show="userId === this.currentUserId">
            <button class="btn btn-secondary btn-sm"
                    @click="logout"
            >{{$t('userCenter_logout')}}</button>
          </span>

        </div>
      </div>
    </div>
    <div class="userPosted">
      <nav class="navbar nav-pills navbar-expand-lg bg-body-tertiary">
        <div class="container">
            <div class="navbar-nav">
              <button @click="changeState(1)" class="nav-link" :class="{ active: selectedState === 1 }">{{$t('userCenter_label_article')}}</button>
              <button @click="changeState(2)" class="nav-link" :class="{ active: selectedState === 2 }">{{$t('userCenter_label_favorite')}}</button>
<!--              <button @click="changeState(3)" class="nav-link" :class="{ active: selectedState === 3 }">评论</button>-->
<!--              <button class="nav-link">帖子</button>-->
            </div>
        </div>
      </nav>
      <div class="user-context">
<!--        <div class="tag-nav">-->
<!--          <div class="dropdown-center">-->
<!--            <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">-->
<!--              排序-->
<!--            </button>-->
<!--            <ul class="dropdown-menu">-->
<!--              <li><button class="dropdown-item" href="#">发布时间降序</button></li>-->
<!--              <li><button class="dropdown-item" href="#">发布时间升序</button></li>-->
<!--&lt;!&ndash;              <li><button class="dropdown-item" href="#">点赞数最高</button></li>&ndash;&gt;-->
<!--            </ul>-->
<!--          </div>-->
<!--        </div>-->
        <div class="article-context">
          <div class="article-list row">
            <div class="card article-info col-3" v-for="article in processedArticles">
              <a :href="'/article/'+article.id">
                <img :src="article.thumbnail" class="card-img-top" alt="...">
              </a>

              <div class="card-body bg-light">
                <h5 class="card-title">
                  <a :href="'/article/'+article.id">
                    {{ article.title }}
                  </a>
                </h5>
                <div class="card-tags">
            <span
                v-for="tag in article.categoryTagsArray"
                :key="tag"
                class="badge text-bg-secondary"> <a href="#"># {{ tag }}</a> </span>
                </div>
                <div class="row">
                  <a :href="'/user/'+article.createBy" class="col-2">
                    <img class="author" :src="article.avatar" alt="">
                  </a>
                  <!--            <div class="posted col-4">-->
                  <!--              2024-6-14-->
                  <!--            </div>-->
                  <div class="article-data col-6">
              <span>
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye"
           viewBox="0 0 16 16">
  <path
      d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
  <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
</svg>
                {{ article.viewCount }}
              </span>

                    <span>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart"
                     viewBox="0 0 16 16">
  <path
      d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>
</svg>
                {{ article.likeCount }}
              </span>
                  </div>
                </div>
              </div>
            </div>


          </div>

          <div class="show-more-btn">
            <button class="btn btn-primary" v-if="!allLoaded && this.articles.length > 0" @click="loadMoreArticles">{{$t('see_more')}}</button>
            <span v-else>{{$t('no_more')}}</span>
          </div>

        </div>



      </div>
    </div>


    <!-- 修改密码 -->
    <div class="modal fade" data-bs-backdrop="static" id="changePwd" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content bg-dark">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="changePwdLabel">{{$t('userCenter_changePwd')}}</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form>
              <div class="mb-3">
                <label for="passwordNew" class="col-form-label">{{$t('userCenter_label_updatePasswordNew')}}:</label>
                <input v-model="changePwd.password" type="password" class="form-control" id="recipient-name">
              </div>
              <div class="mb-3">
                <label for="passwordCheck" class="col-form-label">{{$t('userCenter_label_updatePasswordConfirm')}}:</label>
                <input v-model="changePwd.confirm" type="password" class="form-control" id="recipient-name">
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button @click="clearChangePwd" type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('btn_Cancel')}}</button>
            <button @click="changePassword" type="button" class="btn btn-primary" data-bs-dismiss="modal">{{ $t('btn_yes') }}</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 修改个人信息 -->
    <div class="modal fade modal-lg" data-bs-backdrop="static" id="changeUserInfo" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content bg-dark">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="changePwdLabel">{{$t('userCenter_changeInfo')}}</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="avatarUpdateDiv">
              <div class="input-group mb-3">
                <input @change="uploadAvatar" type="file" class="form-control" id="inputGroupFile02">
                <label class="input-group-text" for="inputGroupFile02">{{$t('userCenter_label_uploadAvatar')}}</label>
              </div>
              <div class="avatarO userIconUpdate">
                <img :src="this.userInfo.avatar" alt="">
                <button @click="saveOldAvatar" class="btn btn-primary" data-bs-dismiss="modal">{{$t('userCenter_label_saveAvatarOld')}}</button>
              </div>
              <div v-show="this.avatarNew" class="vr"></div>
              <div v-show="this.avatarNew" class="avatarN userIconUpdate">
                <img :src="this.avatarNew" alt="">
                <button @click="saveNewAvatar" class="btn btn-primary" data-bs-dismiss="modal">{{$t('userCenter_label_saveAvatarNew')}}</button>
              </div>
            </div>
            <hr>
            <div class="baseInfoUpdateDiv row">

              <div class="col-6">
                <label for="username" class="col-form-label">{{$t('userCenter_label_updateUsername')}}</label>
                <input v-model="this.UserInfoDto.username" type="text" class="form-control" id="recipient-name" disabled>
              </div>

              <div class="col-6">
                <label for="nickname" class="col-form-label">{{$t('userCenter_label_updateNickname')}}</label>
                <input v-model="this.UserInfoDto.nickname" type="text" class="form-control" id="recipient-name">
              </div>

              <div class="col-12">
                <label for="nickname" class="col-form-label">{{$t('userCenter_label_updateEmail')}}</label>
                <input v-model="this.UserInfoDto.email" type="text" class="form-control" id="recipient-name">
              </div>

            </div>
          </div>
          <div class="modal-footer">
            <button @click="clearUserInfoDto" type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('btn_Cancel')}}</button>
            <button @click="changeUserInfo" type="button" class="btn btn-primary" data-bs-dismiss="modal">{{$t('btn_yes')}}</button>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import '../../css/style.css'
import request from "../../js/request.js";
import {ElMessage} from "element-plus";
import store from "../../js/store.js";
import router from "../../js/router.js";

export default {
  name: "UserCenter",
  data() {
    return {
      articles: [
      ],
      userInfo:{

      },
      changePwd: {

      },
      UserInfoDto: {

      },
      avatarNew: "",
      avatarUploaded: "",
      currentUserId: this.$store.state.userId,
      selectedState: 1,
      currentPage: 1,
      isLoading: false,
      allLoaded: false,
    }
  },
  methods: {
    getUserInfo(){
      request.get(`/account/getUserInfo/${this.userId}`).then(res => {
        if(res.code === 200){
          this.userInfo = res.data
        }else{
          ElMessage({
            message: res.msg,
            type: 'error',
            plain: true,
          })
          this.$router.push('/')
        }
      })
    },
    getArticlesByUser(page) {
      if (this.isLoading || this.allLoaded) return;
      this.isLoading = true;
      request.get(`/article/getArticlesByUserId?page=${page}&userId=${this.userId}`).then(res => {
        if (res.data.length > 0) {
          this.articles.push(...res.data);
          this.isLoading = false
        } else {
          this.allLoaded = true;
        }
      });
    },
    getUserFavorites(page) {
      if (this.isLoading || this.allLoaded) return;
      this.isLoading = true;
      request.get(`/favorites/list/${this.userInfo.id}/${page}`).then(res => {
        if (res.data.length > 0) {
          this.articles.push(...res.data);
          this.isLoading = false
        } else {
          this.allLoaded = true;
        }
      });
    },
    loadMoreArticles() {
      this.currentPage++;
      if(this.selectedState === 1){
        this.getArticlesByUser(this.currentPage);
      }else if(this.selectedState === 2){
        this.getUserFavorites(this.currentPage);
      }
    },
    changeState(state){
      this.selectedState = state
      this.currentPage = 1
      this.articles = []
      this.isLoading = false
      this.allLoaded = false
      if(this.selectedState === 1){
        this.getArticlesByUser(1)
      }else if(this.selectedState === 2){
        this.getUserFavorites(1)
      }
    },
    clearChangePwd(){
      this.changePwd = {}
    },
    changePassword(){
      request.post("/account/changePwd",this.changePwd).then(res => {
        if(res.code === 200){
          ElMessage({
            message: res.msg,
            type: 'success',
            plain: true,
          })
          store.commit("setToken",'')
          localStorage.removeItem('token')
          this.$router.push('/login')
        }else{
          ElMessage({
            message: res.msg,
            type: 'error',
            plain: true,
          })
        }
      })
    },
    clearUserInfoDto(){
      this.UserInfoDto = {}
    },
    checkEmail(str) {
      const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      if (!emailPattern.test(str)) {
        return false
      } else {
        return true
      }
    },
    setUserInfoDto(){
      this.UserInfoDto = this.userInfo
    },
    changeUserInfo() {
      // todo 保存用户信息
      const b = this.checkEmail(this.UserInfoDto.email)
      if(!b){
        ElMessage({
          message: "邮箱格式错误",
          type: 'warning',
          plain: true,
        })
        return
      }

      request.put("/account/update",this.UserInfoDto).then(res => {
        if(res.code === 200){
          ElMessage({
            message: res.msg,
            type: 'success',
            plain: true,
          })
        }else{
          ElMessage({
            message: res.msg,
            type: 'error',
            plain: true,
          })
        }
      })
    },
    uploadAvatar(event){
      const file = event.target.files[0];
      if(file){
        const formData = new FormData();
        formData.append('file', file);

        request.post("/account/uploadAvatar",formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(res => {
          if(res.code === 200){
            this.avatarNew = res.data
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
    saveOldAvatar(){
      if(this.avatarNew){
        var temp = this.avatarNew.split("/").pop();
        console.log(temp)
        // 删除新头像
        request.post("/uploadAvatar/del/"+temp).then(res => {
          if(res.code === 200){
            ElMessage({
              message: "保留旧头像!",
              type: 'success',
              plain: true,
            })
          }else{
            ElMessage({
              message: res.msg,
              type: 'error',
              plain: true,
            })
          }
        })
        temp = null
      }
      this.avatarNew = "";
    },
    saveNewAvatar(){
      if(this.avatarNew){
        var temp = this.avatarNew.split("/").pop();
        request.post("/account/saveAvatar/"+temp).then(res => {
          if(res.code === 200){
            ElMessage({
              message: "更换头像成功!",
              type: 'success',
              plain: true,
            })
            this.getUserInfo()
          }else{
            ElMessage({
              message: res.msg,
              type: 'error',
              plain: true,
            })
          }
        })
        this.avatarNew = "";
        temp = null
      }
    },
    logout(){
      request.post("/user/logout").then(res => {
        ElMessage({
          message: res.msg,
          type: 'success',
          plain: true,
        })
        store.commit("setToken",'')
        localStorage.removeItem('token')
        store.commit("setUserId",'')
        localStorage.removeItem('userId')
        router.push('/')
      })
    }
  },
  mounted() {
    this.getUserInfo()
    this.getArticlesByUser(1)
  },
  computed: {
    processedArticles() {
      return this.articles.map(article => {
        return {
          ...article,
          categoryTagsArray: article.categoryTags ? article.categoryTags.split(",") : []
        };
      });
    },
    userId() {
      return this.$route.params.id;
    },
  }
}
</script>

<style scoped>
  #user {
    display: inline-block;
    margin-top: 5rem;
    margin-bottom: 2rem;
    border-radius: 1rem;
    background-color: pink;
    width: 65%;
    height: 125vh;
  }
  .userBackImg {
    width: 100%;
    border-radius: 1rem 1rem 0 0;
    transition: all 0.5s;
    height: 21rem;
  }

  .user-details {
    height: 23rem;
    width: auto;
  }

  .userInfo {
    height: 7rem;
  }
  .userIcon {

    display: inline-block;
    text-align: left;
  }
  .userIcon > img {
    margin-top: -5rem;
    line-height: 10rem;
    width: 10rem;
    height: 10rem;
    border-radius: 2rem;
  }
  .username {
    text-align: left;
    font-size: 20px;
  }

  .nickname {
    text-align: left;
    font-size: 16px;
  }

  .day-mode  #user, .day-mode  #user  {
    color: black;
    background-color: #fff;
  }

  .night-mode #user, .night-mode #user {
    color: #fff;
    background-color: #3f4041;
  }

  .userPosted {
    height: 50vh;
    /*background-color: skyblue !important;*/
    border-radius: 1rem;
    margin-top: 5rem;
  }

  /deep/ .navbar-nav > a{
    display: inline-block !important;
  }

  .user-context {

  }

  .tag-nav {
    width: auto;
    height: 3rem;
  }

  .tag-nav > .dropdown-center {
    text-align: right;
  }


  .article-context {
    height: 35rem;
    overflow-y: scroll;
    overflow-x: hidden;
  }

  .article-list {
    align-items: center;
    justify-content: center;
  }

  .article-info {
    margin: 8px 12px;
    border-radius: 1rem;
    border: none;

    width: 270px;
    height: auto;
    background-color: #ffffff;
  }

  .night-mode .article-list .article-info {
    background-color: rgba(63, 64, 65, 0.81) !important;
  }

  .card-img-top {
    margin-top: 1rem;
  }

  .card-body {
    margin-bottom: 1rem;
  }

  .card-tags {
    margin-bottom: 1rem;
  }

  .card-tags > span {
    margin: 0 0.2rem;
  }

  a {
    text-decoration: none;
    color: #fff;
  }

  a:hover{
    cursor: pointer;
  }

  .author {
    line-height: 24px;
    display: inline-block;
    width: 24px;
    height: 24px;
    border-radius: 12px;
  }

  .posted {
    text-align: left;
    font-size: 12px;
    line-height: 24px;
    padding-right: 0;
  }

  .article-data {
    padding-right: 0;
    font-size: 12px;
    line-height: 24px;
    text-align: left;
    padding-left: 2rem;
  }

  .article-data > span {
    margin-left: 0.5rem;
  }

  /* 垂直滚动条样式 */
  /* 宽度 */
  ::-webkit-scrollbar {
    width: 10px;
    border-radius: 5xp;
  }

  /* 背景 */
  ::-webkit-scrollbar-track {
    background: linear-gradient(to bottom, #f5f5f5, #e8e8e8);
  }


  /* 滑块 */
  ::-webkit-scrollbar-thumb {
    background: linear-gradient(to bottom, #a1a1a1, #6b6b6b);
    border-radius: 5px; /* 圆角 */
  }

  /* 让浏览器的滚动条不显示 */
  body {
    overflow: hidden;
  }

  .btn-changeUserInfo {
    padding-left: 1.5rem;
  }

  .btn-changePwd {
    padding: 2rem;
  }

  .show-more-btn {
    margin-bottom: 8px;
  }

  .day-mode .show-more-btn > span {
    color: #3f4041;
  }

  h5 > a {
    color: #3f4041;
  }

  .avatarO , avatarN {
    display: inline-block;
    width: 12rem;
    height: 12rem;
  }

  .avatarO {
    margin-right: 3rem;
  }

  .avatarN {
    margin-left: 3rem;
  }

  .userIconUpdate {
    display: inline-block;
    width: 12rem;
    height: 12rem;
  }

  .userIconUpdate > img {
    width: 10rem;
    height: 10rem;
    border-radius: 2rem;
  }

  .userIconUpdate > button {
    margin-top: 1rem;;
    margin-bottom: 1.5rem;
  }

  .vr {
    height: 10rem;
  }

</style>