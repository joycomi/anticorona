
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import VaccineManager from "./components/vaccineManager"

import BookingManager from "./components/bookingManager"

import InjectionManager from "./components/InjectionManager"
import CancellationManager from "./components/CancellationManager"


import Mypage from "./components/mypage"
export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/vaccine',
                name: 'vaccineManager',
                component: vaccineManager
            },

            {
                path: '/booking',
                name: 'bookingManager',
                component: bookingManager
            },

            {
                path: '/Injection',
                name: 'InjectionManager',
                component: InjectionManager
            },
            {
                path: '/Cancellation',
                name: 'CancellationManager',
                component: CancellationManager
            },


            {
                path: '/mypage',
                name: 'mypage',
                component: mypage
            },


    ]
})
