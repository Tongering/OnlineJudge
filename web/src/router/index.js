import { createRouter, createWebHistory } from 'vue-router'
import UserAccountLoginView from "@/views/UserIdentify/UserAccountLoginView.vue"
import UserAccountRegisterView from "@/views/UserIdentify/UserAccountRegisterView.vue"
import HomeView from "@/views/HomeView.vue"
import PersonalHomeView from "@/views/UserProfileBar/PersonalHomeView.vue"
import ExercisesView from "@/views/ExerciseBar/ExercisesView.vue"
import ExercisesTypeView from "@/views/ExerciseBar/ExercisesTypeView.vue"
import TeacherHomeView from "@/views/TeacherBar/TeacherHomeView.vue"
import ExercisePageView from "@/views/ExercisePageView.vue"
import store from '@/store'
import TerminalView from "@/views/TerminalView.vue"
import InvitationTypeView from "@/views/Invitation/InvitationTypeView.vue"
import InvitationListView from "@/views/Invitation/InvitationList.vue"
import InvitationPageView from "@/views/Invitation/InvitationPageView.vue"
import PostInvitationView from "@/views/Invitation/PostInvitationView.vue"
import AdminLeftBarView from "@/views/AdminLeftBarView.vue"

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/login/',
    name: 'user_login',
    component: UserAccountLoginView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/register/',
    name: 'user_register',
    component: UserAccountRegisterView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/user/space/:userId/userhome/',
    name: 'personal_home_userhome',
    component: PersonalHomeView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/user/space/:userId/userprofile/',
    name: 'personal_home_userprofile',
    component: PersonalHomeView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/exercise/choose/',
    name: 'exercise_choose',
    component: ExercisesView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/exercise/choose/:type/',
    name: 'exercise_choose_type',
    component: ExercisesTypeView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/tongering/teacher/student/',
    name: 'teacher_home_student',
    component: TeacherHomeView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/tongering/teacher/exercise/',
    name: 'teacher_home_exercise',
    component: TeacherHomeView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/tongering/teacher/invitation/',
    name: 'teacher_home_invitation',
    component: TeacherHomeView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/exercise/page/:pageId/',
    name: 'exercise_page',
    component: ExercisePageView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/terminal/',
    name: 'terminal_page',
    component: TerminalView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/invitation/choose/',
    name: 'invitation_choose',
    component: InvitationTypeView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/invitation/choose/:type/',
    name: 'invitation_choose_type',
    component: InvitationListView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/invitation/page/:pageId/',
    name: 'invitation_page',
    component: InvitationPageView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/invitation/post/',
    name: 'post_invitation',
    component: PostInvitationView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/tongering/admin/adminhome/',
    name: 'admin_home_about',
    component: AdminLeftBarView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/tongering/admin/teachermanager/',
    name: 'admin_home_teacher',
    component: AdminLeftBarView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/tongering/admin/studentmanager/',
    name: 'admin_home_student',
    component: AdminLeftBarView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/tongering/admin/invitationmanager/',
    name: 'admin_home_invitation',
    component: AdminLeftBarView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/tongering/admin/commentmanager/',
    name: 'admin_home_commment',
    component: AdminLeftBarView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/tongering/admin/terminalmanager/',
    name: 'admin_home_terminal',
    component: AdminLeftBarView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/tongering/admin/adminsetting/',
    name: 'admin_home_admin',
    component: AdminLeftBarView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/terminal/',
    name: 'terminal',
    component: TerminalView,
    meta: {
      requestAuth: true,
    }
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  let flag = 1;
  const jwt_token = localStorage.getItem("jwt_token");

  if (jwt_token) {
    store.commit("updateToken", jwt_token);
    store.dispatch("getinfo", {
      success() {
        
      },
      error() {
        // alert("invalid token, please login again!");
        router.push({ name: "user_login" });
        store.commit("logout");
      },
    })
  } else {
    flag = 2;
  }

  if (to.meta.requestAuth && !store.state.user.is_login) {
    if (flag === 1) {
      next();
    } else {
      // alert("please login");
      next({ name: "user_login" });
      store.commit("logout");
    }
  } else {
    next();
  }
})

export default router
