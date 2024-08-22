import { createStore } from 'vuex';

const store = createStore({
  state: {
    locale: localStorage.getItem('locale') || 'en',
    mode: localStorage.getItem('mode') || 'night-mode',
    token: localStorage.getItem('token'),
    userId: localStorage.getItem('userId'),
    // currentUser: {},
    breadcrumbs: [],
    currentUserName: localStorage.getItem('currentUserName'),
    currentNickName: localStorage.getItem('currentNickName'),
  },
  mutations: {
    setLocale(state, locale) {
      state.locale = locale
      localStorage.setItem("locale",locale)
    },
    setMode(state, mode) {
      state.mode = mode
      localStorage.setItem("mode",mode)
    },
    setToken(state, token) {
      state.token = token
      localStorage.setItem("token",token)
    },
    setUserId(state, UserId) {
      state.userId = UserId
      localStorage.setItem("userId",UserId)
    },
    setCurrentUserName(state, currentUserName){
      state.currentUserName = currentUserName
      localStorage.setItem("currentUserName",currentUserName)
    },
    setCurrentNickName(state, currentNickName){
      state.currentNickName = currentNickName
      localStorage.setItem("currentNickName",currentNickName)
    },
    // setCurrentUser(state, currentUser) {
    //   state.currentUser = {... currentUser}
    //   localStorage.setItem("currentUser",JSON.stringify(currentUser))
    // },
    updateBreadcrumbs(state, breadcrumbs) {
      state.breadcrumbs = breadcrumbs;
    }
  },
});

export default store;
