<template>
  <div id="PlayList">
    <table class="table table-hover">
      <tbody>
      <tr v-for="forlder in soundList.folder">
        <th scope="row" @click="navigateToFolder(forlder.audioRootDirPath)">
          <svg style="margin-right: 10px" xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" class="bi bi-folder" viewBox="0 0 16 16">
            <path d="M.54 3.87.5 3a2 2 0 0 1 2-2h3.672a2 2 0 0 1 1.414.586l.828.828A2 2 0 0 0 9.828 3h3.982a2 2 0 0 1 1.992 2.181l-.637 7A2 2 0 0 1 13.174 14H2.826a2 2 0 0 1-1.991-1.819l-.637-7a1.99 1.99 0 0 1 .342-1.31zM2.19 4a1 1 0 0 0-.996 1.09l.637 7a1 1 0 0 0 .995.91h10.348a1 1 0 0 0 .995-.91l.637-7A1 1 0 0 0 13.81 4H2.19zm4.69-1.707A1 1 0 0 0 6.172 2H2.5a1 1 0 0 0-1 .981l.006.139C1.72 3.042 1.95 3 2.19 3h5.396l-.707-.707z"/>
          </svg>
          {{forlder.title}}
        </th>
      </tr>

      <tr v-for="file in soundList.file">
        <th @click="play(file.title,file.audioRootDirPath)" scope="row">
          <svg style="margin-right: 10px" xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" class="bi bi-file-music" viewBox="0 0 16 16">
            <path d="M10.304 3.13a1 1 0 0 1 1.196.98v1.8l-2.5.5v5.09c0 .495-.301.883-.662 1.123C7.974 12.866 7.499 13 7 13c-.5 0-.974-.134-1.338-.377-.36-.24-.662-.628-.662-1.123s.301-.883.662-1.123C6.026 10.134 6.501 10 7 10c.356 0 .7.068 1 .196V4.41a1 1 0 0 1 .804-.98l1.5-.3z"/>
            <path d="M4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H4zm0 1h8a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z"/>
          </svg>
          {{file.title}}
        </th>
      </tr>
      </tbody>
    </table>


  </div>
</template>

<script>
import request from "../../js/request.js";

export default {
  name: "PlayList",
  data() {
    return {
      soundList: {
        folder:[
        ],
        file:[
        ]
      },
      playing: "",
      playedAudio: {
        title:"",
      },
      requestDto: {
        path: "",
      },
      breadcrumbs: [],
    }
  },
  methods: {
    play(title,path) {
      this.playedAudio = {};
      this.playedAudio.title = title
      this.playedAudio.path = path
      this.$emit('show-player',this.playedAudio)
    },
    // 获取当前路径下的文件夹或者文件
    getCurrentPathFiles(){
      this.requestDto.path =this.idAndFollowingPath
      request.post("/kaiwa/getCurrentFiles",this.requestDto).then(res => {
        this.soundList.folder = []
        this.soundList.file = []
        for(var i=0;i<res.data.length;i++){
          if(res.data[i].isFile === 0){
            // folder
            this.soundList.folder.push(res.data[i])
          }else{
            // file
            this.soundList.file.push(res.data[i])
          }
        }
      })
    },
    navigateToFolder(path){
      // 直接使用传递的路径更新 URL
      this.$router.push(`/speak/playlist${path}`);
      // 更新面包屑
      // 将路径分割成数组并更新面包屑
      const newCrumbs = path.split('/').filter(Boolean); // 去掉空字符串
      if (newCrumbs.length > 0) {
        // 更新面包屑
        this.breadcrumbs = newCrumbs;
        // 处理特殊情况
        if (this.breadcrumbs[0] === '1' || this.breadcrumbs[0] === '2') {
          this.breadcrumbs.shift(); // 删除第一个元素
        }
      }
      console.log("面包屑的值: "+this.breadcrumbs)
      this.$store.commit('updateBreadcrumbs', this.breadcrumbs);
      // 发送请求获取新路径下的内容
      this.getCurrentPathFiles()
    }
  },
  computed: {
    // 获取完整的 URL 路径
    fullPath() {
      return this.$route.path; // 例如 '/speak/playlist/1/Unit1'
    },

    idAndFollowingPath() {
      return this.fullPath.replace('/speak/playlist', '');
    }
  },
  watch: {
    // 当 URL 路径变化时更新
    '$route.path': function () {
      this.getCurrentPathFiles();
    }
  },

  mounted() {
    this.requestDto.path = this.$route.path;
    if(this.requestDto.path !== "/speak/playlist/1" ||
        this.requestDto.path !== "/speak/playlist/2"
    ){
      this.breadcrumbs.push(this.requestDto.path.split('/').pop());
      if(this.breadcrumbs[0] === "1" || this.breadcrumbs[0] === "2"){
        this.breadcrumbs = this.breadcrumbs.shift()
      }
      console.log("面包屑的值: "+this.breadcrumbs[0])
      this.$store.commit('updateBreadcrumbs', this.breadcrumbs);
    }
    // 发送请求获取新路径下的内容
    this.getCurrentPathFiles()
  }
}
</script>

<style scoped>

 * {
   transition: background-color 0.5s, color 0.5s,opacity 0.5s;
 }

::-webkit-scrollbar {
  width: 10px;
  border-radius: 5px;
}

/* 背景 */
::-webkit-scrollbar-track {
  background: linear-gradient(to bottom, #f5f5f5, #e8e8e8);
}

::-webkit-scrollbar-thumb {
  background: linear-gradient(to bottom, #a1a1a1, #6b6b6b);
  border-radius: 5px; /* 圆角 */
}

#PlayList {
  background-color: white;
  max-height: 40rem;
  overflow-y: scroll;
  overflow-x: hidden;
}

.table {
  text-align: left;
  margin: 0;
}

th:hover {
  cursor: pointer;
}

.night-mode .table th{
  background-color: #3f4041;
  color: snow;
}



</style>