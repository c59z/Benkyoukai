<template>
  <div id="SpeakDiv" class="container">

    <div id="PlayList">
      <nav aria-label="breadcrumb" class="path-nav">
        <ol class="breadcrumb"
            style="/*noinspection CssUnknownTarget*/--bs-breadcrumb-divider: url(&#34;data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='8' height='8'%3E%3Cpath d='M2.5 0L1 1.5 3.5 4 1 6.5 2.5 8l4-4-4-4z' fill='%236c757d'/%3E%3C/svg%3E&#34;);">
          <li class="breadcrumb-item "><a href="/speak">首页</a></li>
          <li v-if="this.lessonId === '1'" class="breadcrumb-item"><a href="/speak/playlist/1">新・シャドーイング 日本語を話そう! 初~中級編 [英語・中国語・韓国語訳版]</a></li>
          <li v-if="this.lessonId === '2'" class="breadcrumb-item"><a href="/speak/playlist/2">シャドーイング日本語を話そう! 中~上級編 [英語・中国語・韓国語訳版]</a></li>
          <li v-show="this.myBreadcrumbs" v-for="(crumb, index) in myBreadcrumbs" :key="index" class="breadcrumb-item">
            <a :href="'/speak/playlist/'+this.lessonId+'/' + myBreadcrumbs.slice(0, index + 1).join('/')">{{ crumb }}</a>
          </li>
<!--          <li class="breadcrumb-item active" aria-current="page">uint 1</li>-->
        </ol>
      </nav>
    </div>

    <router-view @show-player="showPlayer"></router-view>

    <!-- 全屏播放器界面 -->
    <transition name="slide-up" mode="in-out">
      <div v-if="isFullScreen" id="full-screen-player">
        <div @click="showFullPlayer" class="down-btn"></div>
        <!-- 全屏播放器内容 -->
        <div class="lyrics">
          <ul>
            <li v-for="(subtitle, index) in subtitles" :key="index"
                :class="{ 'highlight': currentTime >= subtitle.time && (subtitles[index + 1] ? currentTime < subtitles[index + 1].time : true) }">
              {{ subtitle.text }}
            </li>
          </ul>
        </div>
      </div>
    </transition>

    <!-- 播放器 -->
    <div v-show="this.isShowPlayer" id="player-div" class="container" :class="{fullscreen : this.isFullScreen}">
      <div class="row">

        <div @click="showFullPlayer" class="col-5 audio-info">
          <div style="display: inline-block">
            <span>{{this.playedAudio.title}}</span>
            <span>{{this.currentSubtitle}}</span>
          </div>
        </div>
        <div class="my-audio col-5">
          <audio controls ref="audio">
            <source :src="this.playedAudio.path" type="audio/mp3">
          </audio>
          <div class="custom-controls">
            <input type="range" min="0" :max="totalTime" step="0.1" v-model="currentTime" @input="seekAudio">
          </div>
        </div>



        <div class="col-2 option-btn-group">

<!--          <div>-->
<!--            <svg xmlns="http://www.w3.org/2000/svg" width="3.9rem" height="3.9rem" fill="currentColor" class="bi bi-skip-start" viewBox="0 0 16 16">-->
<!--              <path d="M4 4a.5.5 0 0 1 1 0v3.248l6.267-3.636c.52-.302 1.233.043 1.233.696v7.384c0 .653-.713.998-1.233.696L5 8.752V12a.5.5 0 0 1-1 0V4zm7.5.633L5.696 8l5.804 3.367V4.633z"/>-->
<!--            </svg>-->
<!--          </div>-->

          <div @click="playAudio">
            <svg v-if="this.isPlay" xmlns="http://www.w3.org/2000/svg" width="3.9rem" height="3.9rem" fill="currentColor" class="bi bi-pause" viewBox="0 0 16 16">
              <path d="M6 3.5a.5.5 0 0 1 .5.5v8a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5zm4 0a.5.5 0 0 1 .5.5v8a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
            </svg>
            <svg v-else xmlns="http://www.w3.org/2000/svg" width="3.9rem" height="3.9rem" fill="currentColor" class="bi bi-play" viewBox="0 0 16 16">
              <path d="M10.804 8 5 4.633v6.734L10.804 8zm.792-.696a.802.802 0 0 1 0 1.392l-6.363 3.692C4.713 12.69 4 12.345 4 11.692V4.308c0-.653.713-.998 1.233-.696l6.363 3.692z"/>
            </svg>
          </div>



