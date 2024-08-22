<template>
  <div>
    <nav style="user-select: none" class="navbar navbar-expand-md fixed-top">
      <div class="container-fluid">
        <a style="color: white" class="navbar-brand" href="/">{{ $t('header_title') }}</a>
        <button style="color: white" class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
          <ul class="navbar-nav me-auto mb-2 mb-md-0">
            <li class="nav-item">
              <a href="/speak" style="color: white" class="nav-link active" aria-current="page">{{ $t('header_test') }}</a>
            </li>
            <li class="nav-item">
              <a href="/article/1" style="color: white" class="nav-link active" aria-current="page">{{ $t('header_about') }}</a>
            </li>
<!--            <li class="nav-item">-->
<!--              <a style="color: white" class="nav-link" href="/dict">{{ $t('header_tool') }}</a>-->
<!--            </li>-->
          </ul>
          <div class="nav-right">
            <ul class="navbar-nav me-auto mb-2 mb-md-0">
              <!-- write -->
              <li class="nav-item">
                <a :href="`/write`">
                  <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                    <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                    <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                  </svg>
                </a>
              </li>
              <!-- translate -->
              <li class="nav-item">
                <div class="dropdown-center">
                  <a class="dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-translate" viewBox="0 0 16 16">
                      <path d="M4.545 6.714 4.11 8H3l1.862-5h1.284L8 8H6.833l-.435-1.286H4.545zm1.634-.736L5.5 3.956h-.049l-.679 2.022H6.18z"/>
                      <path d="M0 2a2 2 0 0 1 2-2h7a2 2 0 0 1 2 2v3h3a2 2 0 0 1 2 2v7a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2v-3H2a2 2 0 0 1-2-2V2zm2-1a1 1 0 0 0-1 1v7a1 1 0 0 0 1 1h7a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H2zm7.138 9.995c.193.301.402.583.63.846-.748.575-1.673 1.001-2.768 1.292.178.217.451.635.555.867 1.125-.359 2.08-.844 2.886-1.494.777.665 1.739 1.165 2.93 1.472.133-.254.414-.673.629-.89-1.125-.253-2.057-.694-2.82-1.284.681-.747 1.222-1.651 1.621-2.757H14V8h-3v1.047h.765c-.318.844-.74 1.546-1.272 2.13a6.066 6.066 0 0 1-.415-.492 1.988 1.988 0 0 1-.94.31z"/>
                    </svg>
                  </a>

                  <ul class="dropdown-menu dropdown-menu-end">
                    <li><button @click="this.changeLanguage('cn')" class="dropdown-item" href="#">中国语</button></li>
                    <li><button @click="this.changeLanguage('ja')" class="dropdown-item" href="#">日本語</button></li>
                    <li><button @click="this.changeLanguage('en')" class="dropdown-item" href="#">English</button></li>
                  </ul>
                </div>
                <div>

                </div>
              </li>
              <!-- day-night -->
              <li class="nav-item" @click="changeDayMode">
                <div v-if="mode === 'day-mode'">
                  <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-brightness-high" viewBox="0 0 16 16">
                    <path d="M8 11a3 3 0 1 1 0-6 3 3 0 0 1 0 6zm0 1a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z"/>
                  </svg>
                </div>
                <div v-else-if="mode === 'night-mode'">
                  <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-moon-stars" viewBox="0 0 16 16">
                    <path d="M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278zM4.858 1.311A7.269 7.269 0 0 0 1.025 7.71c0 4.02 3.279 7.276 7.319 7.276a7.316 7.316 0 0 0 5.205-2.162c-.337.042-.68.063-1.029.063-4.61 0-8.343-3.714-8.343-8.29 0-1.167.242-2.278.681-3.286z"/>
                    <path d="M10.794 3.148a.217.217 0 0 1 .412 0l.387 1.162c.173.518.579.924 1.097 1.097l1.162.387a.217.217 0 0 1 0 .412l-1.162.387a1.734 1.734 0 0 0-1.097 1.097l-.387 1.162a.217.217 0 0 1-.412 0l-.387-1.162A1.734 1.734 0 0 0 9.31 6.593l-1.162-.387a.217.217 0 0 1 0-.412l1.162-.387a1.734 1.734 0 0 0 1.097-1.097l.387-1.162zM13.863.099a.145.145 0 0 1 .274 0l.258.774c.115.346.386.617.732.732l.774.258a.145.145 0 0 1 0 .274l-.774.258a1.156 1.156 0 0 0-.732.732l-.258.774a.145.145 0 0 1-.274 0l-.258-.774a1.156 1.156 0 0 0-.732-.732l-.774-.258a.145.145 0 0 1 0-.274l.774-.258c.346-.115.617-.386.732-.732L13.863.1z"/>
                  </svg>
                </div>
              </li>
<!--              &lt;!&ndash; notification &ndash;&gt;-->
<!--              <li class="nav-item">-->
<!--                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-bell" viewBox="0 0 16 16">-->
<!--                  <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4.002 4.002 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4.002 4.002 0 0 0-3.203-3.92L8 1.917zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5.002 5.002 0 0 1 13 6c0 .88.32 4.2 1.22 6z"/>-->
<!--                </svg>-->
<!--              </li>-->
              <!-- userInfo -->
              <li class="nav-item user-icon">
                <a :href="'/user/'+this.userId">
<!--                <div data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight" aria-controls="offcanvasRight">-->
                  <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                  </svg>
                </a>
              </li>
            </ul>
          </div>

        </div>
      </div>
    </nav>
    <!-- UserInfo -->
<!--    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">-->
<!--      <div class="offcanvas-header">-->
<!--        <h5 class="offcanvas-title" id="offcanvasRightLabel">{{ $t('header_user_h5')}}</h5>-->
<!--        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>-->
<!--      </div>-->
<!--      <div class="offcanvas-body">-->
<!--        这里是用户信息-->
<!--      </div>-->
<!--    </div>-->
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      mode: this.$store.state.mode, //  'night-mode'
      userId: this.$store.state.userId
    };
  },
  methods: {
    // 切换夜间-白天模式
    changeDayMode(){
      console.log(this.mode)
      this.mode = this.mode === 'day-mode' ? 'night-mode' : 'day-mode';
      this.$store.commit('setMode',this.mode)
    },
    // 切换语言
    changeLanguage(lang) {
      this.$i18n.locale = lang;
      this.$store.commit('setLocale',lang)
    },

  },
  mounted() {
    this.$i18n.locale = this.$store.state.locale;
    this.mode = 'night-mode'
  }
}

</script>

<style scoped>
.nav-right {
  display: inline-block;
}

.nav-right > .navbar-nav > .nav-item {
  margin-left: 2rem;
}

a {
  text-decoration: none;
  color: #fff;
}

.nav-item:hover {
  cursor: pointer;
}

/deep/ .dropdown-toggle::after{
  content: none;
}

/deep/ .dropdown-toggle {
  color: white;
}

/deep/ .navbar {
  background-color: #ec8951;
}

/deep/ .container-fluid {
  padding: 0 3rem;
}

</style>