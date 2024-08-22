<template>
  <div>
    <div id="article-detail">

      <div class="row g-0 text-center">
        <div class="article col-10">
          <h1>{{article.title}}</h1>
          <!-- 用户信息 -->
          <div class="posted-info">
            <a href="#" style="display: inline-block;margin-right: 1rem">
              <img class="posted-info-icon" :src="article.avatar">
            </a>
            <span style="display: inline-block">{{article.nickname}}</span>
            <div style="margin-right: 0;" class="row">
              <div class="col-8"></div>
              <div class="posted col-2">
                {{article.createTime}}
              </div>
              <div class="article-data col-2">
              <span>
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye"
                           viewBox="0 0 16 16">
  <path
      d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
  <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
</svg>
                {{article.viewCount}}
              </span>

                <span>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart" viewBox="0 0 16 16">
  <path d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>
</svg>
                {{article.likeCount}}
              </span>
              </div>
            </div>
          </div>

          <hr style="color:#999;">

          <!-- 背景图 -->
          <div>
            <img class="article-cover" :src="article.thumbnail" alt="">
          </div>

          <hr style="color:#999;">

          <!-- 正文 -->
          <div v-html="article.content" class="main-body">

          </div>

          <hr style="color:#999;">

          <!-- 操作区 -->
          <div class="action-bar">
            <div>
              <span v-for="tag in categoryTagsArray" class="badge text-bg-secondary">
                <a href="#"># {{ tag }}</a>
              </span>
