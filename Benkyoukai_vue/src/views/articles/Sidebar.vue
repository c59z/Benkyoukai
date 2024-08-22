<template>
  <div id="Sidebar">
    <div class="user-details">
      <img class="userBackImg" src="https://s2.loli.net/2024/06/18/JAzLy5R2oFMNcTd.jpg" alt="bg">
      <div class="userInfo">
        <div class="userIcon">
          <img :src="currentUserInfo.avatar" alt="">
        </div>
        <div style="display: inline-block;text-align: center;margin-bottom: 1rem">
          <div class="username">{{currentUserInfo.username}}</div>
          <span class="badge rounded-pill text-bg-secondary">{{currentUserInfo.email}}</span>
        </div>

        <div class="button-group">
          <button @click="goToUserCenter" type="button" class="btn btn-primary btn-sm">{{$t('aside_label_userCenter')}}</button>
          <button @click="goToWrite" type="button" class="btn btn-secondary btn-sm">{{$t('aside_label_postArticle')}}</button>
        </div>

      </div>
    </div>

    <!-- 搜索栏 -->
    <aside id="search">

      <form class="form-inline clearfix" method="get" id="searchform" action="#">
        <input v-model="this.searchText" class="form-control" type="text" name="s" id="s" placeholder="search">
        <button @click="goToSearch" type="button" class="btn btn-danger btn-small" name="submit">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
          </svg>
        </button>
      </form>

    </aside>
  </div>
</template>

<script>
export default {
  name: "Sidebar",
  props: {
    currentUserInfo:{
      type: Object,
      required: true
    }
  },
  data() {
    return {
      searchText: "",
    }
  },
  methods: {
    goToUserCenter(){
      this.$router.push(`/user/${this.currentUserInfo.id}`)
    },
    goToWrite(){
      this.$router.push('/write')
    },
    goToSearch(){
      if(this.searchText === "" || this.searchText === null){
        return
      }
      const text = this.searchText
      console.log('搜索相关文章:', text);
      this.$router.push("/search?kw="+text)
      this.searchText = ""
    }
  },
}
</script>

<style scoped>

  #Sidebar{
    display: inline-block;
    margin-top: 3rem;
  }

  .night-mode .user-details {
    background-color: #3f4041;
  }
  .night-mode .username {
    color: #fff;
  }

  .username {
    color: #6c757d;
  }

 .user-details {
   background-color: #fff;
   border-radius: 1rem;
 }

 .userBackImg {
   border-radius: 1rem 1rem 0 0;
   width: 100%;
 }

 .userInfo {
   margin-top: -2.5rem;
 }

 .userIcon {

 }

 .userIcon > img {
   border-radius: 5rem;
   width: 5rem;
   height: 5rem;
 }

 .button-group {
   margin-bottom: 1rem;
 }

 .btn-sm {
   margin: 1rem 1rem 2rem;
 }

  #search {
    border-radius: 10px;
    /*width: 300px;*/
    height: 50px;
    background-color: rgba(22, 22, 22,0.4);
    margin-bottom: 40px;
    box-shadow: 0px 0px 15px 0px rgba(150, 150, 150,0.4)
  }

  #searchform {
    text-align:center;
    line-height: 50px;
  }

  .btn-danger {
    display: inline-block;
    width: 20%;
    color: #fff;
    background-color: rgb(255,105,180);
    border-color: rgb(255,105,180);
  }

  .form-inline .form-control {
    width: 70%;
    display: inline-block;
    color: #fff;
    background-color: rgba(255, 255, 255,0.8);
    border-color: rgba(255,105,180, 0.6);
    margin-right: 5px;
    /*width: 280px;*/
    vertical-align: middle;
  }

  .night-mode .form-inline .form-control {
    background-color: rgba(255, 255, 255,0.5);
  }

</style>