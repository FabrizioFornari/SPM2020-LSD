import store from '@/store'
import router from '@/router'
import config from '@/config/firebase'
import Firebase from 'firebase/app'
import 'firebase/auth'
import 'firebase/firestore'

export const fireApp = Firebase.initializeApp(config)
export const fireAuth = Firebase.auth()
export const fireStore = Firebase.firestore()
export const geoPoint = Firebase.firestore.GeoPoint

export async function signin(email: string, password: string, username: string) {
    try {
        await fireAuth.createUserWithEmailAndPassword(email, password)
        const current = fireAuth.currentUser
        if (current) {
            current.updateProfile({
                displayName: username
            }).then(function() {
                store.dispatch("fetchUser", current)
            }).catch(function(error) {
                console.log(error)
            })
        }
        console.log("Registred")
    } catch (error) {
        console.log(error)
    }
}

export async function login(email: string, password: string) {
    try {
        await fireAuth.signInWithEmailAndPassword(email, password)
        console.log("Logged in")
    } catch (error) {
        console.log(error)
    }
}

export async function logout() {
    try {
        await fireAuth.signOut()
        console.log("Logged out")
        router.push('/')
    } catch (error) {
        console.log(error)
    }
}

async function getRole(user) {
    if (user) {
        await user.getIdTokenResult(true).then(idTokenResult => {
            store.dispatch("fetchRole", idTokenResult.claims.role)
        }, err => {
            console.log(err)
        })
    }
}

const onAuthStateChangedPromise = new Promise((resolve, reject) => {
    fireAuth.onAuthStateChanged(async user => {
        store.dispatch("fetchUser", user)
        await getRole(user)
        resolve(user)
    }, err => {
        reject(err)
    })
})
  
export const onAuthStateInit = () => onAuthStateChangedPromise