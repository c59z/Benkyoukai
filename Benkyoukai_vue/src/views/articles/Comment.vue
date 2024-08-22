<template>
  <div class="comment-lists">
    <!-- 一级评论 -->
    <div class="comment">
      <a href="#" style="display: inline-block;margin-right: 1rem">
        <img class="posted-info-icon" :src="comment.avatar">
      </a>
      <span style="display: inline-block">{{comment.nickname}}</span>
      <div class="comment-content text-truncate">{{comment.content}}</div>
      <div style="margin: 0" class="row">
        <span class="comment-data col-11">{{comment.createTime}}</span>
        <span @click="openReplyArea" class="reply-btn col-1">{{$t('Reply_tips')}}</span>
        <div v-show="isOpened">
          <textarea v-model="this.replyDto.content" class="form-control my-textarea" id="summaryInput" rows="3"></textarea>
          <span class="comment-data col-11"></span>
          <button @click="addReply" class="btn btn-primary col-1">{{$t('Reply_btn')}}</button>
        </div>

      </div>
      <hr>
      <!-- 回复 -->
      <!-- 渲染子评论 -->
      <div v-if="comment.replies && comment.replies.length > 0" class="reply">
        <Comment v-for="reply in comment.replies"
                 :key="reply.id"
                 :comment="reply"
        >

        </Comment>
      </div>
    </div>
  </div>
</template>

<script>
import {ElMessage} from "element-plus";
import request from "../../js/request.js";
import { events } from '../../js/bus.js' // 事件总线

export default {
  name: "Comment",
  props: {
    comment: {
      type: Object,
      required: true
    }
  },
  data() {
    return{
      replyDto: {
        "articleId":this.$route.params.id,
        "content":"",
        "parentId":this.comment.id
      },
      isOpened: false,
      currentUserId: this.$store.state.userId,
      isSending: false,
    }
  },
  methods: {
    openReplyArea(){
      if(this.currentUserId === null){
        ElMessage({
          message: "您尚未登录，请登录",
          type: 'warning',
          plain: true,
        })
        return;
      }
      this.isOpened = !this.isOpened
    },
    addReply(){
      if(this.currentUserId === null){
        ElMessage({
          message: "您尚未登录，请登录",
          type: 'warning',
          plain: true,
        })
        return;
      }
      if(this.replyDto.content == "" || this.replyDto.content == null){
        ElMessage({
          message: "请输入要回复的内容",
          type: 'warning',
          plain: true,
        })
        return;
      }
      if(this.isSending){
        ElMessage({
          message: "正在发送中,请不要重复提交",
          type: 'warning',
          plain: true,
        })
      }
      this.isSending = true
      request.post("/article/addReply",this.replyDto).then(res => {
        if(res.code === 200){
          console.log("发送成功!")
          // this.$emit('getCommentsForAnArticle');
          events.emit('getComments')
          this.isOpened = false
        }else{
          ElMessage({
            message: res.msg,
            type: 'error',
            plain: true,
          })
        }
      })
      this.isSending = false
    }
  }
}
</script>

<style scoped>

.posted-info-icon {
  display: inline-block;
  width: 3rem;
  height: 3rem;
  border-radius: 3rem;
}

.comment-content {
  margin: 1rem 0;
  display: block;
}

.comment-data {
  display: inline-block;
  padding-right: 0;
  font-size: 12px;
  line-height: 24px;
  text-align: left;
}

.reply-btn {
  text-align: right;
  cursor: pointer;
}

.reply {
  margin-left: 1rem;
}

.my-textarea {
  height: 2rem;
}

.day-mode .comment {
  color: #1a1a1a;
}

.btn-primary {
  margin-top: 0.5rem;
}

</style>