<!--          <div>-->
<!--            <svg xmlns="http://www.w3.org/2000/svg" width="3.9rem" height="3.9rem" fill="currentColor" class="bi bi-skip-end" viewBox="0 0 16 16">-->
<!--              <path d="M12.5 4a.5.5 0 0 0-1 0v3.248L5.233 3.612C4.713 3.31 4 3.655 4 4.308v7.384c0 .653.713.998 1.233.696L11.5 8.752V12a.5.5 0 0 0 1 0V4zM5 4.633 10.804 8 5 11.367V4.633z"/>-->
<!--            </svg>-->
<!--          </div>-->

        </div>
      </div>
    </div>

  </div>
</template>

<script>
import LessonInfo from "./LessonInfo.vue";
export default {
  name: "Speak",
  components: {LessonInfo},
  data() {
    return {
      isShowPlayer: false,
      isPlay: false,
      isFullScreen: false,
      totalTime: 0,
      currentTime: 0,
      playedAudio: {

      },
      lessonId: undefined,
      requestDto: {
        path: "",
      },
      myBreadcrumbs: [],
      subtitles: [],
      currentSubtitle: "",
    }
  },
  methods: {
    showPlayer(playedAudio){
      this.isShowPlayer = true
      this.isPlay = true
      this.playedAudio = playedAudio
      this.playedAudio.path ="http://localhost:7777/audio"+playedAudio.path
      this.playedAudio.subtitlePath = this.playedAudio.path.replace(".mp3",'.lrc')

      // 加载音频并播放
      this.$nextTick(() => {
        const audio = this.$refs.audio;
        audio.load(); // 加载音频
        audio.play(); // 播放音频
      });

      fetch(this.playedAudio.subtitlePath)
          .then(response => response.text())
          .then(subtitleText => {
            this.parseSubtitles(subtitleText);
          })
          .catch(error => {
            console.log('无法加载字幕:', error);
          });

      const audio = this.$refs.audio;
      audio.addEventListener('timeupdate', this.updateTime);

      console.log("开始播放音频: "+playedAudio.title)
      console.log("音频地址为: "+this.playedAudio.path)
    },
    parseSubtitles(subtitleText) {
      this.subtitles = subtitleText
          .split('\n')
          .filter(line => line.includes('['))
          .map(line => {
            const match = line.match(/\[(\d{2}):(\d{2}\.\d{2})\](.*)/);
            if (match) {
              const minutes = parseInt(match[1], 10);
              const seconds = parseFloat(match[2]);
              const time = minutes * 60 + seconds;
              const text = match[3].trim();
              return { time, text };
            }
            return null;
          })
          .filter(Boolean);
    },
    // 更新字幕
    updateSubtitles() {
      const audio = this.$refs.audio;
      this.currentTime = audio.currentTime;

      let matchedSubtitle = '';
      for (let i = 0; i < this.subtitles.length; i++) {
        const subtitle = this.subtitles[i];
        if (this.currentTime >= subtitle.time) {
          matchedSubtitle = subtitle.text;
        } else {
          break;
        }
      }
      this.currentSubtitle = matchedSubtitle;
    },
    playAudio(){
      const audio = this.$refs.audio
      if(this.isPlay === true){
        // 暂停
        audio.pause();
        this.isPlay = false
      }else{
        // 播放
        audio.play()
        this.isPlay = true
      }
    },
    updateTime() {
      const audio = this.$refs.audio;
      this.currentTime = audio.currentTime;
      this.updateSubtitles();
    },
    seekAudio(event) {
      const audio = this.$refs.audio;
      audio.currentTime = event.target.value;
    },
    updateTotalTime() {
      const audio = this.$refs.audio;
      this.totalTime = audio.duration;
    },
    showFullPlayer(){
      this.isFullScreen = !this.isFullScreen;
      console.log("切换播放器样式")
    },
    updateBreadcrumbs(path) {
      console.log("执行了")
      // 处理面包屑
      const pathSegments = path.split('/').filter(Boolean); // 去掉空字符串
      // if (pathSegments.length > 2) {
      //   // 跳过 '/speak/playlist' 部分
      //   this.breadcrumbs = pathSegments.slice(2);
      // } else {
      //   this.breadcrumbs = [];
      // }

      this.myBreadcrumbs = pathSegments.slice(3);
      console.log(pathSegments)
      console.log(this.breadcrumbs)
    },
  },
  mounted() {
    const audio = this.$refs.audio;
    audio.addEventListener('timeupdate', this.updateTime);
    audio.addEventListener('loadedmetadata', this.updateTotalTime);
  },
  beforeUnmount() {
    const audio = this.$refs.audio;
    audio.removeEventListener('timeupdate', this.updateTime);
    audio.removeEventListener('loadedmetadata', this.updateTotalTime);
  },
  watch: {
    '$route.path': {
      immediate: true, // 初始化时立即触发
      handler(newPath) {
        // 获取路径的id
        const match = newPath.match(/\/(\d+)(\/|$)/);
        this.lessonId = match ? match[1] : null;
        // console.log("this.lessonId: "+this.lessonId)
        this.myBreadcrumbs = this.breadcrumbs
        this.updateBreadcrumbs(newPath);
      }
    }
  },
  computed: {
    breadcrumbs() {
      console.log(Array.isArray(this.$store.state.breadcrumbs));
      console.log(this.$store.state.breadcrumbs)
      return this.$store.state.breadcrumbs;
    }
  }
}
</script>

