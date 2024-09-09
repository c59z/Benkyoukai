<template>
  <div id="index">
    <MainSearchBar></MainSearchBar>
    <div id="articlesContent">
      <RandomRecommend v-show="!this.searchText"></RandomRecommend>
      <div v-show="this.searchText" class="alert alert-secondary" role="alert">
        搜索到相关文章:
      </div>
      <AllArticles></AllArticles>
    </div>
  </div>
</template>

<script>
import MainSearchBar from "./main/MainSearchBar.vue";
import RandomRecommend from "./main/RandomRecommend.vue";
import AllArticles from "./main/AllArticles.vue";
import request from "../js/request.js";

export default {
  name: 'Index',
  components: {AllArticles, RandomRecommend, MainSearchBar},
  data() {
    return {
      searchText: '',
    }
  },
  watch: {
    // 监听路由变化
    '$route'(to, from) {
      this.checkSearchText();
    }
  },
  mounted() {
    this.checkSearchText();
  },
  methods: {
    checkSearchText() {
      // 获取查询参数 text 的值
      this.searchText = this.$route.query.kw || null;
      // 发送请求获取文章列表连接(在AllArticles组件)
    }
  }
};
</script>

<style>

  #index {
    height: auto;
  }

  #articlesContent {
    margin: 0 auto;
    width: 65%;
  }

  .alert {
    width: 93.5%;
    margin: 2rem auto 1rem;
  }

</style>
