<template>
  <div id="DictIndex">

  <div class="row dict-div">
    <div class="col-5 search-div">
      <div>
        <input v-model="this.searchText" type="text" class="form-control search-bar col-10" placeholder="...">
        <!--        <button class="search-btn-div col-2">{{$t("search_btn")}}</button>-->
        <div v-if="searchText" class="button-container">
          <ul>
            <li @click="this.searchRelatedWords" class="search-li-btn"> Text </li>
            <li @click="this.searchRelatedWords" class="search-li-btn"> Text </li>
            <li @click="this.searchRelatedWords" class="search-li-btn"> Text </li>
            <li @click="this.searchRelatedWords" class="search-li-btn"> Text </li>
            <li @click="this.searchRelatedWords" class="search-li-btn"> Text </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="d-flex col-1">
      <div class="vr"></div>
    </div>
    <div class="col-5">
      <div class="dict-detail">
        <h1 class="word">{{ word }}</h1>
        <p class="reading">{{ reading }}</p>
        <div class="tags">
          <span class="badge bg-secondary" v-for="tag in tags" :key="tag">{{ tag }}</span>
        </div>
        <div class="definitions">
          <div class="definition" v-for="definition in definitions" :key="definition.id">
            <h2 class="definition-header">{{ definition.header }}</h2>
            <p class="definition-text">{{ definition.text }}</p>
          </div>
        </div>
      </div>

    </div>
  </div>



  </div>
</template>

<script>
export default {
  name: "DictIndex",
  data() {
    return {
      searchText: '',
      word: '見合い',
      reading: 'みあい | miai',
      tags: ['N2-N3'],
      definitions: [
        { id: 1, header: '相抵、平衡、相称', text: '対応すること。' },
        { id: 2, header: '給の見合い。', text: '供求平衡。' },
        { id: 3, header: '相来、相看', text: '互いに結婚相手としてふさわしいかをみるために人を介して会うこと。' }
      ],

    }
  },
  methods: {
    searchRelatedWords() {
      // 搜索相关单词的逻辑
      console.log('搜索相关单词:', this.searchText);
    },
  },
}
</script>

<style scoped>
  #DictIndex {
    margin-top: 56px;
    height: 100vh;
    background-color: #f5f6f7;
    color: #3f4041;
  }

  .night-mode #DictIndex {
    background-color: #292a2d;
  }

  .night-mode .dict-detail {
    background-color: #3f4041;
    color: snow;
  }

  .d-flex {
    margin: 7rem auto;
    color: #3f4041;
  }

  .vr {
    text-align: center;
  }

  .dict-div {
    margin: 0 10%;
  }

  .dict-detail {
    padding: 2rem;
    border-radius: 1rem;
    margin: 7rem auto;
    /*height: 45rem;*/
    background-color: #fff;
  }


  .search-div {
    margin-top: 7rem;
    display: inline-block;
    width: 43rem;
    height: 30rem;
    align-items: center;
    justify-content: center;
    border-radius: 1rem;
  }

  .search-bar {
    font-size: 18px;
    width: 30rem;
    display: inline-block !important;
    height: 100%;
    background-color: rgba(255,255,255,0.8);
    border-radius: 5rem;
  }

  .button-container {
    display: inline-block;
    background-color: rgba(255,255,255,0.3);
    width: 30rem;
    border-radius: 1rem;
    height: auto;
  }

  ul {
    padding: 0;
  }

  .search-li-btn {
    padding: 1rem;
    text-align: left;
    margin-top: 0.8rem;
    list-style: none;
    font-size: 20px;
    line-height: 20px;
  }

  .search-li-btn:hover {
    background-color: rgba(255,255,255,0.6);
    cursor: pointer;
  }

  .dict-detail {
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
  .word {
    font-size: 2em;
    margin-bottom: 10px;
  }
  .reading {
    font-size: 1.5em;
    color: gray;
    margin-bottom: 10px;
  }
  .tags .badge {
    margin-right: 5px;
  }
  .definitions .definition {
    margin-top: 20px;
  }
  .definition-header {
    font-size: 1.2em;
    font-weight: bold;
  }
  .definition-text {
    font-size: 1em;
    margin-top: 5px;
  }

</style>