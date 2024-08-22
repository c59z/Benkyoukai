<template>
  <div>
    <h5 style="text-align: left;"><span class="badge rounded-pill text-bg-pink">{{ $t("article_list") }}</span></h5>

    <div v-if="!searchText" class="article-list row">
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

    <div v-else class="article-list row">
      <div class="card article-info col-3" v-for="article in processedSelectedArticles">
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

    <div v-show="!searchText" class="show-more-btn">
      <button class="btn btn-primary" v-if="!allLoaded" @click="loadMoreArticles">{{$t('see_more')}}</button>
      <span v-else>{{ $t('no_more') }}</span>
    </div>


  </div>
</template>

<script>
import request from "../../js/request.js";
import {ElMessage} from "element-plus";

export default {
  name: "AllArticles",

  data() {
    return {
      currentPage: 1,
      isLoading: false,
      allLoaded: false,
      articles: [],
      selectedArticles: [],
      searchText: "",
    }
  },
  methods: {
    getArticles(page) {
      if (this.isLoading || this.allLoaded) return;
      this.isLoading = true;
      request.get(`/article/getArticles?page=${page}`).then(res => {
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
      this.getArticles(this.currentPage);
    },
    checkSearchText() {
      // 获取查询参数 text 的值
      this.searchText = this.$route.query.kw || null;
      // 发送请求获取文章列表连接
      if(this.searchText !== null){
        request.get("/article/select?kw="+this.searchText).then(res => {
            if(res.code === 200){
              this.selectedArticles = res.data
            }else if(res.code === 404){
              ElMessage({
                message: res.msg,
                type: 'error',
                plain: true,
              })
              this.$router.push("/")
            }else{
              ElMessage({
                message: res.msg,
                type: 'error',
                plain: true,
              })
              this.$router.push("/")
            }
        })
      }
    }
  },
  mounted() {
    this.checkSearchText();
    this.getArticles(1)
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
    processedSelectedArticles() {
      if(this.selectedArticles.length !== 0)
      return this.selectedArticles.map(article => {
        return {
          ...article,
          categoryTagsArray: article.categoryTags ? article.categoryTags.split(",") : []
        };
      });
    },
  },
  watch: {
    // 监听路由变化
    '$route'(to, from) {
      this.checkSearchText();
    }
  },

}
</script>

<style scoped>
#random-recommend {
  /*background-color: pink;*/
}

.text-bg-pink {
  margin-left: 2.5rem;
  background-color: deeppink;
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

.card-img-top {
  margin-top: 1rem;
}

.card-body {
  margin-bottom: 1rem;
}

h5 > a {
  color: #3f4041;
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

a:hover {
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

.show-more-btn {
  margin-bottom: 8px;
}

.day-mode .show-more-btn > span {
  color: #3f4041;
}

</style>