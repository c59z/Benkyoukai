<template>
  <div id="article-list">

    <div v-for="lesson in this.lessonInfo" class="row article diary-div center-block">
      <div class="col-md-3 col-sm-3 col-xs-3">
        <img :src="lesson.cover" alt="image">
      </div>
      <!-- 文章内容 -->
      <div class="col-md-8 col-sm-8 col-xs-4">
        <!-- 标题 -->
        <h4>
          <b>
            <RouterLink :to="`/speak/playlist/${lesson.id}`" class="article-title">
              {{lesson.title}}
            </RouterLink>
          </b>
        </h4>
        <!-- 子标题 -->
        <div class="sub-title">
          <p style="color: white;">
            {{lesson.description}}
          </p>
        </div>
      </div>
    </div>




  </div>
</template>

<script>
import request from "../../js/request.js";
export default {
  name: "LessonInfo",
  data() {
    return {
      lessonInfo:[
      ],
    }
  },
  methods: {
    getLessonInfo(){
      request.get("/kaiwa/info").then(res => {
        this.lessonInfo = res.data
      })
    }
  },
  mounted() {
    this.getLessonInfo()
  },
}
</script>

<style scoped>
/* 文章列表板块 */
#article-list {
  padding-top: 1rem;
  color: #fff;
  text-align: center;
}

#article-list img {
  width: 10rem;
  padding: 1rem;
  border-radius: 1.5rem;
}

/* 一个文章板块 */
.article {
  border-radius: 20px;
  background-color: rgba(125, 125, 125, 0.5);
  box-shadow: 0px 0px 25px 0px rgba(5, 5, 5, 0.6);
  /* height: 300px; */
  /*height: 220px;*/
  margin-bottom: 30px;
}

.article-title {
  margin-top: 1rem;
  color: black;
  text-shadow: 0 0 30px lightblue;
}

.article .article-title a {
  text-decoration: none;
  padding: 5px 15px;
  border-radius: 25px;
  color: #fff;
  font-size: 24px;
  display: inline-block;
  margin: 10px auto;
  background-color: rgba(148, 0, 211, 0.6);
}

.article .article-title a:hover {
  background-color: rgba(148, 0, 211, 0.8);
  box-shadow: 0px 0px 20px 0px rgba(225, 225, 225, 0.6);
}

.article .tags a:hover {
  text-decoration: none;
}

.article .article-content a {
  color: #fff;
  /* text-indent: 40px; */
  display: inline-block;
  font-size: 18px;
  text-decoration: none;
}

.sub-title {
  margin-top: 2rem;
}
</style>