<!--              <span class="badge text-bg-secondary"> <a href="#"># 日语</a> </span>-->
<!--              <span class="badge text-bg-secondary"> <a href="#"># PDF</a> </span>-->
            </div>

            <div style="color: gray;text-align: center">{{$t('article_tips_like')}}</div>

            <div style="text-align: center;margin-top: 1rem">
              <div @click="likeArticle" class="option-btn">
                <div v-if="isLiked === 0">
                  <svg xmlns="http://www.w3.org/2000/svg" width="60" height="60" fill="currentColor" class="bi bi-hand-thumbs-up" viewBox="0 0 16 16">
                    <path d="M8.864.046C7.908-.193 7.02.53 6.956 1.466c-.072 1.051-.23 2.016-.428 2.59-.125.36-.479 1.013-1.04 1.639-.557.623-1.282 1.178-2.131 1.41C2.685 7.288 2 7.87 2 8.72v4.001c0 .845.682 1.464 1.448 1.545 1.07.114 1.564.415 2.068.723l.048.03c.272.165.578.348.97.484.397.136.861.217 1.466.217h3.5c.937 0 1.599-.477 1.934-1.064a1.86 1.86 0 0 0 .254-.912c0-.152-.023-.312-.077-.464.201-.263.38-.578.488-.901.11-.33.172-.762.004-1.149.069-.13.12-.269.159-.403.077-.27.113-.568.113-.857 0-.288-.036-.585-.113-.856a2.144 2.144 0 0 0-.138-.362 1.9 1.9 0 0 0 .234-1.734c-.206-.592-.682-1.1-1.2-1.272-.847-.282-1.803-.276-2.516-.211a9.84 9.84 0 0 0-.443.05 9.365 9.365 0 0 0-.062-4.509A1.38 1.38 0 0 0 9.125.111L8.864.046zM11.5 14.721H8c-.51 0-.863-.069-1.14-.164-.281-.097-.506-.228-.776-.393l-.04-.024c-.555-.339-1.198-.731-2.49-.868-.333-.036-.554-.29-.554-.55V8.72c0-.254.226-.543.62-.65 1.095-.3 1.977-.996 2.614-1.708.635-.71 1.064-1.475 1.238-1.978.243-.7.407-1.768.482-2.85.025-.362.36-.594.667-.518l.262.066c.16.04.258.143.288.255a8.34 8.34 0 0 1-.145 4.725.5.5 0 0 0 .595.644l.003-.001.014-.003.058-.014a8.908 8.908 0 0 1 1.036-.157c.663-.06 1.457-.054 2.11.164.175.058.45.3.57.65.107.308.087.67-.266 1.022l-.353.353.353.354c.043.043.105.141.154.315.048.167.075.37.075.581 0 .212-.027.414-.075.582-.05.174-.111.272-.154.315l-.353.353.353.354c.047.047.109.177.005.488a2.224 2.224 0 0 1-.505.805l-.353.353.353.354c.006.005.041.05.041.17a.866.866 0 0 1-.121.416c-.165.288-.503.56-1.066.56z"/>
                  </svg>
                </div>
                <div v-else-if="isLiked === 1">
                  <svg xmlns="http://www.w3.org/2000/svg" width="60" height="60" fill="currentColor" class="bi bi-hand-thumbs-up-fill" viewBox="0 0 16 16">
                    <path d="M6.956 1.745C7.021.81 7.908.087 8.864.325l.261.066c.463.116.874.456 1.012.965.22.816.533 2.511.062 4.51a9.84 9.84 0 0 1 .443-.051c.713-.065 1.669-.072 2.516.21.518.173.994.681 1.2 1.273.184.532.16 1.162-.234 1.733.058.119.103.242.138.363.077.27.113.567.113.856 0 .289-.036.586-.113.856-.039.135-.09.273-.16.404.169.387.107.819-.003 1.148a3.163 3.163 0 0 1-.488.901c.054.152.076.312.076.465 0 .305-.089.625-.253.912C13.1 15.522 12.437 16 11.5 16H8c-.605 0-1.07-.081-1.466-.218a4.82 4.82 0 0 1-.97-.484l-.048-.03c-.504-.307-.999-.609-2.068-.722C2.682 14.464 2 13.846 2 13V9c0-.85.685-1.432 1.357-1.615.849-.232 1.574-.787 2.132-1.41.56-.627.914-1.28 1.039-1.639.199-.575.356-1.539.428-2.59z"/>
                  </svg>
                </div>
                <div style="text-align: center;margin-top: 5px;">{{$t('article_btn_like')}}</div>
              </div>

              <div @click="favoriteArticle" class="option-btn">
                <div v-if="isFavorite === 0">
                  <svg xmlns="http://www.w3.org/2000/svg" width="60" height="60" fill="currentColor" class="bi bi-star" viewBox="0 0 16 16">
                    <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                  </svg>
                </div>
                <div v-else-if="isFavorite === 1">
                  <svg xmlns="http://www.w3.org/2000/svg" width="60" height="60" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                    <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                  </svg>
                </div>


                <div style="text-align: center;margin-top: 5px;">{{$t('article_btn_star')}}</div>
              </div>

              <div v-show="this.currentUserId == this.article.createBy" @click="updateArticle" class="option-btn">
                <svg xmlns="http://www.w3.org/2000/svg" width="60" height="60" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                  <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                  <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                </svg>
                <div style="text-align: center;margin-top: 5px;">{{$t('article_btn_edit')}}</div>
              </div>

            </div>

          </div>
        </div>
        <sidebar :currentUserInfo="this.currentUserInfo" style="width: 25%" class="col-2"></sidebar>
        <!-- 评论区 -->
        <div id="Comments" class="col-10">
          <div class="post-article">
            <div class="userInfoDiv" v-if="currentUserId">
              <a href="#" style="display: inline-block;margin-right: 1rem">
                <img class="posted-info-icon" :src="currentUserInfo.avatar">
              </a>
              <span style="display: inline-block">{{currentUserInfo.nickname}}</span>
            </div>
            <div class="userInfoDiv" v-else>
              <a href="/login" style="display: inline-block">{{$t('needLogin_tips')}}</a>
            </div>

            <div class="mb-3">
              <label for="exampleFormControlTextarea1" class="form-label">{{$t('article_btn_postCommit')}}</label>
              <textarea v-model="this.postCommentDto.content" class="form-control" id="summaryInput" rows="3"></textarea>
            </div>
            <div style="padding: 0;margin: 0" class="row">
              <div class="col-10"> </div>
              <button @click="postComment" class="btn btn-light col-2 post-btn">{{$t('article_btn_postCommit')}}</button>
            </div>
          </div>

          <hr>

          <Comment v-for="comment in comments"
                   :key="comment.id"
                   :comment="comment"
          ></Comment>


        </div>
      </div>
    </div>


  </div>

