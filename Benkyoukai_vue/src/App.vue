<template>
  <div :class="mode" id="Main">
    <Header v-show="this.showHeaderAndFooter"></Header>
    <router-view></router-view>
    <Footer v-show="this.showHeaderAndFooter"></Footer>
  </div>
</template>

<script>
import './css/style.css';
import Header from "./views/base/Header.vue";
import {mapState} from "vuex";
import Footer from "./views/base/Footer.vue";
import { ElMessage } from 'element-plus'

export default {
  components: {Footer, Header, ElMessage },
  data() {
    return {
      // mode: this.$store.state.mode, //  'day-mode'
    };
  },
  methods: {


  },
  mounted() {
    this.$i18n.locale = this.$store.state.locale;
  },
  computed: {
    // mode() {
    //   return this.$store.state.mode;
    // },
    ...mapState(['mode']),
    ...mapState(['locale']),
    showHeaderAndFooter(){
      return this.$route.path !== '/login' &&  this.$route.path !== '/register';
    },
  },

};
</script>

<style scoped>
  * {
    padding: 0;
    margin: 0 auto;
    text-align: center;
    color: white;

    transition: background-color 0.5s, color 0.5s,opacity 0.5s;
 }


</style>