<style scoped>

#SpeakDiv {
  user-select: none;
  margin-top: 56px;
  height: 100vh;
}
#PlayList {
  margin-top: 56px;
}

.path-nav {
  padding-top: 2rem;
  width: 100%;
}

.path-nav li a {
  color: #3f4041;
}

.night-mode .path-nav li a {
  color: white;
}

.night-mode .path-nav .active {
  color: snow;
}

.night-mode #player-div, .night-mode .option-btn-group > div{
  background-color: rgb(66, 66, 66);
}

#player-div {
  padding: 0 3rem;
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100% !important;
  max-width: 100% !important;
  height: 3.9rem;
  background-color: rgb(150, 150, 150);
  /*transition: all 0.5s ease;*/
}

#player-div > .row {
  height: 100%;
}

.audio-info , .option-btn-group{
  display: inline-block;
}

audio {
  display: none;
}

.audio-info {
  text-align: left;
  color: white;
}

.audio-info >div> span {
  margin-top: 0.3rem;
  display: block;
}

.my-audio{
  display: inline-block;
}

.custom-controls {
  line-height: 3.9rem;
}

.custom-controls input[type="range"] {
  flex-grow: 1;
  -webkit-appearance: none; /* 移除默认样式 */
  appearance: none;
  width: 100%;
  height: 10px;
  border-radius: 5px;
  background: #d3d3d3;
  outline: none;
  opacity: 0.7;
  transition: opacity 0.2s;
}

.custom-controls input[type="range"]:hover {
  opacity: 1;
}

.custom-controls input[type="range"]::-webkit-slider-thumb {
  -webkit-appearance: none; /* 移除默认样式 */
  appearance: none;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background: #4CAF50;
  cursor: pointer;
  transition: background 0.2s;
}

.custom-controls input[type="range"]::-webkit-slider-thumb:hover {
  background: #45a049;
}

.custom-controls input[type="range"]::-moz-range-thumb {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background: #4CAF50;
  cursor: pointer;
  transition: background 0.2s;
}

.custom-controls input[type="range"]::-moz-range-thumb:hover {
  background: #45a049;
}

.option-btn-group {
  text-align: right;
}

.option-btn-group > div:hover, .audio-info:hover {
  cursor: pointer;
  background-color: rgb(111, 111, 111);
}

.option-btn-group > div {
  display: inline-block;
  border: none;
  margin: 0 auto;
  height: 100%;
  width: 3.9rem;
  background-color: rgb(150, 150, 150);
}



#full-screen-player {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgb(150, 150, 150);
  margin-top: 56px;
  display: inline-block;
  /*z-index: 999;*/
  /*overflow-y: auto;*/
  /*display: flex;*/
  /*flex-direction: column;*/
  /*align-items: center;*/
  /*justify-content: center;*/
  padding: 10px;
}

.slide-up-enter-active, .slide-up-leave-active {
  transition: transform 0.2s linear;

}

.slide-up-enter, .slide-up-leave-to {
  transform: translateY(100%);
}

.down-btn:hover {
  cursor: pointer;
}

.down-btn {
  margin: 0 auto 1rem;
  border-radius: 1rem;
  background-color: rgba(255,255,255,0.8);
  width: 30%;
  height: 0.6rem;
}

.lyrics {
  display: inline-block;
  font-size: 1.2rem;
  text-align: center;
}

.lyrics > ul > li {
  list-style: none;
}

.highlight {
  font-weight: bold;
  color: #ff6347; /* 高亮颜色 */
}

</style>