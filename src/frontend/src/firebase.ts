import store from '@/store'
import router from '@/router'
import config from '@/config/firebase'
import Firebase from 'firebase/app'
import auth from '@/api/auth'
import 'firebase/auth'
import 'firebase/firestore'

export const fireApp = Firebase.initializeApp(config)
export const fireAuth = Firebase.auth()
export const fireStore = Firebase.firestore()
export const geoPoint = Firebase.firestore.GeoPoint

export async function getToken() {
    if (!fireAuth.currentUser) return null
    return await fireAuth.currentUser.getIdToken().then(idToken => {
        return idToken
    })
}

const onAuthStateChangedPromise = new Promise((resolve, reject) => {
    fireAuth.onAuthStateChanged(async user => {
        let account = null
        if (user) {
            account = (await auth.getUser()).data
            account.displayName = user.displayName
        }
        store.dispatch("fetchUser", account)
        resolve(user)
    }, err => {
        reject(err)
    })
})
  
export const onAuthStateInit = () => onAuthStateChangedPromise

export async function login(email: string, password: string) {
    try {
        await fireAuth.signInWithEmailAndPassword(email, password)
        console.log("Logged in")
        return
    } catch (error) {
        return(error.message)
    }
}

export async function logout() {
    try {
        await fireAuth.signOut()
        console.log("Logged out")
        router.push('/map')
    } catch (error) {
        console.log(error)
    }
}