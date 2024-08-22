<template>
  <div class="search-bar-div">

    <div class="search-img-bg">
      <div class="welcome">
        <h1>{{ $t("welcome") }}</h1>

        <h4> <span style="color: #5fdbff;text-shadow: none"> {{ this.articleCount }} </span> {{ $t("articlesNumber") }}</h4>
      </div>
      <div class="search-div">
        <input v-model="this.searchText" type="text" class="form-control search-bar col-10" placeholder="...">
<!--        <button class="search-btn-div col-2">{{$t("search_btn")}}</button>-->
        <div v-if="searchText" class="button-container">
          <ul>
<!--            <li @click="this.searchRelatedWords" class="search-li-btn">搜索相关单词: {{this.searchText}}</li>-->
            <li @click="this.searchRelatedArticles"class="search-li-btn">{{$t('search_li_btn')}} {{this.searchText}}</li>
          </ul>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import {mapState} from "vuex";
import request from "../../js/request.js";

export default {
  name: "MainSearchBar",
  data() {
    return {
      searchText: '',
      articleCount: 0,
    }
  },
  methods: {
    getArticleCount(){
      request.get("/article/Count").then(res => {
        this.articleCount = res.data
      })
    },
    searchRelatedWords() {
      console.log('搜索相关单词:', this.searchText);
      this.$router.push("/searchWord?text="+this.searchText)
    },
    searchRelatedArticles() {
      const text = this.searchText
      console.log('搜索相关文章:', text);
      this.$router.push("/search?kw="+text)
      this.searchText = ""
    },
  },
  computed: {
    ...mapState(['mode']),
  },
  mounted() {
    this.getArticleCount()
  }
}
</script>

<style scoped>
.search-bar-div {
  position: relative;
}

.search-img-bg div {
  display: inline-block;
  color: #1a1a1a;
}

.search-img-bg {
  transition: all 0.5s;
  background-image: url('https://s2.loli.net/2024/06/13/Klc38YFo915ROjh.jpg');
}

.night-mode .search-img-bg {
  transition: all 0.5s;
  background-image: url('https://s2.loli.net/2024/06/13/oHxeIh1P9jmNVrz.jpg');
}

.welcome {
  text-shadow: 2px 2px 2px #000;
  padding-top: 10rem;
  width: 100%;
  height: 8rem;
}

h1 {
  margin-bottom: 1rem;
  color: white;
}

h4 {
  color: white;
}

.search-img-bg {
  display: inline-block;
  height: 42rem;
  width: 100%; /* 宽度适应屏幕 */

  background-size: cover; /* 拉伸图片以覆盖整个搜索栏 */
  background-position: center; /* 图片居中对齐 */
  background-repeat: no-repeat; /* 防止图片重复 */
}

.search-div {
  margin-top: 7rem;
  display: inline-block;
  width: 55rem;
  height: 3rem;
  align-items: center;
  justify-content: center;
  border-radius: 5rem;
}

.search-bar {
  width: 45rem;
  display: inline-block !important;
  line-height: 3rem;
  height: 100%;
  background-color: rgba(255,255,255,0.8);
  border-radius: 5rem;
}

.button-container {
  display: inline-block;
  background-color: rgba(255,255,255,0.3);
  width: 45rem;
  border-radius: 1rem;
  height: 3rem;
}

ul {
  padding: 0;
}

.search-li-btn {
  padding: 0;
  text-align: left;
  margin-top: 0.8rem;
  list-style: none;
  color: snow;
  font-size: 20px;
  line-height: 20px;
}

.search-li-btn:hover {
  background-color: rgba(255,255,255,0.6);
  cursor: pointer;
}



/*.search-btn-div {*/
/*  border: none;*/
/*  display: inline-block;*/
/*  height: 100%;*/
/*  background-color: rgba(252, 60, 45,0.9);*/
/*  border-radius: 0 5rem 5rem 0;*/
/*}*/

</style>