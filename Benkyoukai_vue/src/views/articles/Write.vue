<template>
  <div id="write-div">
  <h1 v-if="this.currentUserId == this.article.createBy">{{$t('edit_h1')}}</h1>
  <h1 v-else>{{$t('write_h1')}}</h1>


    <div class="editor">
      <div class="mb-3">
        <label for="exampleFormControlInput1" class="form-label">{{$t('write_title')}}</label>
        <input v-model="this.article.title" type="email" class="form-control" id="titleInput" placeholder="请输入标题">
      </div>
      <div class="mb-3">
        <label for="exampleFormControlTextarea1" class="form-label">{{$t('write_subtitle')}}</label>
        <textarea v-model="this.article.summary" class="form-control" id="summaryInput" rows="3"></textarea>
      </div>
      <!-- 标签 -->
      <div class="mb-3">
        <label for="exampleFormControlInput1" class="form-label">{{$t('write_tags')}}</label>
        <input v-model="this.article.categoryTags" type="text" class="form-control" id="titleInput">
      </div>
      <!-- 文章内容 -->
      <label for="exampleFormControlInput1" class="form-label">{{$t('write_content')}}</label>
      <mavon-editor
          v-model="this.article.content"
          ref="md"
          @imgAdd="uploadImage"
          class="my-editor"
      >

      </mavon-editor>
    </div>

    <div class="option-div">
      <button v-if="currentUserId == this.article.createBy" @click="updateArticle" type="button" class="btn btn-success">{{$t('write_btn_repost')}}</button>
      <button v-else @click="postArticle" type="button" class="btn btn-success">{{$t('write_btn_post')}}</button>
      <button @click="returnIndex" type="button" class="btn btn-primary">{{$t('write_btn_return')}}</button>
    </div>

  </div>
</template>

<script>
import mavonEditor from "mavon-editor";
import request from "../../js/request.js";
import {ElMessage} from "element-plus";

export default {
  name: "Write",
  data(){
    return {
      article: {},
      articleIdEdit: 0,
      currentUserId: this.$store.state.userId,
    }
  },
  methods: {
    // 上传图片
    uploadImage(pos, file){
      // console.log(file)
      let formdata = new FormData();
      formdata.append("file", file);
      request.post("/article/upload",formdata,{
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(res => {
        if(res.code === 200){
          this.$refs.md.$img2Url(pos, res.data);
          this.updateThumbnail();
        }else{
          ElMessage({
            message: res.msg,
            type: 'error',
            plain: true,
          })
        }
      })
    },
    updateArticle(){
      if(this.article.title === null || this.article.content === null ||
          this.article.categoryTags === null
      ){
        ElMessage({
          message: "文章的标题，正文,以及标签部分不能为空",
          type: 'warning',
          plain: true,
        })
        return
      }
      request.post("/article/updateArticle",this.article).then(res => {
        if(res.code === 200){
          ElMessage({
            message: "发布成功！",
            type: 'success',
            plain: true,
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
    },
    updateThumbnail(){
      // 获取第一个图片url,读取markdown格式的图片链接
      const imgMatch = this.article.content.match(/!\[.*?\]\((.*?)\)/);
      if (imgMatch) {
        this.article.thumbnail = imgMatch[1]; // 提取第1个图片的 URL，这里的1代表第二个分组
        console.log(this.article.thumbnail)
      } else {
        this.article.thumbnail = '';
      }
    },
    postArticle(){
      if(this.article.title === null || this.article.content === null ||
        this.article.categoryTags === null
      ){
        ElMessage({
          message: "文章的标题，正文,以及标签部分不能为空",
          type: 'warning',
          plain: true,
        })
        return
      }
      request.post("/article/postArticle",this.article).then(res => {
        if(res.code === 200){
          ElMessage({
            message: "发布成功！",
            type: 'success',
            plain: true,
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
    },
    returnIndex(){
      this.$router.push('/')
    },
    checkUrl(){
      if(this.$route.params.id){
        this.articleIdEdit = this.$route.params.id;
        this.getArticleDetail()
      }
    },
    getArticleDetail(){
      const id = this.articleIdEdit;
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

        // console.log(res.data.createBy)
        // console.log(this.currentUserId)
        // console.log(this.currentUserId === res.data.createBy)

        if(res.data.createBy == this.currentUserId){
          this.article = res.data
          if(this.article.content){
            this.article.content = this.markdownIt.render(this.article.content)
          }
        }else{
          ElMessage({
            message: "权限不足",
            type: 'error',
            plain: true,
          })
          this.$router.push('/')
        }
      });
    },
    checkIsLogin(){
      console.log(this.currentUserId)
      if(this.currentUserId === null){
        ElMessage({
          message: "请先登录",
          type: 'warning',
          plain: true,
        })
        this.$router.push('/login')
      }
    }
  },
  watch: {
    // 文章内容发生变化时更新
    'article.content': function() {
      this.updateThumbnail();
    }
  },
  mounted() {
    this.checkUrl()
    this.checkIsLogin();
  }
}
</script>

<style scoped>
#write-div {
  width: 65%;
  margin: 56px auto 0;
  background-color: rgb(255, 255, 255);
  height: 100vh;
  color: #1a1a1a;
}

.option-div > button {
  margin: 0.5rem 2rem;
}

.my-editor {
  height: 400px;
  overflow-y: scroll;
}

</style>