</template>

<script>
import Sidebar from "./Sidebar.vue";
import request from "../../js/request.js";
import MarkdownIt from 'markdown-it';
import {ElMessage} from "element-plus";
import store from "../../js/store.js";
import router from "../../js/router.js";
import Comment from "./Comment.vue";
import { events } from '../../js/bus.js' // 事件总线

export default {
  name: "ArticlesDetail",
  components: {Comment, Sidebar},
  // props: ['id'],
  data() {
    return {
      markdownIt: new MarkdownIt(),
      article: {},
      comments: {},
      currentUserId: this.$store.state.userId,
      isLiked: 0,
      isFavorite: 0,
      currentUserInfo: {},
      postCommentDto: {},
      posting: false,
    }
  },
  methods: {
    getArticleDetail(){
      const id = this.$route.params.id;
      request.get(`/article/getArticleDetail/${id}`).then(res => {
        if(res.code === 400) {
          ElMessage({
            message: "文章不存在",
            type: 'error',
            plain: true,
          })
          this.$router.push('/')
          return;
        }
        this.article = res.data
        if(this.article.content){
          this.article.content = this.markdownIt.render(this.article.content)
        }
      });
    },
    articleViewed(){
      const id = this.$route.params.id;
      request.put(`/article/${id}/viewed`).then(res => {
        // console.log(res.code)
      });
    },
    checkIsLiked(){
      if(this.currentUserId !== null){
        // 用户已经登录
        const id = this.$route.params.id;
        request.get(`/article/isLike/${id}`).then(res => {
          if(res.code === 200){
            this.isLiked = res.data
            console.log("当前点赞状态:"+this.isLiked)
          }
        })
      }
    },
    checkIsFavorite(){
      if(this.currentUserId !== null){
        // 用户已经登录
        const id = this.$route.params.id;
        request.get(`/favorites/isFavorite/${id}`).then(res => {
          if(res.code === 200){
            this.isFavorite = res.data
            console.log("当前收藏状态:"+this.isFavorite)
          }
        })
      }
    },
    likeArticle(){
      if(this.currentUserId === null){
        ElMessage({
          message: "您尚未登录",
          type: 'warning',
          plain: true,
        })
        router.push('/login')
      }else{
        const id = this.$route.params.id;
        request.put(`/article/${id}/like`).then(res => {
          if(res.code === 200){
            this.isLiked = res.data
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
    favoriteArticle(){
      if(this.currentUserId === null){
        ElMessage({
          message: "您尚未登录",
          type: 'warning',
          plain: true,
        })
        router.push('/login')
      }else{
        const id = this.$route.params.id;
        if(this.isFavorite === 0){
          // 添加收藏
          request.post(`/favorites/add?articleId=${id}`).then(res => {
            if(res.code === 200){
              this.isFavorite = res.data
            }else{
              ElMessage({
                message: res.msg,
                type: 'error',
                plain: true,
              })
            }
          });
        }else{
          // 取消收藏
          request.post(`/favorites/remove?articleId=${id}`).then(res => {
            if(res.code === 200){
              this.isFavorite = res.data
            }else{
              ElMessage({
                message: res.msg,
                type: 'error',
                plain: true,
              })
            }
          });
        }
        this.checkIsFavorite()
      }
    },
    updateArticle(){
      if(this.currentUserId == this.article.createBy){
        this.$router.push(`/edit/${this.article.id}`)
      }
    },
    getUserInfo(){
      if(this.currentUserId != null){
        request.get(`/account/getUserInfo/${this.currentUserId}`).then(res => {
          if(res.code === 200){
            this.currentUserInfo = res.data
          }else{
            ElMessage({
              message: res.msg,
              type: 'error',
              plain: true,
            })
            this.$router.push('/')
          }
        })
      }
    },
    getCommentsForAnArticle(){
      console.log("我被调用了")
      const id = this.$route.params.id;
      request.get(`/article/getArticleDetail/${id}/Comments`).then(res => {
        if(res.code === 200){
          this.comments = res.data
        }else{
          ElMessage({
            message: res.msg,
            type: 'error',
            plain: true,
          })
        }
      })
    },
    postComment(){
      if(this.currentUserInfo === null){
        ElMessage({
          message: "您尚未登录,请登录",
          type: 'error',
          plain: true,
        })
        this.$router.push("/login")
      }

      if(this.posting === true){
        ElMessage({
          message: "正在发表评论中,请稍等..",
          type: 'warning',
          plain: true,
        })
        return
      }

      this.posting = true

      const articleId = this.$route.params.id;
      this.postCommentDto.articleId = articleId

      request.post(`/article/addComment/`,this.postCommentDto).then(res => {
        if(res.code === 200){
          ElMessage({
            message: "发布成功!",
            type: 'success',
            plain: true,
          })
          this.postCommentDto.content = ""
          this.getCommentsForAnArticle()
        }else{
          ElMessage({
            message: res.msg,
            type: 'error',
            plain: true,
          })
        }
      })

      this.posting = false

    }
  },
  mounted() {
    this.getArticleDetail()
    this.articleViewed()
    this.checkIsLiked()
    this.checkIsFavorite()
    this.getUserInfo()
    this.getCommentsForAnArticle()
    // 监听事件
    events.on('getComments',()=>{
      this.getCommentsForAnArticle()
    })
  },
  computed: {
    categoryTagsArray() {
      return this.article.categoryTags ? this.article.categoryTags.split(",") : [];
    },
  }
}
</script>

<style scoped>
a {
  text-decoration: none;
  color: #fff;
}
.badge {
  margin: 0 0.5rem;
}

 #article-detail {
   margin: 56px;
 }

 #Comments {
   background-color: #fff;
   margin-top: 3rem;
   width: 70%;
   height: auto;
   text-align: left;
   padding: 1rem 2.5rem;
   border-radius: 2rem;
   display: inline-block;
 }



 .article {
   margin-top: 3rem;
   background-color: #fff;
   border-radius: 2rem;
   display: inline-block;
   width: 70%;
   margin-right: 3rem;
   height: auto;
   text-align: left;
   padding: 1rem 2.5rem;
 }

 .night-mode .article , .night-mode #Comments{
   background-color: #3f4041;
 }

 /deep/ .row {
   margin: 0 10%;
 }

 h1 {
   font-size: 24px;
   font-weight: bold;
 }

 .posted-info {
   height: 5rem;
   margin-top: 1rem;
 }

 .posted, span {
   color: #3f4041;
 }

 .night-mode span, .night-mode .posted ,.night-mode .main-body {
    color: white;
 }

 .posted-info-icon {
   display: inline-block;
   width: 3rem;
   height: 3rem;
   border-radius: 3rem;
 }

 .posted {
   text-align: right;
   font-size: 14px;
   padding-right: 0;
 }

 .article-data {
   padding-right: 0;
   font-size: 14px;
   text-align: right;
   /* padding-left: 1rem; */
 }

 .article-data > span {
   margin-left: 0.5rem;
 }

 .article-cover {
   margin-top: 1rem;
   /*background-color: green;*/
   /*max-height: 12rem;*/
   max-width: 100%;
   border-radius: 1rem;
 }

 .main-body {
   margin: 1rem auto;
   /*width: auto;*/
   height: auto;

   color: #3f4041
 }

 .option-btn {
   display: inline-block;
   margin: 0 1rem;
   color: #3f4041;
 }

 .night-mode .option-btn {
   color: #f5f6f7;
 }

 .option-btn:hover {
   cursor: pointer;
 }

 .main-body * {
   max-width: 100%;
 }

 .post-article {

 }

 .userInfoDiv {
   margin: 1rem 0
 }

 .post-btn {

 }

.day-mode .post-article {
  color: #1a1a1a;
}

</style>