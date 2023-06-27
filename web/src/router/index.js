import { createRouter, createWebHistory } from 'vue-router'
import PkindexView from '../views/pk/PkindexView'
import RecordIndexView from '../views/record/RecordIndexView'
import RanklistIndexView from '../views/ranklist/RanklistIndexView'
import UserBotIndexView from '../views/user/bots/UserBotIndexView'
import NotFound from '../views/error/NotFound'
import UserAccountLoginView from '../views/user/account/UserAccountLoginView'
import UserAccountRegisterView from '../views/user/account/UserAccountRegisterView'
import store from '../store/index'

const routes = [
  {
    path: "/",
    name: "home",
    redirect: "/pk/",
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/pk/",
    name: "pk_index",
    component: PkindexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/ranklist/",
    name: "ranklist_index",
    component: RanklistIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/record/",
    name: "record_index",
    component: RecordIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/user/bot/",
    name: "user_bot_index",
    component: UserBotIndexView,
    meta: {
      requestAuth: true,
    }
  },

  {
    path: "/user/account/login/",
    name: "user_account_login",
    component: UserAccountLoginView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/user/account/register/",
    name: "user_account_register",
    component: UserAccountRegisterView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/404/",
    name: "404",
    component: NotFound,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404/"
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})



router.beforeEach((to, from, next) => {
  if (to.meta.requestAuth && !store.state.user.is_login) {
    next({ name: "user_account_login" });
  }
  else {
    next();
  }

  // let flag = 1;
  // const jwt_token = localStorage.getItem("jwt_token");

  // if (jwt_token) {
  //   store.commit("updateToken", jwt_token);
  //   store.dispatch("getInfo", {
  //     success() {
  //     },
  //     error() {

  //       alert("token无效，请重新登录！");
  //       router.push({ name: 'userLogin' });
  //       store.commit("updatePullingInfo", false);
  //     }
  //   })
  // } else {
  //   flag = 2;
  // }

  // if (to.meta.requestAuth && !store.state.user.is_login) {
  //   if (flag === 1) {

  //     next();
  //     store.commit("updatePullingInfo", false);
  //   } else {

  //     alert("请先进行登录！");
  //     next({ name: "userLogin" });
  //     store.commit("updatePullingInfo", false);
  //   }
  // } else {

  //   next();
  //   store.commit("updatePullingInfo", false);
  // }
  // console.log(store.state.user.pulling_info);

})

export default router
