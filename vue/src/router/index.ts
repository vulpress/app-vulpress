/*
 * Copyright 2023 Szabolcs Bazil Papp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Composables
import { createRouter, createWebHistory, Router } from 'vue-router';

function redirectFromRoot(): string {
  let jwtToken: string | null = localStorage.getItem('jwtToken');
  return jwtToken ? 'Main' : 'Login';
}

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/Home.vue'),
    // redirect: redirectFromRoot,
    children: [
      {
        path: 'login',
        name: 'login',
        component: () => import('@/views/Login.vue'),
      },
      {
        path: 'main',
        name: 'main',
        component: () => import('@/views/Main.vue'),
        meta: {
          requiresAuth: true,
        },
      },
    ],
  },
];

const router: Router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

// router.beforeEach(async (to, from) => {
//   // if we are to navigate to a marked route or any of its descendants,
//   if (to.matched.some((r) => r.meta.requiresAuth)) {
//     // redirect to /login if there is no token, or it has expired:
//     if (!(await authService.isAuthenticated())) {
//       localStorage.clear();
//       return { name: 'Login' };
//     }
//   }
// });

export default router;
